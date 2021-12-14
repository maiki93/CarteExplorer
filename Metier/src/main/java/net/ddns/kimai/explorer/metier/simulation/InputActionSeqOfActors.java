package net.ddns.kimai.explorer.metier.simulation;

import java.util.Map;
import java.util.Optional;

public class InputActionSeqOfActors {
	
	private final Map<MovingActor, ActionSequence> mapActions;
	
	public InputActionSeqOfActors( Map<MovingActor, ActionSequence> mapActorActions) {
		this.mapActions = mapActorActions;
	}
	
	public Optional<MovingAction> nextAction(MoveActorPosition movePair) {
		return mapActions.get(movePair.getActor()).nextAction();
	}
}
