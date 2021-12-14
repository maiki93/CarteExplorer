package net.ddns.kimai.explorer.metier.carte.item;

import net.ddns.kimai.explorer.metier.carte.FixedItem;

// wrong Morphologie decrit le terrain pas de notion d'obstacle
//      (obstacle for some MovingsActors)
// in fact used this only because  Morphologie is an impelmentation
//   => need an other Interface Terrain (Morhpologie, one possible description du terrain)
// in fact, all objects fixed on a carte (une Tour pour voir les voleurs et cannibales)

// aventurier/movingPlayer doit pouvor définir ses obstacles
// supplier, test for HashParser
public enum Morphologie implements FixedItem /*, Supplier<Morphologie>*/ {
	
	// need  body, for use with Class<T> in FactoryItem. with body Class = Morphologie$1,2
	PLAINE("P") { public String toString2() {return "PLAINE";} } 
//		@Override
//		public Morphologie get() {
//			return this;
//		}
	//}
	,
	MONTAGNE("M") { public String toString2() {return "MONTAGNE";} };
//		@Override
//		public Morphologie get() {
//			return this;
//		}
//	}
	//;
	
	//public abstract String toString();
	
	private String letter;
	private Morphologie(String str) {
		letter = str;
	}
	
	public String getLetter() {
		return letter;
	}
	
	static public Morphologie create(String name) {
		return Morphologie.valueOf(name);
	}
	
	// easy to use in Array2D, overridde normal toString() of Morphologie, maybe not nice
	@Override
	public String toString() {
		return getLetter();
	}
	
	
	public abstract String toString2();

	
	// allow to retrieve the "static" instance from the Class stored in FactoryItem
//	static public Morphologie fromClass(Class<? extends Morphologie> clazz) {
//		for( var list : Morphologie.values() ) {
//			if( list.getClass() == clazz ) {
//				return list;
//			}
//		}
//		//return Morphologie.MONTAGNE;
//		return null;
//	}
	
	// it is working, but interface very different of others Item
	static public Morphologie fromClass(Class<?> clazz) {
		for( var list : Morphologie.values() ) {
			if( list.getClass() == clazz ) {
				return list;
			}
		}
		//return Morphologie.MONTAGNE;
		return null;
	}
	
	// try generic interface for creation
	//static public Morphologie fromClass(Class<?> clazz) {

	static public <T extends FixedItem> Morphologie createItem(Class<T> clazz) {
		for( var list : Morphologie.values() ) {
			if( list.getClass() == clazz ) {
				return list;
			}
		}
		//return Morphologie.MONTAGNE;
		return null;
	}
	
}

// TourObservation implements FixedOnCarte
