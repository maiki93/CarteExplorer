package net.ddns.kimai.explorer.metier.simulation;

// BinaryOperator Position + Action => newPosition

@FunctionalInterface
public interface MoveOnCarteProcessor {
	MoveActorPosition process( MoveActorPosition mt );

}
