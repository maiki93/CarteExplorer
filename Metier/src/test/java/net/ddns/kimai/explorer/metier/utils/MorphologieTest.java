package net.ddns.kimai.explorer.metier.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.carte.item.Morphologie;

/** Create this test for getting correct syntax / behavior of
 * enumeration type mainly.
 */
class MorphologieTest {
	
	@Test
	void createDefaultArrayMorphologie() {
		Morphologie[] tabMorpho = new Morphologie[5];
		Arrays.fill(tabMorpho, Morphologie.PLAINE);
		assertEquals(tabMorpho[0], Morphologie.PLAINE);
	}
	
	@Test
	void createDefaultArrayMorphologie2D() {
		Morphologie[][] tabMorpho = new Morphologie[5][5];
		Arrays.stream(tabMorpho).forEach(a -> Arrays.fill(a, Morphologie.PLAINE));
		assertEquals(tabMorpho[0][0], Morphologie.PLAINE);
		assertEquals(tabMorpho[4][4], Morphologie.PLAINE);
	}
	
	@Test
	void valueOfReturnObject() {
		Object copy = Morphologie.valueOf("MONTAGNE");
		
	}

}
