package net.ddns.kimai.explorer.metier.movement;

import net.ddns.kimai.explorer.metier.simulation.MovingAction;
import net.ddns.kimai.explorer.metier.position.Orientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

public class TournerGauche implements MovingAction {

	//public static final Character 
	public static final TournerGauche GAUCHE = new TournerGauche();
	//static {
	//	ActionFactory.addAction('G', GAUCHE);
	//}
	
	@Override
	public PositionOrientation nextPosition(PositionOrientation actual) {
		Orientation orient = actual.getOrientation();
		return new PositionOrientation2D( actual.getPosition(), orient.tourneGauche());
	}

}
