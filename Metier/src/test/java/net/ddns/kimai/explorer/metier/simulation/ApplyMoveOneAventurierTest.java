package net.ddns.kimai.explorer.metier.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import net.ddns.kimai.explorer.metier.carte.ChercheurTresor;
import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.movement.Avancer;
import net.ddns.kimai.explorer.metier.movement.TournerGauche;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

@ExtendWith(MockitoExtension.class)
public class ApplyMoveOneAventurierTest {

//	static final Carte carte2d45 = Carte2DBuilderDSL
//									.newCarte2D(4, 5)
//										.withTerrain2DArray(Morphologie.PLAINE)
//											.montagneAt( Position2D.create(1,1) )
//										    .endTerrain()
//										.withMovingActors()
//											// need withAventurier( aventurier )
//											.withAventurier( "lara", PositionOrientation2D.create(1, 0, 'S'), "AGDA" )
//											.endMovingActors()
//									.build();
	
	// dependencies to MoveService
	@Mock
	Carte carte;
	@Mock
	InputActionSeqOfActors actorsSim;
	// class under test
	private MoveAndRecolteService service;
	
	// to reconstruct everytime
	private final PositionOrientation2D initialPosition = PositionOrientation2D.create(1, 1,'E');
	private final PositionOrientation2D newPositionAfterAvance = PositionOrientation2D.create(2, 1,'E');
	private final PositionOrientation2D newPositionAfterTurnLeft = PositionOrientation2D.create(1, 1,'N');
	
	private final LinkedList<MoveActorPosition> actorPositionList = new LinkedList<>();
	private final Aventurier lara = new Aventurier("lara");
	// used for assertion
	private final MoveActorPosition newPositionPairAfterAvance = new  MoveActorPosition(lara, newPositionAfterAvance);
	private final MoveActorPosition newPositionPairAfterTurnLeft = new  MoveActorPosition(lara, newPositionAfterTurnLeft);
	
	// return value of applyMove
	boolean atLeastOneActionPerformed;
	
	@BeforeEach
	void initializeOneAventurier() {
		MoveActorPosition mapLara = new MoveActorPosition(lara, initialPosition );
		actorPositionList.add(mapLara);
		// helper : always link to the map
		when( carte.getMoveActorPosition() ).thenReturn( actorPositionList );
		//when( carte.getActors() ).thenReturn( List.of(lara) );
		when( carte.filterActorbyRole( ChercheurTresor.class )).thenReturn( Set.of(lara));
		// problem mocking static
		// when( FilterActor.byRole( ChercheurTresor.class, any(List.class)) ).thenReturn( Set.of(lara));
		// never managed to run
//		try( MockedStatic<FilterActor> mockedS = mockStatic(FilterActor.class)) {
//			mockedS.when( () -> FilterActor.byRole( ChercheurTresor.class, any(List.class) ) )
//				   .thenReturn( Set.of(lara) );
		service = new MoveAndRecolteService(carte, actorsSim);
	}
	
	@Test
	void noActionToPerformThenNoUpdateAndReturnFalse() {
		// Given no action
		when( actorsSim.nextAction( any() )).thenReturn( Optional.empty() );
		// when
		atLeastOneActionPerformed = service.applyMove();
		// then
		verify( carte, never() ).updateActorPosition( any(MoveActorPosition.class) );
		assertFalse( atLeastOneActionPerformed );
		// first test if there was an action
		verify( carte, never() ).applyBoundaryCarte( any(MoveActorPosition.class) );
	}
	
	@Test
	void oneActionAllTestAccepted() {
		// given
		when( actorsSim.nextAction( any() )).thenReturn( Optional.of( Avancer.AVANCER ) );
		when( carte.applyBoundaryCarte(any(MoveActorPosition.class)) ).thenReturn( Optional.of(newPositionPairAfterAvance) );		
		when( carte.isAnObstacle( any(MoveActorPosition.class) )).thenReturn( false );
		when( carte.isFreeFromOtherMovingPlayers(any(MoveActorPosition.class))).thenReturn( true );
		
		// when
		atLeastOneActionPerformed = service.applyMove();		
		// then, comparison with newPositionPair need hashcode and equals
		verify( carte ).applyBoundaryCarte( newPositionPairAfterAvance );
		verify( carte ).tryToCollectItem(newPositionPairAfterAvance);
		assertThatMoveAccepted();
	}
	
	@Test
	void refusedIfOutsideCarte() {
		when( actorsSim.nextAction( any() )).thenReturn( Optional.of( Avancer.AVANCER ) );
		when( carte.applyBoundaryCarte(any(MoveActorPosition.class)) ).thenReturn( Optional.empty() );
		
		atLeastOneActionPerformed = service.applyMove();
		
		verify( carte ).applyBoundaryCarte( newPositionPairAfterAvance );
		verify( carte, never() ).isAnObstacle(newPositionPairAfterAvance);
		assertThatMoveRefused();
	}
	
	@Test
	void refusedIfMeetAnObstacle() {
		when( actorsSim.nextAction( any() )).thenReturn( Optional.of( Avancer.AVANCER ) );
		when( carte.applyBoundaryCarte(any(MoveActorPosition.class)) ).thenReturn( Optional.of(newPositionPairAfterAvance) );
		when( carte.isAnObstacle( any(MoveActorPosition.class) )).thenReturn( true );
		
		atLeastOneActionPerformed = service.applyMove();
		
		verify( carte ).isAnObstacle( newPositionPairAfterAvance );
		verify( carte, never() ).isFreeFromOtherMovingPlayers(newPositionPairAfterAvance);
		assertThatMoveRefused();
	}
	
	@Test
	void refusedIfAnOtherActorIsPresent() {
		when( actorsSim.nextAction( any() )).thenReturn( Optional.of( Avancer.AVANCER ) );
		when( carte.applyBoundaryCarte(any(MoveActorPosition.class)) ).thenReturn( Optional.of(newPositionPairAfterAvance) );
		when( carte.isFreeFromOtherMovingPlayers(any(MoveActorPosition.class))).thenReturn( false );
		
		atLeastOneActionPerformed = service.applyMove();
		
		assertThatMoveRefused();
	}
	
	@Test
	void noTryToRecolteAfterTurnLeft() {
		when( actorsSim.nextAction( any() )).thenReturn( Optional.of( TournerGauche.GAUCHE ) );

		atLeastOneActionPerformed = service.applyMove();
		
		verify( carte, times(1) ).updateActorPosition( newPositionPairAfterTurnLeft );
		verify( carte, never() ).tryToCollectItem( any(MoveActorPosition.class) );
	}
	
	private void assertThatMoveAccepted() {
		verify( carte, times(1) ).updateActorPosition( newPositionPairAfterAvance );
		assertTrue( atLeastOneActionPerformed );
	}
	
	private void assertThatMoveRefused() {
		verify( carte, never() ).updateActorPosition( any(MoveActorPosition.class) );
		verify( carte, never() ).tryToCollectItem( any(MoveActorPosition.class) );
		assertTrue( atLeastOneActionPerformed );
	}
}
