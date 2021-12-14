package net.ddns.kimai.explorer.metier.carte;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.simulation.MoveActorPosition;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;
 
@ExtendWith(MockitoExtension.class)
class CarteTest {
	// @Spy // why not working with isAnObstacle / isFreeFromOther ? 
	private Aventurier lara = new Aventurier("lara");
	private Aventurier other = new Aventurier("indiana");
	
	private final MoveActorPosition maOutside = 
							new MoveActorPosition(lara, 
												  PositionOrientation2D.create(3, 4, 'E'));
	// @Spy here working maInside.getActor()
	private final MoveActorPosition maInside = 
							new MoveActorPosition(lara, 
												  PositionOrientation2D.create(1, 1, 'E'));
	
	private List<ActorPosition> listActorDifferentPlace =
			Arrays.asList(  
				new ActorPosition(lara, PositionOrientation2D.create(1, 1, 'N')),
				new ActorPosition(other, PositionOrientation2D.create(2, 1, 'N')));
				
	private List<ActorPosition> listActorsSamePlace =
			Arrays.asList(  
				new ActorPosition(lara, PositionOrientation2D.create(1, 1, 'N')),
				new ActorPosition(other, PositionOrientation2D.create(1, 1, 'E')));
	
	// Mockito can construct carte by injection with only this ?
	// @Spy or Mock => error final class, make explicit constructor in init()
	int[] dimensions = new int[] { 3,4 };
	@Mock
	ActorsOnCarte actors;
	@Mock
	Terrain terrain;
	@Mock
	CollectableItemsOnCarte tresors;
	// under test
	Carte2D carte;
	
	@BeforeEach
	void init() {
		carte= new Carte2D( dimensions, actors, terrain, tresors );
	}
	
	@Test
	void updateActorPositionCallsActorsOnCarte() {
		// when
		carte.updateActorPosition(maInside);
		verify( actors ).updatePosition( maInside.getActor(), maInside.getPositionOrientation());
	}
	
	@Test
	void applyBoundaryOutsideCarteReturnsEmptyOptional() {
		// given
		// when
		Optional<MoveActorPosition> optMove = carte.applyBoundaryCarte( maOutside );
		assertFalse(optMove.isPresent());
	}
	
	@Test
	void applyBoundaryInsideCarteReturnsOptionalwithSameInput() {
		// given
		// when
		Optional<MoveActorPosition> optMove = carte.applyBoundaryCarte( maInside );
		assertTrue(optMove.isPresent());
		assertThat( optMove.get() ).isEqualTo( maInside );
	}
	
	@Test
	void isAnObstacleTrue() {
		// when
		when(terrain.getFixedItem( maInside.getPositionOrientation().getPosition() ))
					.thenReturn( Morphologie.MONTAGNE);
		// then
		boolean test = carte.isAnObstacle(maInside);
		// no need to verify call to getFixedItem , error if not called
		
		// why not working with lara as a spy ??
		// verify( maInside ).getActor(); // working...
		// verify( lara ).isAnObstacle( any() ); // not working...
		assertTrue(test);
	}
	
	@Test
	void isAnObstacleFalse() {
		// when
		when(terrain.getFixedItem( maInside.getPositionOrientation().getPosition() ))
					.thenReturn( Morphologie.PLAINE);
		// then
		boolean test = carte.isAnObstacle(maInside);
		assertFalse(test);
	}
	
	// how to test this, ha yes.
	@Test
	void isFreeFromOtherActorFalse() {
		when(actors.getActorsPosition()).thenReturn(listActorsSamePlace);
		
		boolean test = carte.isFreeFromOtherMovingPlayers(maInside);
		assertFalse(test);
	}
	
	@Test
	void isFreeFromOtherActorTrue() {
		when(actors.getActorsPosition()).thenReturn(listActorDifferentPlace);
		
		boolean test = carte.isFreeFromOtherMovingPlayers(maInside);
		assertTrue(test);
	}
	
	@Test
	void tryToCollectItemWithATresor() {
		when( tresors.collect( any(Position.class))).thenReturn( Optional.of(new Tresor()));
		
		carte.tryToCollectItem(maInside);
		//verify(lara).recolteTresor(any(Tresor.class));
		assertThat( lara.backpack().size() ).isEqualTo(1);
	}
	
	@Test
	void tryToCollectItemWithoutTresor() {
		when( tresors.collect( any(Position.class))).thenReturn( Optional.empty());
		
		carte.tryToCollectItem(maInside);
		assertThat( lara.backpack().size() ).isEqualTo(0);
	}
	
// Rendering Frame
//	@Test
//	void getFrameCallFunctionOfComponents() {
//		carte.getFrame();
//		verify( terrain ).fillFrame( any(Frame.class) );
//		verify( tresors ).fillFrame( any(Frame.class) );
//		verify( actors ).fillFrame( any(Frame.class) );
//	}
	
	// Not running, cannot cast 
//	@Test
//	void getFrameCallFunctionOfComponents() {
//		carte.getFrame(); //error here
//		verify( (RenderingToFrame)terrain ).fillFrame( any(Frame.class), any(Frame.REPLACEMENT.class) );
//		verify( (RenderingToFrame)tresors ).fillFrame( any(Frame.class), any(Frame.REPLACEMENT.class) );
//		verify( (RenderingToFrame)actors ).fillFrame( any(Frame.class), any(Frame.REPLACEMENT.class) );
//	}
	
	// Something is wrong in the design
//	@Test
//	void getFrameCallFunctionOfComponents() {
//		carte.getFrame(); // error here ! Terrain cannot be cast to RenderingtoFrame
//		//verify( carte.renderer ).renderFrame();
//	}
}
