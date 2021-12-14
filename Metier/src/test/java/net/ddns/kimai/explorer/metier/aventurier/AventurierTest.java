package net.ddns.kimai.explorer.metier.aventurier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;

class AventurierTest {

	private Aventurier lara;
	
	@BeforeEach
	void init() {
		lara = new Aventurier("Lara Croft");
	}
	
	@Test
	void createAventurierWithName() {
		assertEquals("Lara Croft", lara.getNom());
	}
	
//	@Test
//	void createAventurierWithInvalidNameRaisesException() {
//		Assertions.assertThrows(InvalidNameAventurierException.class, ()-> {
//			new Aventurier("Lara-Croft");
//		});
//	}
	
	// MovingActors
	@Test
	void canAccessToPlaine() {
		assertFalse( lara.isAnObstacle(Morphologie.PLAINE));
	}
	
	@Test
	void cannotAccessToMontagne() {
		assertTrue( lara.isAnObstacle(Morphologie.MONTAGNE));
	}
	
	// ChercheurTresor
	@Test
	void createAventurierHasZeroTresorAtCreation() {
		assertEquals( 0, lara.backpack().size() );
	}
	
	@Test
	void aventurierRecolteTresor() {
		lara.recolteTresor( new Tresor() );
		assertEquals( 1, lara.backpack().size());
	}
}
