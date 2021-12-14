package net.ddns.kimai.explorer.metier.parseinput;

import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.Pair;

// Can contain items from input or call from a random class
// 1. Move some logic in Helper, keep ConfigurationJeu simple POJO (data)
// 2. Need more if want to extend with Random, extends Parser ??
public class ConfigurationJeu {
	
	protected Dimension dimensionCarte;

	// more flexible with a map ? MovingActors.class, Terrain.class, CollectableItems.class as keys ?
	// Map < Terrain, Pair<FixedItem, Position>
	//       colectable, Pair<collectableItem, Position, Nb
	//                  Pair<MovingAction, ActorsPropsInConfiguration>
	protected final ItemsInConfiguration<MovingActor, ActorPropsInConfiguration > actors = new ItemsInConfiguration<>();
	protected final ItemsInConfiguration<FixedItem, Position> landscape = new ItemsInConfiguration<>();
	protected final ItemsInConfiguration<CollectableItem, Position> collectItems = new ItemsInConfiguration<>();
	
	public Dimension getDimensionCarte() {
		return dimensionCarte;
	}
	
	public void setDimensionCarte(Dimension dim) {
		this.dimensionCarte = dim;
	}
			
	public List<Pair<FixedItem, Position>> getFixedItems() {
		//return landscape.getItems();
		return landscape.getPairItems();
	}
	
	public void addFixedItem( FixedItem item, Position position ) {
		landscape.addItem( item, position );
	}
	
	public List<Pair<CollectableItem, Position>> getCollectableItems() {
		return collectItems.getPairItems();
	}
	
/// should not use clone here. caller may do the job
// corrected 
	// strange only called in Test/CarteBuilderTest, configurationProviderTest !!
	// it is used  by code ?
	//public void addCollectableItem( CollectableItem item, Position position, int nbItem ) {
	public void addCollectableItem( CollectableItem item, Position position) {
		collectItems.addItem(item, position);
	}
	
	// Possible also to send ItemsInConfiguration, with convenient API
	// but add coupling throught this class. If coupling only in Builder, it may be fine
	public List<Pair<MovingActor, ActorPropsInConfiguration>> getMovingActors() {
		return actors.getPairItems();
	}
		
	public void addMovingActors(MovingActor actor, ActorPropsInConfiguration move) {
		actors.addItem(actor, move);
	}
	
}
