package net.ddns.kimai.explorer.metier.carte;

import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;

/**
 * OnCarte Actor and Position, refer to its role as well. Always 
 * Ordered : Linked in name of implementation . May vary, with use Parallel MoveService 
 * Unicity : Map, Set rather than List in implmentation will depend. Always
 * 
 * Minimize coupling, no knowledge of MoveActorPosition
 *          carte is the adapter for it / should be updated by MoveOnCarte(Service)
 */
public interface ActorsOnCarte {
    // With this interface, both list or map can be implemented
	List<MovingActor> getActors();
	PositionOrientation getPositionOritentation(MovingActor actor);
	List<ActorPosition> getActorsPosition();
	
	void updatePosition( MovingActor actor, PositionOrientation position);
	// Rendering, moved to renderingToFrame interface, only in implementation
	// Frame fillFrame( Frame frame );
}
