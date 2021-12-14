package net.ddns.kimai.explorer.metier.parseinput;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;

// only ony in ConfigurationJeu, then it is splitted
public class ActorPropsInConfiguration {

	protected PositionOrientation positionOrientation;
	protected ActionSequence actions;
	
	public ActorPropsInConfiguration( PositionOrientation posOrient, ActionSequence actions) {
		this.positionOrientation = posOrient;
		this.actions = actions;
	}
	
	public ActionSequence getActionSequence() {
		return actions;
	}

	public PositionOrientation getPositionOrientation() {
		return positionOrientation;
	}

}
