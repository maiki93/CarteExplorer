package net.ddns.kimai.explorer.metier.parsers;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.carte.Carte2D;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.parseinput.DataInput;
import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;

// looks fine to me, I have the control on my implementation
// will be enforced if the pacakge is exported only to parsers
import net.ddns.kimai.explorer.metier.parseinput.datainput.*;
//import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCarte;
//import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCollectableItem;
//import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputFixedItem;
//import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputMovingActor;
//import net.ddns.kimai.explorer.metier.parseinput.datainput.RandomProperties;
import net.ddns.kimai.explorer.metier.parseinput.parsers.FactoryParser;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;

// same
import net.ddns.kimai.explorer.metier.position.*;
//import net.ddns.kimai.explorer.metier.position.Position;
//import net.ddns.kimai.explorer.metier.position.Position2D;
//import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;

class ParserInputTest {
	
	ParserLineInput pLine;
	DataInput<?, ?, ?> data;
	
	@Test
	void parseCarte() {
		//given
		String line = "C - 4 - 5";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		// then
		Dimension dim = (Dimension) data.getPropsItem();
		assertThat( dim.rank() ).isEqualTo( 2 );
		assertThat( dim.getElement(0)).isEqualTo(4);
		assertThat( dim.getElement(1)).isEqualTo(5);
		
		assertThat( data ).isExactlyInstanceOf( DataInputCarte.class );
		assertThat( data.getItemClass() ).isEqualTo(Carte2D.class);
		assertThat( data ).isInstanceOf( DataInputCarte.class );
		
	// could use specific entry, exactly same Id for the object
		DataInputCarte dataSpec = (DataInputCarte) pLine.getData();
		//Dimension dim2 = dataSpec.getPropsItem();
		assertThat( dataSpec.getItemClass() ).isEqualTo(Carte2D.class);
		// assert random is null
		assertThat( data.getPropsRandom() ).isNull();
	}
	
	@Test
	void oneAventurierFixedCheckType() {
		//given
		String line = "A - Lara - 2 - 3 - E - AGGD";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		ActorPropsInConfiguration props = (ActorPropsInConfiguration) data.getPropsItem();
		
		assertThat( data ).isExactlyInstanceOf( DataInputMovingActor.class );
		assertThat( MovingActor.class ).isAssignableFrom( data.getItemClass() );
		assertThat( data.getItemClass()).isEqualTo( Aventurier.class );
		assertThat( props.getPositionOrientation() )
						 .isEqualTo( PositionOrientation2D.create(2, 3, 'E'));
	}
	
// need specific type to create an instance
	@Test
	void oneAventurierCreateInstance() {
		//given
		String line = "A - Lara - 2 - 3 - E - AGGD";
		// when
		pLine = FactoryParser.createParser(line);
		
		DataInputMovingActor dataActor = (DataInputMovingActor) pLine.getData();
		MovingActor aventurier = dataActor.createItem();
		assertThat( aventurier ).isNotNull()
		                        .isExactlyInstanceOf( Aventurier.class );
		assertThat( aventurier.getNom() ).isEqualTo("Lara");
	}
	
	@Test
	void oneAventurierRandomCheckType() {
		//given
		String line = "A - R - 5";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		//ActorPropsInConfiguration props = (ActorPropsInConfiguration) data.getPropsItem();
		
		assertThat( data ).isExactlyInstanceOf( DataInputMovingActor.class );
		assertThat( data.getItemClass()).isEqualTo( Aventurier.class );
		assertThat( data.getPropsItem() ).isNull();
		
		RandomProperties rProp = (RandomProperties) data.getPropsRandom();
		assertThat( rProp ).isNotNull()
						   .isInstanceOf( RandomProperties.class );
		assertThat( rProp.nbItem()).isEqualTo( 5 );
	}
	
	@Test
	void oneMontagneFixedCheckType() {
		//given
		String line = "M - 1 - 2";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		
		assertThat( data ).isExactlyInstanceOf( DataInputFixedItem.class );
		assertThat( Morphologie.class ).isAssignableFrom( data.getItemClass() );
		assertThat( FixedItem.class ).isAssignableFrom( data.getItemClass() );
		
		assertThat( data.getPropsItem() ).isExactlyInstanceOf( Position2D.class );
		assertThat( data.getPropsItem() ).isInstanceOf( Position.class );
	}
	
	@Test
	void oneMontagneFixedCreateInstance() {
		//given
		String line = "M - 1 - 2";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		
// specific to Enumeration
		FixedItem objFixed = Morphologie.fromClass( data.getItemClass() );
		
		assertThat( objFixed ).isNotNull()
							  .isEqualTo( Morphologie.MONTAGNE );
		assertThat( (Position) data.getPropsItem() )
								   .isNotNull()
								   .isEqualTo( Position2D.create(1, 2));
	}
	
	@Test
	void montagneRandomCheckType() {
		//given
		String line = "M - R - 20";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		
		RandomProperties rProp = (RandomProperties) data.getPropsRandom();
		assertThat( rProp ).isNotNull()
		   				   .isInstanceOf( RandomProperties.class );
		assertThat( rProp.nbItem()).isEqualTo( 20 );
	}
	
	@Test
	void twoTresorFixedCheckType() {
		//given
		String line = "T - 1 - 2 - 2";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		
		assertThat( data ).isExactlyInstanceOf( DataInputCollectableItem.class );
		assertThat( CollectableItem.class ).isAssignableFrom( data.getItemClass() );		
		assertThat( data.getItemClass() ).isEqualTo( Tresor.class );
		assertThat( data.getPropsItem() ).isExactlyInstanceOf( Position2D.class );
		assertThat( data.getPropsItem() ).isInstanceOf( Position.class );
	}
	
	@Test
	void tresorRandomCheckType() {
		//given
		String line = "T - R - 5 ";
		// when
		pLine = FactoryParser.createParser(line);
		data = pLine.getData();
		
		RandomProperties rProp = (RandomProperties) data.getPropsRandom();
		
		assertThat( data ).isExactlyInstanceOf( DataInputCollectableItem.class );
		assertThat( data.getItemClass() ).isEqualTo( Tresor.class );
		assertThat( data.getPropsItem() ).isNull();
		assertThat( rProp ).isNotNull();
		assertThat( rProp.nbItem() ).isEqualTo( 5 );
	}
	
	@Test
	void tresorCreateInstance() {
		//given
		String line = "T - 1 - 2 - 2";
		// when
		pLine = FactoryParser.createParser(line);
		
		DataInputCollectableItem data = (DataInputCollectableItem) pLine.getData();
		// specific interface
		assertThat( data.nbItem() ).isEqualTo(2);
		
		CollectableItem item = data.createItem();
		assertThat( item ).isExactlyInstanceOf( Tresor.class );
		assertThat( pLine.getData().getPropsItem() )
						 .isNotNull()
						 .isEqualTo( Position2D.create(1, 2));
	}
	
	
}
