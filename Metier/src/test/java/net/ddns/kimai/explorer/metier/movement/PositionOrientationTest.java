package net.ddns.kimai.explorer.metier.movement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.position.Orientation;
import net.ddns.kimai.explorer.metier.position.Orientation2D;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;


// notes
// https://blog.codeleak.pl/2013/07/3-ways-of-handling-exceptions-in-junit.html


/* Nomenclature E00 direction Est, position(0,0) */
class PositionOrientationTest {

	private static final Position defaultPos = new Position2D(0,0);
	private static final Orientation defaultOrient = Orientation2D.NORD;
	
	private PositionOrientation2D posOrient;
	
	@Test
	void createDefaultPostion() {
		 posOrient = new PositionOrientation2D();
		assertEquals( defaultPos, posOrient.getPosition());
	}
	
	@Test
	void createDefaultOrientation() {
		posOrient = new PositionOrientation2D();
		assertEquals( defaultOrient, posOrient.getOrientation());
	}
	
	@Test
	void createDeepCopy() {
		posOrient = new PositionOrientation2D(defaultPos, defaultOrient);
		PositionOrientation copy = new PositionOrientation2D(posOrient);
		assertDeepCopy( posOrient, copy);
	}
	
	// important to keep this test somewhere
	private void assertDeepCopy(PositionOrientation original, 
								PositionOrientation copy) {
			// compare values with equals()
			assertEquals(original, copy);
			// should not be necessary anymore
			assertEquals( original.getPosition(), copy.getPosition());
			assertEquals( original.getOrientation(), copy.getOrientation());
			
			// compare reference (adress), really a new copy
			assertFalse( original == copy );
			assertFalse( original.getPosition() == copy.getPosition());
			// not the same witn Enum !! 
			// "adresses are static" always reference the same object
			// assertFalse( original.getOrientation() == copy.getOrientation());
	}
	
	// test all mouvements, should be in PositionAndActions => MoveSequence
//	@Test 
//	void avanceReturnCopyAndOriginalNotModified() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, defaultOrient);
//		PositionAndOrientation nextPosOrient = posOrient.nextMouvement('A');
//		nextPosOrient.nextMouvement('A');
//		assertEquals( new PositionAndOrientation(defaultPos, defaultOrient),
//				  posOrient);
//	}
//	
//	@Test
//	void tourneGaucheReturnCopyOriginalNotModified() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, defaultOrient);
//		PositionAndOrientation nextPosOrient = posOrient.nextMouvement('G');
//		nextPosOrient.nextMouvement('G');
//		assertEquals( new PositionAndOrientation(defaultPos, defaultOrient),
//				  posOrient);
//	}
//	
//	@Test
//	void tourneDroiteReturnCopyOriginalNotModified() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, defaultOrient);
//		PositionAndOrientation nextPosOrient = posOrient.nextMouvement('D');
//		nextPosOrient.nextMouvement('D');
//		assertEquals( new PositionAndOrientation(defaultPos, defaultOrient),
//				  posOrient);
//	}
//	
//	@Test
//	void TourneGaucheFromNord() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, defaultOrient);
//		PositionAndOrientation nextPosOrient = posOrient.nextMouvement('G');
//		
//		assertEquals( Orientation.OUEST, nextPosOrient.getOrientation());
//		nextPosOrient = nextPosOrient.nextMouvement('G');
//		assertEquals( Orientation.SUD, nextPosOrient.getOrientation());
//		// check position unchanged
//		assertEquals( defaultPos, nextPosOrient.getPosition());
//		nextPosOrient = nextPosOrient.nextMouvement('G');
//		assertEquals( Orientation.EST, nextPosOrient.getOrientation());
//		nextPosOrient = nextPosOrient.nextMouvement('G');
//		assertEquals( Orientation.NORD, nextPosOrient.getOrientation());
//	}
//	
//	@Test
//	void TourneDroiteFromNord() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, defaultOrient);
//		PositionAndOrientation nextPosOrient = posOrient.nextMouvement('D');
//		
//		assertEquals( Orientation.EST, nextPosOrient.getOrientation());
//		nextPosOrient = nextPosOrient.nextMouvement('D');
//		assertEquals( Orientation.SUD, nextPosOrient.getOrientation());
//		nextPosOrient = nextPosOrient.nextMouvement('D');
//		assertEquals( Orientation.OUEST, nextPosOrient.getOrientation());
//		// check position unchanged
//		assertEquals( defaultPos, nextPosOrient.getPosition());
//		nextPosOrient = nextPosOrient.nextMouvement('D');
//		assertEquals( Orientation.NORD, nextPosOrient.getOrientation());
//	}
//	
//	@Test
//	void AvanceN00xpectsN0_1() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, Orientation.NORD);
//		assertEquals( new Position(0,-1), posOrient.nextMouvement('A').getPosition());
//		assertEquals( Orientation.NORD, posOrient.nextMouvement('A').getOrientation());
//	}
//	
//	@Test
//	void AvanceE00ExpectsE10() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, Orientation.EST);
//		assertEquals( new Position(1,0), posOrient.nextMouvement('A').getPosition());
//		assertEquals( Orientation.EST, posOrient.nextMouvement('A').getOrientation());
//	}
//	
//	@Test
//	void AvanceS00ExpectsS01() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, Orientation.SUD);
//		assertEquals( new Position(0,1), posOrient.nextMouvement('A').getPosition());
//		assertEquals( Orientation.SUD, posOrient.nextMouvement('A').getOrientation());
//	}
//	
//	@Test
//	void AvanceO00ExpectsS_10() throws IllegalMouvementException {
//		posOrient = new PositionAndOrientation(defaultPos, Orientation.OUEST);
//		assertEquals( new Position(-1,0), posOrient.nextMouvement('A').getPosition());
//		assertEquals( Orientation.OUEST, posOrient.nextMouvement('A').getOrientation());
//	}
//	
//	// IllegalMouvement
//	@Test 
//	void wrongMouvementRaisesException() {
//		posOrient = new PositionAndOrientation();
//		Assertions.assertThrows(IllegalMouvementException.class, ()-> {
//			posOrient.nextMouvement('X');
//		});
//	}

}
