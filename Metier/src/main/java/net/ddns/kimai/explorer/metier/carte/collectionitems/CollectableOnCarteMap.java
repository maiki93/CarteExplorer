package net.ddns.kimai.explorer.metier.carte.collectionitems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.rendering.Frame;
import net.ddns.kimai.explorer.metier.rendering.Renderable;
import net.ddns.kimai.explorer.metier.utils.Pair;
// can avoid the dependence to Tresor ?
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.position.Position;

// problem with construction of instance inside a generic
// idea with supplier from this link
// https://stackoverflow.com/questions/75175/create-instance-of-generic-type-in-java

// Implementation class, responsible of its internal representation
// 1. expects the correct format for this implementation
// 2. expects a "standart" List< Pair<I,P> > and transform into internal representation 
//     => more responsabilities, fine if ONLY 1 standart

// 3 A mixin  : StandarTransformation in Static class / function, List< Pair > > will come from Configuration Jeu
public class CollectableOnCarteMap<T extends CollectableItem> implements CollectableItemsOnCarte, Renderable {  

	private final Map<Position, List<T>> tresors;

	// corect format 
	public CollectableOnCarteMap( Map<Position, List<T>> tresors ) {
		this.tresors = tresors;
	}
	
	// If all accept this format, factorize a lot the creation
	public CollectableOnCarteMap( List< Pair<T,Position> > tresors ) {
		this.tresors = tresors.stream()
				       .collect(Collectors.groupingBy( Pair::value, //Pair<CollectableItem,Position>::value
				    		   						   Collectors.mapping( pair -> pair.item(), Collectors.toList() )));
	}
		
	@Override
	public Optional<CollectableItem> collect(Position position) {		
		var listTresor = tresors.get(position);	 
		if( (listTresor != null ) && (! listTresor.isEmpty())  ) {
			//return Optional.ofNullable( listTresor.remove(0) );
			Optional<CollectableItem> item = Optional.of( listTresor.remove(0) );
			removeEntryIfEmptyItems(position, listTresor);
			return item;
		}
		return Optional.empty();		
	}
	
	private void removeEntryIfEmptyItems(Position position, List<T> listTresor) {
		if ( listTresor.isEmpty() )
			tresors.remove(position);
	}
	
	// TODO improve with stream() ! and correct unchecked if possible
	// signature convenient for reading the input file
	// but name not clear setTresor(Tresor, position) expected
	@SuppressWarnings("unchecked")
	public void addNbTresorAt(Position position, int nbTresor) {
		
		if ( tresors.containsKey(position) ) {
			var listTresor = tresors.get(position);
			listTresor.add((T) new Tresor());
		} else {
			var listTresor = new ArrayList<T>();
			for(int i =0; i < nbTresor; i++ ) {
				listTresor.add((T) new Tresor() );
				tresors.put(position, listTresor);
			}
		}
	}

	@Override
	public Frame fillFrame(Frame frame, Frame.REPLACEMENT strategyReplace) {
		tresors.entrySet().stream()
			   .forEach( entry -> frame.setFrame( formatTresor( entry.getValue().size()), 
					   							  entry.getKey(),
					   							  Frame.REPLACEMENT.APPEND_AFTER ));
		return frame;
	}
	
	@Override
	public void fillFrameWithLambda(BiConsumer<String, Position> fillProcess) {
		// TODO Auto-generated method stub
		tresors.entrySet().stream()
		   .forEach( entry -> fillProcess.accept( formatTresor( entry.getValue().size()), 
				   							      				entry.getKey() ));
	}
	
	// Should be in Tresor !, same as actor  T.formatT
	private static String formatTresor( int nb ) {
		return "T(" + nb + ")";
	}

	//@Override
	//	public Optional<CollectableItem> collect(Position position) {
//		return Optional.ofNullable(
//				tresors.get(position) >
//				);
//				//tresors.get(position).remove(0) );
//				
//		//return Optional.ofNullable(
//		//		tresors.computeIfPresent(position, 
//		//								 (k,v) -> v.remove(0) ));
//		//return Optional.empty();
//	}

//	@Override
//	public Frame fillFrame2(Frame frame, BiConsumer<String, Position> fillProcess) {
//		// TODO Auto-generated method stub
//		tresors.entrySet().stream()
//		   .forEach( entry -> fillProcess.accept( formatTresor( entry.getValue().size()), 
//				   							      entry.getKey() ));
//		return frame;
//	}
	
	
//	public int getNbCollectableAt(Position position) {
//		if( tresors.containsKey(position)) {
//			return tresors.get(position).size();
//		}
//		return 0;
//	}
}
