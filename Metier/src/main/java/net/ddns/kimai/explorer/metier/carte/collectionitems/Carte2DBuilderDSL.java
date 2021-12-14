package net.ddns.kimai.explorer.metier.carte.collectionitems;

// only interface
import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.utils.Dimension;
// if moved to Carte, need import BuilderDSL
// here allow to make other the builder DSL package private ? avoid bad use
import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
import net.ddns.kimai.explorer.metier.carte.Carte2D;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.Terrain;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;

// keep visible fields only for testing purpose
public class Carte2DBuilderDSL {
	
	final Dimension dimension;
	ActorsOnCarte actors = new ActorsOnCarteOrdered();
	Terrain terrain;
	CollectableItemsOnCarte tresors;
	
	// wrapped object to be created, only created before returning it
	//Carte carte;
		
	private Carte2DBuilderDSL( Dimension dim) {
		this.dimension = dim;
	}
	
	public static Carte2DBuilderDSL newCarte2D( int largeur, int hauteur) {
		return new Carte2DBuilderDSL( Dimension.of( largeur, hauteur) );
	}
	
	public MovingActorsBuilderDSL withMovingActors() {
			return new MovingActorsBuilderDSL(this);
	}
		
	// nice ! no need to pass a clazz explicitly
//	public <T extends FixedItem> Terrain2DArrayBuilderDSL<T> withTerrain2DArray( T defaultType ) {
//		return new Terrain2DArrayBuilderDSL<T>( this, dimension, defaultType );
//	}
	
	public <T extends FixedItem, C extends T> Terrain2DArrayBuilderDSL<T> withTerrain2DArray( Class<T> clazz, T defaultType ) {
		return new Terrain2DArrayBuilderDSL<T>( this, clazz, dimension, defaultType );
	}
	
	public <T extends CollectableItem> CollectItemsBuilderDSL<T> withCollectableItems(Class<T> clazz) {
		return new CollectItemsBuilderDSL<T>( this );
	}
	
	public void setActors( ActorsOnCarte actors ) {this.actors = actors;}
	public void setTerrain( Terrain terrain ) {this.terrain = terrain; }
	public void setCollectableItems( CollectableItemsOnCarte tresors ) {this.tresors = tresors;}
	
	public Carte build() {
		Carte carte = new Carte2D(dimension.toArray(), actors, terrain, tresors);
		return carte;
	}
}
