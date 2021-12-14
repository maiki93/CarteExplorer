package net.ddns.kimai.explorer.metier.simulation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Carte main interface for MoveService 
 *   Translate MoveActorPosition ActorPosition
 */
public interface Carte {
	// properties of all Carte, base Class ?
	int[] getDimensions();
	int getRank();
	// Set points unicity, but not the ordering  (LinkedHashSet possible implementation)
	List<MovingActor> getActors();
	
	// RendererService refactoring, if we want to put logic outside Carte => need access
	// 1. A rendererService, but need access to Terrain,... fillFrame( frame, STRATEGY)
	//		=> So, getTerrain , getActorsOnCarte... cast to Renderable etc...
	//     Renderer could be a singleton there, it will ASK access to compoentns of Carte
	
	// 2. Carte owns its (Optional) Renderer( CarteAuxTresors) (injected or not?), 
	//     which can be accessed from outside
	//     Delegates and encapsulates. implementation KNOWS what to/ how to implement
	
// Interface for MoveService, mix of Behavior && Data ( certainly needed at some point )
// All need MoveActorService, acts as Adapter in fact or Bridge
	// ArrayList, optimal for loop (efficient speed and memory) vs LinkedHashSet 
	List<MoveActorPosition> getMoveActorPosition();
	void updateActorPosition(MoveActorPosition moveActor); // clear signal of update
	Optional<MoveActorPosition> applyBoundaryCarte(MoveActorPosition moveActor); // PBCRule rule)
	// specific to some cases only ? (CarteWithCollectableItems), 
	// should not be in Carte Interface ? (but need MovingActor and Terrain) ?
	void tryToCollectItem(MoveActorPosition moveActor);	
	boolean isAnObstacle(MoveActorPosition moveActor);
	// or ObstacleRule lambda if we want to be generic and reusable
		// Optional<MoveActorPosition> moveWrtToObstable( MoveActorPosition, ObstacleRule)
	// List<MovingActor> could be more general for interacting Actors
	boolean isFreeFromOtherMovingPlayers(MoveActorPosition moveActor);
	
	/// do not see use for domain model now, add only for convenience ?
// could be in implementation only ?
	// int getNbActors()
	// int getNbCollectableAt( Position position ); Nbcollectabletotal
	// FixedItem getItemOnTerrainAt()
	// perfromance cost to check ?? convenient for builder
	// boolean throwExceptionIfOutsideTheCarte(Position pos) throws InvalidPositionCarteException;
	
	// do not know where to put this, static problematic for stubbing
	<T> Set<MovingActor> filterActorbyRole(Class<T> clazz);

// moved away, to see later
	//Frame getFrame();
}
