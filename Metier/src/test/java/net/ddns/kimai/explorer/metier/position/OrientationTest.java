package net.ddns.kimai.explorer.metier.position;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrientationTest {

	@Test
	void createOrientationNord() {
		Orientation direction = Orientation2D.EST;
		assertEquals( Orientation2D.EST, direction);
	}
	
	@Test
	void getOrdinalValue() {
		Orientation2D est = Orientation2D.EST;
		assertEquals(1, est.ordinal());
		Orientation2D sud = Orientation2D.SUD;
		assertEquals(2, sud.ordinal());
	}
	
	@Test
	void fromNordTurnRight() {
		Orientation direction = Orientation2D.NORD;
		Orientation right = direction.tourneDroite();
		assertEquals(Orientation2D.EST, right);
	}
	
	@Test
	void fromSudTurnRight() {
		Orientation direction = Orientation2D.SUD;
		assertEquals(Orientation2D.OUEST, direction.tourneDroite());
	}
	
	@Test
	void fromOuesTurnRight() {
		Orientation direction = Orientation2D.OUEST;
		assertEquals(Orientation2D.NORD, direction.tourneDroite());
	}
	
	@Test
	void fromSudTurnLeft() {
		Orientation direction = Orientation2D.SUD;
		assertEquals(Orientation2D.EST, direction.tourneGauche());
	}
	
	@Test
	void fromNordTurnLeft() {
		Orientation direction = Orientation2D.NORD;
		assertEquals(Orientation2D.OUEST, direction.tourneGauche());
	}
	

}
