package net.ddns.kimai.explorer.metier.simulation;

import java.util.Objects;

import net.ddns.kimai.explorer.metier.position.PositionOrientation;

// used  for streaming and chaining calls, contains all infos
// only in MoveService and co.
// could add initial Position, make more flexible for Move algo.
public class MoveActorPosition {

	private final MovingActor actor;
	private final PositionOrientation positionOrientation;
	
	public MoveActorPosition( MovingActor actor, 
					   		  PositionOrientation positionOrientation
					   		) {
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
		MoveActorPosition other = (MoveActorPosition) obj;
		return Objects.equals(actor, other.actor) && Objects.equals(positionOrientation, other.positionOrientation);
	}
	
	public String toString() {
		return actor.getNom() + ", " + positionOrientation;
	}

}
