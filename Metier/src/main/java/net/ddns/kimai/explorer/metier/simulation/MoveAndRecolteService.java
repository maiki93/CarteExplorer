package net.ddns.kimai.explorer.metier.simulation;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.ddns.kimai.explorer.metier.carte.ChercheurTresor;

// should split again with specific impelmentation
// MoveAndRecolteNoParralelService (with ordered list)
// MoveAndRecolteWithInputActionNoParrallelService // full name
public class MoveAndRecolteService implements MoveService {
	
	// We have a local copy of the actor's position and action sequence 
	// need Carte to get access to Terrain (or ASK isMovePossibleAt(newPos) / Morpho	
	//    if actor position is updated => sendMessage to Carte
	
	protected final Carte carte;
	// Only if provide input (form file, from interactive user..)
	//       if all random , not necessary this structure, but possible
	protected final InputActionSeqOfActors actorsActions;
	// only if Recolte tresor, Set good for recolt
	protected final Set<MovingActor> collecteurs;
	// how to avoid global ?
	boolean atLeastOneActionPerformed = false;
	
	public MoveAndRecolteService( Carte carte,
								  InputActionSeqOfActors actorsActions ) {
		this.carte = carte; // from here always possible to access actorsOnCarte
		this.actorsActions = actorsActions;
		// problem stubbing a static method
		this.collecteurs = carte.filterActorbyRole( ChercheurTresor.class );
	}
		
	@Override
	public boolean applyMove() {

		atLeastOneActionPerformed = false;
		// group obstable, moving, tryRecolte. in this Processor. Specific to CarteauxTresor requirements
		/* was an idea....
		@FunctionalInterface
		public interface MoveOnCarteProcessor {
			MoveActorPosition process( MoveActorPosition mt );

		}
		MoveOnCarteProcessor mtp = (MoveTriple mt) -> {
										if( carte.checkPositionInCarte( mt.getPositionOrientation()))
											return mt;
										return mt;									  
									};
		*/
		
		// more explicit
		// could be <actor, originalPosition, newPosition> rather than complex (and inefficient) search for retriveing initial position
		// "explictly" modifiable newPosition to able update for intermediate functions
		// or really immutable DS
		//List<MoveActorPosition> tmp = 
		carte.getMoveActorPosition().stream()
			 // <=> .filter(Optional::isPresent).map(Optional::get)
		     .map( this::newPositionFromAction ).flatMap(Optional::stream) 
		     .map( this::updateFlagToContinueSimulation )
		     // if update rotation, always possible, and nothing more to do
			 .filter( t -> { if ( isRotation(t) ) { 
				 				carte.updateActorPosition(t);
				 				return false;
			 				 }
			 				 return true;
						   })
			 // will depend on Carte setup for PBC
			 .map( carte::applyBoundaryCarte ).flatMap(Optional::stream)
			 // specific to CarteAuxTresors (obstacle, movingActor, recolte)
			 .filter( Predicate.not( carte::isAnObstacle ))
			 .filter( carte::isFreeFromOtherMovingPlayers )
			 //.map( t-> { carte.tryToCollectItem(t); return t;} )
			 .map( t-> wrapFunction( carte::tryToCollectItem, t) )
			 // final update if translation
			 .forEachOrdered( carte::updateActorPosition );
		
		return atLeastOneActionPerformed;
	}
	
	protected Optional<MoveActorPosition> newPositionFromAction( MoveActorPosition moveActor ) {		
		return actorsActions.nextAction( moveActor )
				.map( action ->  new MoveActorPosition( 
										moveActor.getActor(), 
						  				action.nextPosition( moveActor.getPositionOrientation() )
								 ));
	}
	
	protected MoveActorPosition updateFlagToContinueSimulation(MoveActorPosition moveActor) {
		atLeastOneActionPerformed = true ;
		return moveActor;
	}
	
	// bad/stupid implementation, better to keep initial Position in MoveActorPosition
	protected boolean isRotation( MoveActorPosition moveActor ) {
		return carte.getMoveActorPosition().stream()
				.filter( t -> moveActor.getActor() == t.getActor() )
				.noneMatch( t -> moveActor.getPositionOrientation().getOrientation()
						        .equals( t.getPositionOrientation().getOrientation() ) );
	}
	
	// utility function, make call a bit more expressive
	static protected MoveActorPosition wrapFunction( Consumer<MoveActorPosition> func, MoveActorPosition t) {
		func.accept( t );
		return t;
	}
}
