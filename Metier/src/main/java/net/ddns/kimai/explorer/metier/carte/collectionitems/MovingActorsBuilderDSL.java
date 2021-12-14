package net.ddns.kimai.explorer.metier.carte.collectionitems;

import java.util.LinkedHashMap;
import java.util.Map;

import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
// concrete type
import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;

public class MovingActorsBuilderDSL {

	private final Carte2DBuilderDSL carteBuilder;
	// too much dependent of implementation
	//private final ActorsOnCarte actors;
	private final Map<MovingActor, ActorPropsInConfiguration> actorsMap;
	private final Map<MovingActor, PositionOrientation> actorsPositionMap;
	
	public MovingActorsBuilderDSL( Carte2DBuilderDSL carteBuilder ) {
		this.carteBuilder = carteBuilder;
		this.actorsMap = new LinkedHashMap<MovingActor, ActorPropsInConfiguration>();
		this.actorsPositionMap = new LinkedHashMap<MovingActor, PositionOrientation>();
	}
	
	public MovingActorsBuilderDSL withAventurier(String name, PositionOrientation posOrient, String actions) {
		actorsMap.put( new Aventurier(name), 
					   new ActorPropsInConfiguration(posOrient, ActionSequenceFactory.input(actions)) );
		actorsPositionMap.put( new Aventurier(name), posOrient );
		return this;
	}
	
	public Carte2DBuilderDSL endMovingActors() {
		ActorsOnCarte actors = new ActorsOnCarteOrdered( actorsPositionMap );
		carteBuilder.setActors( actors );
		return carteBuilder;
	}	
}
