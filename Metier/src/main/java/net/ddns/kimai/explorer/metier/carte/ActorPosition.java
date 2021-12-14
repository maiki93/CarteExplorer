package net.ddns.kimai.explorer.metier.carte;

import java.util.Objects;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;

// It is "almost the same" than MoveActorPosition, but independent and different use
public class ActorPosition {

	private final MovingActor actor;
	private final PositionOrientation positionOrientation;
	
	public ActorPosition( MovingActor actor, 
			 			  PositionOrientation positionOrientation ) {
		this.actor = actor;
		this.positionOrientation = positionOrientation;			
	}
	
	public MovingActor getActor() {
		return actor;
	}

	public PositionOrientation getPositionOrientation() {
		return positionOrientation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actor, positionOrientation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActorPosition other = (ActorPosition) obj;
		return Objects.equals(actor, other.actor) && Objects.equals(positionOrientation, other.positionOrientation);
	}
	
	public String toString() {
		return actor + ", " + positionOrientation;
	}
}
