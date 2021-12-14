package net.ddns.kimai.explorer.metier.carte.collectionitems;

import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.utils.Dimension;

// More "generic", but not so much tested (only Tresor)
// many warning to suppress... to check if necessary
public class Terrain2DArrayBuilderDSL<T extends FixedItem> {
	
	private final Carte2DBuilderDSL carteBuilder;
	// wrapped object
	//private final Terrain2DArray<Morphologie> terrain;
	private final Terrain2DArray<T> terrain;

// Inner class of Terrain2D possible ?	
//	public Terrain2DArrayBuilderDSL( Carte2DBuilderDSL carteBuilder, 
//									 int [] dimensions, Morphologie defaultType ) {
//		this.carteBuilder = carteBuilder;
//		this.terrain = new Terrain2DArray<Morphologie>( Morphologie.class, 
//						dimensions[0], dimensions[1], defaultType);
//	}
	
	// @SuppressWarnings("unchecked")
	public Terrain2DArrayBuilderDSL( Carte2DBuilderDSL carteBuilder, 
			 						 Class<T> clazz, Dimension dim, T defaultType ) {
			 this.carteBuilder = carteBuilder;
//			 this.terrain = new Terrain2DArray<T>( (Class<T>) defaultType.getClass(), //.getDeclaringClass(),  
//					 							   dim, defaultType);
			 this.terrain = new Terrain2DArray<T>( clazz, //.getDeclaringClass(),  
					   							   dim, defaultType);
}
	
	public Terrain2DArrayBuilderDSL<T> montagneAt( Position position ) {
		terrain.setFixedItem(position, Morphologie.MONTAGNE);
		return this;
	}
	
	public Carte2DBuilderDSL endTerrain() {
		carteBuilder.setTerrain( terrain );
		return carteBuilder;
	}

}
