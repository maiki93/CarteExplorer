package net.ddns.kimai.explorer.metier.builders;

import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
import net.ddns.kimai.explorer.metier.carte.Carte2D;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.InvalidSizeCarteException;
import net.ddns.kimai.explorer.metier.carte.Terrain;

import net.ddns.kimai.explorer.metier.carte.collectionitems.ActorsOnCarteOrdered;
import net.ddns.kimai.explorer.metier.carte.collectionitems.CollectableOnCarteMap;
import net.ddns.kimai.explorer.metier.carte.collectionitems.Terrain2DArray;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.Pair;

// aLl this could be generic, always the List< Pair<> >
// start using generic Pair<I,V>
public class FactoryComponent2D implements FactoryComponent {

	// Possible to store Dimension at construction
	private final Dimension dim;
	
	public FactoryComponent2D( Dimension dimension ) {
		this.dim = dimension;
	}
	@Override
	public Carte createCarte( /*int[] dimension,*/ 
							  ActorsOnCarte actors, 
							  Terrain terrain, 
							  CollectableItemsOnCarte tresors) throws InvalidSizeCarteException {
		return new Carte2D( dim.toArray(), actors, terrain, tresors );
	}

	// interface different for Terrain, dimensions are needed and a default value
	@Override
	public <T extends FixedItem> Terrain createTerrain(Class<T> clazz, T typeDefault ) {
		return new Terrain2DArray<T>( clazz, dim , typeDefault);
	}
		
	@Override
	public <T extends CollectableItem> CollectableItemsOnCarte createCollectables(List<Pair<T, Position>> tresors) {
		return new CollectableOnCarteMap<T>( tresors );
	}

	// <T extends MovingActor> not needed. for others ?
	@Override
	public ActorsOnCarte createActors(List<Pair<MovingActor, PositionOrientation>> actorsPosition) {
		return new ActorsOnCarteOrdered ( actorsPosition );
	}
}
