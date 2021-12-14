package net.ddns.kimai.explorer.metier.builders;

import java.util.List;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
import net.ddns.kimai.explorer.metier.carte.CarteException;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.carte.Terrain;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;
import net.ddns.kimai.explorer.metier.rendering.Renderer;
import net.ddns.kimai.explorer.metier.rendering.RendererCarteAuxTresors;
import net.ddns.kimai.explorer.metier.utils.Pair;

/** Must setup CarteExplorer ( by injection  of correct objects)
 *  - from an input file (line by line or full text available, need parserString)
 *  - from an input in console (line by line necessary) 
 *  - from a GUI (need to transform to String, then read)
 *  intermediate ConfigurationJeu
 *  
 *  This implementation deals with a Configuration object already created
 *      Objects aventuriers, Tresor, Montagne already created
 *      	Done by Parser and other factories...
 *  For use by manual console set, need an other implementation / base class / interface ? 
 */

// Inserted Renderer here, convenient but best place ?? 
// Strange sometime call cfgJeu, sometimes use static method in cfgJeu... to make clearer ! 
//   which usage is better ?
// Terrain constructor only with default, then call terrain.setItem( item , position) ! different of others
//     =>  Configuration may need set(), no the private final object used by the simulator in model
// Tresors must a correct format (Map for injection), done here
// Actors same , done by Static function in ConfigurationJeu

// static formatStructureForActorsOnCarte (ConfigurationJeu (List< Pair> in fact ! nowx all the same )
// return map adapted
public class CarteBuilder {
	
	protected final ConfigurationJeu cfgJeu;
	
	//protected final Dimension dimensions; moved in Factory, carteBuilder other business/logic
	protected final FactoryComponent factoryComponent;	
	// to inject in Carte for creation
	protected ActorsOnCarte actors;
	protected Terrain terrain;
	protected CollectableItemsOnCarte tresors;
	// output
	protected Carte carte;
	// need all components to be created, associated to a Carte
	protected Renderer renderer;
	
	// private static final String ERROR_CARTE_NOT_INITIALIZED = "La Carte n'a pas été initialisée. "
	//		+ "Vérifiez l'ordre dans le ficher d'input";
	
	public CarteBuilder(ConfigurationJeu cfgJeu,
						FactoryComponent factoryComponent) throws CarteException {
		this.cfgJeu = cfgJeu;
		this.factoryComponent = factoryComponent;
		//this.dimensions = cfgJeu.getDimensionCarte();
		// pass dimension, but also dimension in create duplicate ?
		// this.factoryComponent = ComponentCarteFactoryProvider.createFactory( dimensions.toArray() );
	}
	
	public Carte buildFromConfiguration() throws CarteException {
		this.terrain = setupTerrain(); 
		this.tresors = setupCollectables();
		this.actors = setupActors();
		
		this.carte = factoryComponent.createCarte(actors, terrain, tresors);
		return this.carte;
	}
	
	// only if needed... good place or should be elsewhere ?
	public Renderer buildRenderer() {
		renderer = new RendererCarteAuxTresors( carte, actors, terrain, tresors);
		return renderer;
	}
	
	// pass CarteException to unchecked exception, should be fine
	private Terrain setupTerrain() {
		Terrain tmp = factoryComponent.createTerrain(Morphologie.class, /*dimensions.toArray(), */ Morphologie.PLAINE);
		cfgJeu.getFixedItems() 
			  //.filter( entry -> testInCarte(entry.getValue()))
			  .forEach( pair -> tmp.setFixedItem( pair.value(), pair.item()) );
		return tmp;
	}
	
	private CollectableItemsOnCarte setupCollectables() {		
		return factoryComponent.createCollectables( cfgJeu.getCollectableItems() );
	}
	
	// not nice to test with static, but mock test not very conclusive anyway
	private ActorsOnCarte setupActors() {
		return factoryComponent.createActors( formatActorsPositionToCarte() );
	}
	
	private List< Pair<MovingActor, PositionOrientation>> formatActorsPositionToCarte() {
		return cfgJeu.getMovingActors().stream()
					 .map( pair -> { return Pair.of( pair.item(), pair.value().getPositionOrientation()); })
					 .collect(Collectors.toList());
	}
	
	//private boolean testInCarte(Position position) {
	//	return true;
	//}
}
