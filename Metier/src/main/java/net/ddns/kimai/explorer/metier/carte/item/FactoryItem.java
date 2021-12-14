package net.ddns.kimai.explorer.metier.carte.item;

import java.util.Map;

import net.ddns.kimai.explorer.metier.carte.Carte2D;

// Moved here, access public... normal
// allow to limit the scope of default constructor of Items
public class FactoryItem {

	// static final, not necessary
	// Morpholotigie must implement Supplier<T> !!
	// Default constructor must be public !! change for Aventureir (rearrange package, expand supplier...)
	// Supplier<?> important ?
	// or use Class<?> Tresor
//	static Map< String, Supplier<?> > mapFactoryObject = Map.of(
//		// nothing to create, just set dimensions
//	    // "C", Carte2D::new, // carte2D necessary => need factory
//		"T" , Tresor::new,  
//		"M" , Morphologie.create("MONTAGNE"),
//		"A", Aventurier::new
//	);
	
	static Map< String, Class<?> > mapFactoryObject = Map.of(
			// nothing to create, just set dimensions
		    // "C", Carte2D::new, // carte2D necessary => need factory
			"C" , Carte2D.class, // ca passe, but which use
			//"C",  Carte.class,
			"T" , Tresor.class,  
			//"M" , Morphologie.create("MONTAGNE"),
			"M" , Morphologie.MONTAGNE.getClass(), // from IDE Class<? extends Morphologie> , so FixedItem ??
			"A", Aventurier.class
		);

		
//	static public Supplier<?> getSupplier(String name) {
//		return mapFactoryObject.get(name);
//	}
	static public Class<?> getSupplier(String name) {
		return mapFactoryObject.get(name);
	}
	
	// could try to implement here, not nice for the return type
	// or in each class, but tricky also
	//static Object createItem(Class<?> clazz) {
	//	return new Object();
	//} //done in DataEntry

}
