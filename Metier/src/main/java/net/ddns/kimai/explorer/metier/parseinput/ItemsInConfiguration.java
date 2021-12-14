package net.ddns.kimai.explorer.metier.parseinput;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import net.ddns.kimai.explorer.metier.utils.Pair;

// Generalization of TresorOnCarte, can replace ?
// Most Generic List< Pair<Item,Position> >  : no logic/business just want to store objects, keep order by default
// Keep order
// Map does not work if static (Morphologie Enum)
// Tresor problem with doublon, ok in fact

// Expects Item, Position
class ItemsInConfiguration<I, V> {
	
	// Positon, List<T> not a bad idea, enforce Postion => 2 tresors 
	//private HashMap<Position, LinkedList<T>> tresors = new HashMap<>();
	//private Map< I, P > items = new LinkedHashMap<>();
	private List< Pair<I,V> > items = new LinkedList<>();

	// tricky with Morphologie, it is static object
	// no check...
	public void addItem(I item, V value) {
		items.add(  new Pair<I, V>(item, value) );
	}
	
	List<Pair<I,V>> getPairItems() {
		return items;
	}
	
	Stream<Pair<I,V>> stream() {
		return items.stream();
	}
	
	// Visitor Pattern only pair with a compatible Key could be inserted 
	// insert( Pair<K,W> )
	//  if K,W equals this.add pair ??
}
