package net.ddns.kimai.explorer.metier.builders;

import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
// very bound to Carte
import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.InvalidSizeCarteException;
import net.ddns.kimai.explorer.metier.carte.Terrain;

import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.utils.Pair;

public interface FactoryComponent {

	Carte createCarte( /*int[] arrayDimension,*/ 
					  ActorsOnCarte actors, 
					  Terrain terrain, 
					  CollectableItemsOnCarte tresors) throws InvalidSizeCarteException;

	// unification of interface, for Ramdom Obstable, still need default. Dimension provided by Factory
	// need further explicit call to setItem, can forget to do it.
	<T extends FixedItem> Terrain createTerrain(Class<T> clazz, T defaultType );
	
	<T extends CollectableItem> CollectableItemsOnCarte
									createCollectables( List<Pair<T, Position>> tresors );

	ActorsOnCarte createActors( List< Pair<MovingActor, PositionOrientation>> actorsPosition);
}
