package net.ddns.kimai.explorer.metier.parseinput;

import java.util.ArrayList;
import java.util.List;

import net.ddns.kimai.explorer.metier.InputConfigurationProvider;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.parseinput.RandomizeItem.Strategy;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCarte;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCollectableItem;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputFixedItem;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputMovingActor;
import net.ddns.kimai.explorer.metier.parseinput.datainput.RandomProperties;
import net.ddns.kimai.explorer.metier.parseinput.parsers.FactoryParser;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.Pair;

// business logic, read Data (String) with Parsers (it is a ParserOfString)
// if not Random : apply the input           / check InitialSetupRulesCAT respected
// if Random : need a clean interface        / follow InitialSetupRulesCAT for generating map
// usage from FileconfigurationProvider && InteractiveModeconsole
// aggregate all infos for configuration

// still not so clear with name of aventurier
public class ConfigurationJeuBuilder {

	private final InputConfigurationProvider serviceConfig;
	// split fonctionalities of randomizerItemSetup
	private final RandomizeItem randomizer;
		
	// not very necessary, it is a singleton. maybe a provider
	// private final FactoryParser factoryParser;

	// structure to return
	private ConfigurationJeu config;
	
	// Logic of building, some items cannot be overlapped
	private List<Position> forbiddenPosition = new ArrayList<>();

	private DataInputCarte dataCarte; // mandatory
	private List<DataInputFixedItem> dataFixed = new ArrayList<>();
	private List<DataInputCollectableItem> dataCollectable = new ArrayList<>();
	private List<DataInputMovingActor> dataActor = new ArrayList<>();
	
	// use only internal, need to provide a FactoryParserProvider if want to mock
	private ParserLineInput pLineInput;
	
	// add a make link between readInput() and build()
	// certainly in a constructor when well splitted
	private Dimension dimension;

	// clearly, one set of rules to provide CATrules
	public ConfigurationJeuBuilder( InputConfigurationProvider serviceConfig,
								    RandomizeItem randomizer ) {
		this.serviceConfig = serviceConfig;
		this.randomizer = randomizer;
	}

	// one entry from File or InteractiveMode, read all provided entry input, store internal
	public void readInput() {
		String line;
		// nextEntry, line by line , should work for file or Interactive Console
		while( ( line = serviceConfig.nextEntry() ) != null ) {
			pLineInput = FactoryParser.createParser(line);
			// config.insert( data ); not bad...
			insert( pLineInput.getData() );
		}
		// once input is read, only from here can set dimension into randomizer
		randomizer.setDimension( dataCarte.getPropsItem() );
		// test to help testing
		config = new ConfigurationJeu();
		config.setDimensionCarte( dataCarte.getPropsItem() );
		
		// will be needed when split, make link between read and build()
		this.dimension = dataCarte.getPropsItem(); 
	}
	
//	private Dimension getDimension() {
//		return this.dimension;
//	}
	
	// from internal storage of Data, build and return (a valid ?) ConfigurationJeu
	// pass option to BuildList, it is a business logic for CAT
	// help testing pass as parameter ?
	// other way  pass dimension in constructor, and report to a new created config
	public ConfigurationJeu build( /*ConfigurationJeu config*/ ) {
		// test to help testing, move into readInput()
		//config = new ConfigurationJeu();
				// 1. Need Dimension Parameter, mandatory : Error if missing !
				// galere to test build() independently, because config created there
		// config.setDimensionCarte( dataCarte.getPropsItem() );
		//config.setDimensionCarte( getDimension() );
		
		// Now we can fix the order of building (independent of input file)
		// may be dependent  of the game (CAT, BD) design template template
		
		// Fixed Item, generate object fixed or random // optionOverlap, makeForbidden
		List<Pair<FixedItem,Position>> posItemFixed = 
				buildListItem( dataFixed, 
							   Strategy.MAX_ONE_ITEM,
							   true ); 
		// config.addLall( test )
		for( Pair<FixedItem,Position> pair : posItemFixed )
			config.addFixedItem( pair.item(), pair.value() );
				
		// Collectable..., cannot be placed on a previous oblstacle
		// internediate, generate object fixed or random
		List<Pair<CollectableItem,Position>> posItemCollectable = 
				buildListItem( dataCollectable, 
							   Strategy.MULTIPLE_ITEM, 
							   false); // makeForbidden
		for( Pair<CollectableItem,Position> pair : posItemCollectable )
			config.addCollectableItem( pair.item(), pair.value() );
				
		// Create the MovingActor
		List<Pair<MovingActor,ActorPropsInConfiguration>> posItemActor = 
				buildListItem( dataActor, 
							   Strategy.MAX_ONE_ITEM,
							   false); // makeForbidden

		for( Pair<MovingActor,ActorPropsInConfiguration> pair : posItemActor ) {
		// more specific, cannot be done in generic buildList	
			// done somewhere ? but where ?
			//ActorPropsInConfiguration randomActorProp = randomizer.randomActorProps(pos);
			// it is a default, could in constructor
			//aventurier.setNom("Anonymous"); // name set done in Data<Actor> createItem()
			//pair.item().setNom("anonymous");
			config.addMovingActors( pair.item(), pair.value() );
		}
		return config;
	}
	
	// R specified here, data().getPropsRandom().nbItem() no need casting to (RandomProperties)
	private <I,V,R extends RandomProperties,T extends DataInput<I,V,R>> 
		List<Pair<I,V>> buildListItem( List<T> listData, 
									   Strategy optionMultiItem, 
									   boolean makeForbidden) {		
		
		// I specific class implemnt. of FixedItem/Collectable
		// V Position or ActorPropsInConfiguration		
		// compatible ConfigurationJeu storage ItemsOnConfiguration
		List<Pair<I, V>> newItems = new ArrayList<>();
		
		for( var data : listData) {
			// correct className ...Morphologie$2
			// Class<?> clazzItem = data.getItemClass();
			// may split further buildRandom / buildFixedInput 
			// based on Optional<RandomProp> or more type DataInputRandom / DataInputFixed
			if( data.isRandom() ) {
				List<Position> posItem = 
						randomizer.generateRandomPosition( data.getPropsRandom().nbItem(), 
														   optionMultiItem, forbiddenPosition );
				// specific to CAT, FixedItem are unique at position
				if( makeForbidden )
					forbiddenPosition.addAll( posItem );

				//if( V instanceof Position) {
				//if( Position.isAssignableFrom( V ))
				//if( listData instanceof List<Pair<I, Position>> ) {
				//Class<?> testV = V.class ; //null;  //new V();
				//if( testV instanceof Position) {
				//if( Position.class.isAssignableFrom( ((RandomProperties) ) ) {
				for( Position pos : posItem ) {
					I objFixed = data.createItem();
					// arg... only once data.getProps() will solve ? not only..
					if( objFixed instanceof MovingActor ) {
						// just a test, name should be set elsewhere ?
						((Aventurier)objFixed).setNom("anonymous");
						newItems.add( new Pair<I, V>( objFixed, (V) randomizer.randomActorProps( pos ) ));
						
					} else { //if( objFixed instanceof type) {
						newItems.add( new Pair<I,V>( objFixed, (V) pos ));	
					}
				}
			// Fixed position in input	
			} else {				
				for( int i=0; i < data.nbItem(); i++ ) {
					I objFixed = data.createItem();
					newItems.add( new Pair<I, V>( objFixed, data.getPropsItem()));
				}				
				// same it becomes a forbidden place
				// works only with Position here, need a getPosition here To test !!
				if( makeForbidden )
					forbiddenPosition.add( (Position) data.getPropsItem() );
			}
		}
		return newItems;
	}
	
	// party I : read inputLine and local storage. To do only once, even with multiple simumlation
	// private <I,V,R> void insert2( DataInput<I,V,R> data ) { 
	private void insert( DataInput<?,?,?> data ) {
		// typical visitor pattern, to replace a switch statement
		// or switch with class type ?? available
		// https://stackoverflow.com/questions/5579309/is-it-possible-to-use-the-instanceof-operator-in-a-switch-statement
		// CLAZZ z = CLAZZ.valueOf(this.getClass().getSimpleName());
	    // switch (z) {
		
		// only ONE , and MUST BE providied
		if( data instanceof DataInputCarte ) {
			// if( dataCarte != null ) throw RuntimeException();
			dataCarte = (DataInputCarte) data;
			
		} else if( data instanceof DataInputFixedItem ) {
			dataFixed.add( (DataInputFixedItem) data );
	
		} else if(  data instanceof DataInputCollectableItem ) {
			
// NOTE there is a clear copy  of the split 3 List DataInput in ConfigurationJeuBuilder
//                                          3 explicit list in ConfigurationJeu
// may regroup in same class, more cohersive ?? or gain to split ?
// a Unique and general  configjeu.insert( Data ), and  ask to build configJeu.build()
// ConfigJeu would not be limited to a simple value / immutable class ? still possible to design api...

// may be more : tell, don't ask			
			dataCollectable.add( (DataInputCollectableItem) data);
			
		} else if( data instanceof DataInputMovingActor ) {
			dataActor.add( (DataInputMovingActor) data );
		}
	} 

}
