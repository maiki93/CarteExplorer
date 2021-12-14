package net.ddns.kimai.explorer.metier.simulation;

import net.ddns.kimai.explorer.metier.position.PositionOrientation;

public interface MovingAction {
	PositionOrientation nextPosition(PositionOrientation actualPosition);
	
	// can use static method in interface ?
}
