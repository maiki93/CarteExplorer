package net.ddns.kimai.explorer.metier.carte.collectionitems;

import java.util.HashMap;
import java.util.List;

import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.position.Position;

public class CollectItemsBuilderDSL<T extends CollectableItem> {
	
	private final Carte2DBuilderDSL carteBuilder;
	private final CollectableOnCarteMap<T> tresors;
	
	public CollectItemsBuilderDSL(Carte2DBuilderDSL carteBuilder) {
		this.carteBuilder = carteBuilder;
		this.tresors = new CollectableOnCarteMap<T>( new HashMap<Position, List<T>>() );
	}
	
	public CollectItemsBuilderDSL<T> tresorAt( Position position ) {
		tresors.addNbTresorAt( position, 1);
		return this;
	}
	
	public Carte2DBuilderDSL endCollectableItems() {
		carteBuilder.setCollectableItems( tresors );
		return carteBuilder;
	}

}
