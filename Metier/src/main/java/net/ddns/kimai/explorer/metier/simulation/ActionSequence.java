package net.ddns.kimai.explorer.metier.simulation;

import java.util.Optional;

// moved here for cohersivity, no dependent of other layouts
public interface ActionSequence {

	public Optional<MovingAction> nextAction();
}
