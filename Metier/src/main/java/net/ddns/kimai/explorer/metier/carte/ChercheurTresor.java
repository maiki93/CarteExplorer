package net.ddns.kimai.explorer.metier.carte;

import java.util.List;

public interface ChercheurTresor {

	void recolteTresor(CollectableItem tresorFound);
	// those actors have a backpack of collecteditems.
	// to put in this interface ??
	//    or with visior pattern, to add in MovingActors ? 
	//int nbOfItemCollected();
	List<CollectableItem> backpack();
}
