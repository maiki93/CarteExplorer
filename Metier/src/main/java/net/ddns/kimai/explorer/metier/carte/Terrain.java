package net.ddns.kimai.explorer.metier.carte;

import net.ddns.kimai.explorer.metier.position.Position;

public interface Terrain {
	FixedItem getFixedItem(Position position);
	// not sure it is needed in interface, yes for builder
	<T extends FixedItem> void setFixedItem(Position pos, T item);
}
