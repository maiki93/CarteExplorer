package net.ddns.kimai.explorer.metier.simulation;

import net.ddns.kimai.explorer.metier.carte.FixedItem;

public interface MovingActor {

	boolean isAnObstacle(FixedItem objet);
	void setNom(String nom); // ?? see with builder
	String getNom();
	// used for rendering only, break a bit the modularity with strong and hidden coupling
	// a formatToStringFrame maybe a generic interface for all items...
	String formatFrame();
}
