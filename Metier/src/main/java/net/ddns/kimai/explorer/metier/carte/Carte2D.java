package net.ddns.kimai.explorer.metier.carte;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// Interfaces from simulation package
import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.MoveActorPosition;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.position.Position;

/**
 * Can be seen as a façade ( Interface Carte should be very metier oriented)
 *
 * First error : group Terrain , Tresor(CollectableItems) and Aventuriers
 *  In the same class DOES NOT require too much dependencies.
 *  Until type are interfaces / base class and Inversion of control is done (by DI)
 *  
 * Explorer and Move Service will utilise only the necessary components
 *  with a clear (deep) copy of some of them if necessary for runnong the simulation
 *  
 *  If everything injected in constructor, do not care about 2D or 3D here
 *  CarteWithCollectableItems better
 */

// implementation: style CarteCarteAuxTresors (CarteWithAventurierAndRenderable)
// Carte2D could be Renderable (implements fillFrame)  , allow to be embedded in an other "Frame"
// not bad, but not use yet
public class Carte2D implements Carte {

	protected final int[] dimensions;	
	// static rank = 2, usefull for factory only ?
	// largeurn hauteur not used in construction anymore
	protected int largeur;
	protected int hauteur;
	// with protected (or default package visibility), test written in the same package have access
	// convenient for testing 'Value Type data", but "breaks" visibility
	final ActorsOnCarte actors;	
	final Terrain terrain;
	final CollectableItemsOnCarte tresors;

	// first test, DI better..
	// when usage will be clearer, or completly independant renderer sotred in a controller indeed
	// test outside, keep renderer reference in Controller
	// final Renderer renderer;
	
	// tests only for HashParser, to improve
//	public Carte2D() {
//		dimensions = new  int[] {1,1};
//		largeur = 1;
//		hauteur = 1;
//		this.terrain = new Terrain2DArray<Morphologie>(Morphologie.class, 
//						   							   largeur, hauteur, Morphologie.PLAINE);
//		this.tresors = new CollectableOnCarteMap<>(new HashMap<Position, List<Tresor>>());
//		this.actors = new ActorsOnCarteOrdered();
//		//this.renderer = new RendererCarteAuxTresors(this, actors, terrain, tresors );
//	}
	
	public Carte2D( int dimensions[], 
					ActorsOnCarte actors, 
					Terrain terrain, 
					CollectableItemsOnCarte tresors ) {
		this.dimensions = dimensions;
		this.actors = actors;
		this.terrain = terrain;
		this.tresors = tresors;
		//this.renderer = new RendererCarteAuxTresors( this, actors, terrain, tresors);
	}
	
	@Override
	public int[] getDimensions() { return dimensions; }
	
	@Override
	public int getRank() { return dimensions.length; }
	
	@Override
	public List<MovingActor> getActors() {
		return actors.getActors();
		//return List.copyOf(actors.getActors());
	}
	
	@Override
	public List<MoveActorPosition> getMoveActorPosition() {
		return actors.getActorsPosition().stream()
			         .map( t -> new MoveActorPosition( t.getActor(), t.getPositionOrientation()) )
		             .collect(Collectors.toList());		
	}

	@Override
	public void updateActorPosition(MoveActorPosition movePair) {
		actors.updatePosition( movePair.getActor(), movePair.getPositionOrientation() );
	}

	@Override
	public Optional<MoveActorPosition> applyBoundaryCarte(MoveActorPosition moveActor) {
		return checkInsideCarte( moveActor.getPositionOrientation().getPosition() )
			   ? Optional.of(moveActor)
			   : Optional.empty();
	}
	
	@Override
	public boolean isFreeFromOtherMovingPlayers(MoveActorPosition moveActor) {
		return getMoveActorPosition().stream()
				.filter( actP -> ! actP.getActor().equals( moveActor.getActor()))
				.noneMatch( actP -> actP.getPositionOrientation().getPosition()
				      .equals( moveActor.getPositionOrientation().getPosition()) );
	}
		
	@Override
	public boolean isAnObstacle(MoveActorPosition moveActor) {
		return moveActor.getActor().isAnObstacle(
				terrain.getFixedItem( moveActor.getPositionOrientation().getPosition()));
	}

	// Need cast in fact... Visitor much more "clean" , extensible (interactions between MovingActor)
	@Override
	public void tryToCollectItem(MoveActorPosition moveActor) {		
		tresors.collect( moveActor.getPositionOrientation().getPosition() )
			   .ifPresent( item -> ((ChercheurTresor) moveActor.getActor()).recolteTresor(item) ); 		
	}
	
	@Override
	public <T> Set<MovingActor> filterActorbyRole(Class<T> clazz) {
		return actors.getActors().stream()
				  .filter( k -> {
					  if ( clazz.isInstance( k ) )
						  return true;
					  return false;
					  })
				  .collect(Collectors.toCollection(HashSet::new));
	}
	
	// @Override
	// public int getNbCollectableAt(Position position) {
	//	return tresors.getNbCollectableAt( position );
	// }
	
	// @Override // not used anymore, at least by external API
	// public FixedItem getItemOnTerrainAt(Position position) {
	//	return terrain.getFixedItem(position);
	//}
	
	public boolean throwExceptionIfOutsideTheCarte(Position pos) throws InvalidPositionCarteException {
		if( !checkInsideCarte(pos) )
			throw new InvalidPositionCarteException(
				"Postion " + pos.toString() 
				+ " is outside the Carte " + Arrays.toString(dimensions) );
		return true;
	}
	
	public int getLargeur() {
		return dimensions[0];
	}
	public int getHauteur() {
		return dimensions[1];
	}
	
//	private void setLargeur(int largeur) throws InvalidSizeCarteException {
//		if( largeur <= 0)
//			throw new InvalidSizeCarteException("Invalid Largeur for a Carte: " + largeur);
//		this.largeur = largeur;
//	}

//	private void setHauteur(int hauteur) throws InvalidSizeCarteException {
//	if( hauteur <= 0)
//		throw new InvalidSizeCarteException("Invalid Hauteur for a Carte: " + hauteur);
//	this.hauteur = hauteur;
//}
	
	protected boolean checkInsideCarte(Position pos) {
		if( pos.getCartesianCoordinates()[0] < 0 || pos.getCartesianCoordinates()[0] >= dimensions[0]  
			||  pos.getCartesianCoordinates()[1] < 0 || pos.getCartesianCoordinates()[1] >= dimensions[1] )
			return false;
		return true;
	}

	// access from here or from Renderer, to see at usage
	// A GUI / ConsoleRenderer will need only access to a Renderer
//	@Override
//	public Frame getFrame() {
//		return renderer.renderFrame();
//	}
}