package net.ddns.kimai.explorer.metier.carte.collectionitems;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.ActorPosition;
import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;

import net.ddns.kimai.explorer.metier.rendering.Frame;
import net.ddns.kimai.explorer.metier.rendering.Renderable;
import net.ddns.kimai.explorer.metier.utils.Pair;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;

// StackOverflow : HashSet consumes about 5.5 times more memory than ArrayList
// For Loop iteration ArrayList fast & efficient in memory
// possible to send back unmodifiable List/Set and copyOf, 
//    => more explicit about use and safer
public class ActorsOnCarteOrdered implements ActorsOnCarte, Renderable {

	// map more general : one actor can have more than one PositionOrientation (record trajectory)
	protected final Map<MovingActor, PositionOrientation> actors;
	
	// use public because of Carte2DBuilder
	public ActorsOnCarteOrdered() {
		actors = new LinkedHashMap<>();
	}
	
	public ActorsOnCarteOrdered( Map<MovingActor, PositionOrientation> actorsMap) {
		actors = new LinkedHashMap<>( actorsMap );
	}
		
	// new interface, kind of "standart"
	// LinkedHashMap ..
	public ActorsOnCarteOrdered(List<Pair<MovingActor, PositionOrientation>> actorsPosition) {
		actors = actorsPosition.stream()
		        .collect(Collectors.toMap( Pair::item, 
		        						   Pair::value,
		        						   // merge mandatory for declaring supplier ?
		        						   // should not happen to merge keys, throw exception ?
		        						   (existing,replace) -> existing,
		        						   LinkedHashMap::new));
	}

	// possible to send unmodifiable List with copy.Of
	@Override
	public List<MovingActor> getActors() {	
		return actors.keySet().stream()
				     .collect(Collectors.toList());
	}
	
	@Override
	public PositionOrientation getPositionOritentation(MovingActor actor) {
		return actors.get(actor);
	}
	
	@Override
	public List<ActorPosition> getActorsPosition() {
		return actors.entrySet().stream()
			.map( entry -> new ActorPosition( entry.getKey(), entry.getValue()))
			.collect( Collectors.toList() );
	}

	@Override
	public void updatePosition(MovingActor actor, PositionOrientation position) {
		actors.put(actor, position);
	}

	@Override
	public Frame fillFrame(Frame frame, Frame.REPLACEMENT strategyReplace) {
		actors.entrySet().stream()
			  .forEach( entry -> frame.setFrame( formatFrame( entry.getKey()), 
					  							 entry.getValue().getPosition(),
					  							 strategyReplace));				
		return frame;
	}
	
	@Override
	public void fillFrameWithLambda(BiConsumer<String, Position> fillProcess) {
		// TODO Auto-generated method stub
		
	}
	
	// break modularity because call to container
	// if => Should forward to Actor, test if no name ?? null (X ? on carte ? optional ?)
	private String formatFrame( MovingActor actor) {
		return actor.formatFrame();
		// should be in ChercheurTresor / CollecteurActor rather
//		return ((Aventurier)actor).backpack().size() > 0 
//				   ? actor.getNom().substring(0, 2) + "(" + backpack().size() + ")"
//			       : actor.getNom().substring(0,2);
	}
	
//	@Override
//	public Frame fillFrame2(Frame frame, BiConsumer<String, Position> fillProcess) {
//		actors.entrySet().stream()
//		  .forEach( entry -> fillProcess.accept( 
//				  				formatFrame( entry.getKey()), 
//				  							 entry.getValue().getPosition()));
//		return frame;
//	}
}
