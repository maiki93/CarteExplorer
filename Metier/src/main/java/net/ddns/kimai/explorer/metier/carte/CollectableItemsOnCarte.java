package net.ddns.kimai.explorer.metier.carte;

import java.util.Optional;
import net.ddns.kimai.explorer.metier.position.Position;

// Tresors in backpack could also be stolen (but no position required)
public interface CollectableItemsOnCarte {
	
	Optional<CollectableItem> collect(Position position);
}
