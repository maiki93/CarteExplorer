package net.ddns.kimai.explorer.metier.movement;

import net.ddns.kimai.explorer.metier.simulation.MovingAction;
import net.ddns.kimai.explorer.metier.position.Orientation;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

public class Avancer implements MovingAction {

	public static final Avancer AVANCER = new Avancer();
		
	@Override
	public PositionOrientation nextPosition(PositionOrientation actualPosition) {
		Orientation orient = actualPosition.getOrientation();
		Position newPosition = orient.avance(actualPosition.getPosition());
		return new PositionOrientation2D( newPosition, orient);
	}

}
