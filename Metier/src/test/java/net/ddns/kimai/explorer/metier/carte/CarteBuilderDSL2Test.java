package net.ddns.kimai.explorer.metier.carte;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.carte.collectionitems.Carte2DBuilderDSL;

import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

/* This is a state unit testing, this need creation of getters 
 *   : NbColltectable; getItemTerrainAt... ONLY for TESTS, not needed by the system
 *   
 *   1. Implement those functions in interafce /implementation classes =
 *   2. Mocking static function creation ? for controlling calls to the correct specific constructor
 *   3. Mocking object returned by the builder, and test it is correct ? but don't test the builder in this case
 *        => certainly the best at this tme
 *   4. Assume to keep fields as package accessible, with casting some access field will be avaiable         
 *          
 *   5. Make one public constructor of Carte2DBuilder, then test
 *     .withTerrain()..., .withCollect()...  because all others are public
 *     And then can check forward calls. not possible also with Mockito (base)
 *     
 *     Do almost nothing at least check it compiles and run
 *       but keep for later (maybe more getter in interface)
 *    */

class CarteBuilderDSL2Test {
	// If use a more specific implementation, could check state 
	//    without polluting the Carte interface with unused function by business
	private Carte carte;
	
	@Test
	void createCarteWithdimensions() {
		carte = Carte2DBuilderDSL
				     .newCarte2D( 4, 5 )
					 .build();	
		assertEquals(4, carte.getDimensions()[0]);
		assertEquals(5, carte.getDimensions()[1]);
	}
	
	@Test
	void createCarteWithTerrain() {
		carte = Carte2DBuilderDSL
						.newCarte2D( 4, 5 )
						.withTerrain2DArray( Morphologie.class, Morphologie.PLAINE)
							.montagneAt( Position2D.create(2,2) )
							.montagneAt( Position2D.create(1,3) )
							.endTerrain()
						.build();
		//assertEquals(Morphologie.MONTAGNE, carte.getItemOnTerrainAt( Position2D.create(2, 2)));
		//assertEquals(Morphologie.MONTAGNE, carte.getItemOnTerrainAt( Position2D.create(1, 3)));
		//assertEquals(Morphologie.PLAINE, carte.getItemOnTerrainAt( Position2D.create(0, 0)));
		//assertEquals(Morphologie.PLAINE, carte.getItemOnTerrainAt( Position2D.create(3, 3)));
	}
	
	@Test
	void createCarteWithTresor() {
		carte = Carte2DBuilderDSL.newCarte2D( 4, 5 )
								 .withCollectableItems(Tresor.class)
								 	.tresorAt( Position2D.create(2, 1) )
								 	.tresorAt( Position2D.create(2, 2) )
								 	.tresorAt( Position2D.create(2, 2) )
								 	.endCollectableItems()
								 .build();
	
		//assertEquals(1, carte.getNbCollectableAt( Position2D.create(2, 1)));
		//assertEquals(2, carte.getNbCollectableAt( Position2D.create(2, 2)));
	}
	
	@Test
	void createCarteWithAventuriers() {
		carte = Carte2DBuilderDSL.newCarte2D( 4, 5)
							     .withMovingActors()
							     	.withAventurier("Lara", PositionOrientation2D.create(1, 1, 'N'), "AGADA" )
							     	.withAventurier("Indiana", PositionOrientation2D.create(1, 2, 'E'), "AGADA" )
							     	.endMovingActors()
							     .build();
		
		assertEquals( 2, carte.getActors().size() );
	}
	
	@Test
	void createCarteWithAll() {
		carte = Carte2DBuilderDSL.newCarte2D( 4, 5)
								 .withTerrain2DArray( Morphologie.class, Morphologie.PLAINE)
								 	.montagneAt( Position2D.create(2,2) )
								 	.montagneAt( Position2D.create(1,3) )
								 	.endTerrain()
								 .withCollectableItems(Tresor.class)
								 	.tresorAt( Position2D.create(2, 1) )
								 	.tresorAt( Position2D.create(2, 3) )
								 	.tresorAt( Position2D.create(2, 3) )
								 	.endCollectableItems()
								 .withMovingActors()
							     	.withAventurier("Lara", PositionOrientation2D.create(1, 1, 'N'), "AGADA" )
							     	.withAventurier("Indiana", PositionOrientation2D.create(1, 2, 'E'), "AGADA" )
							     	.endMovingActors()
								 .build();
		
		//assertEquals(Morphologie.MONTAGNE, carte.getItemOnTerrainAt( Position2D.create(2, 2)));
		//assertEquals(Morphologie.MONTAGNE, carte.getItemOnTerrainAt( Position2D.create(1, 3)));
		//assertEquals( 1, carte.getNbCollectableAt( Position2D.create(2, 1)));
		//assertEquals( 2, carte.getNbCollectableAt( Position2D.create(2, 3)));
		//assertEquals( 2, carte.getNbActors() );
	}	
}
/*
class CarteBuilderMockitoTest {

	private Carte2D carte2d;
	
	// @Spy
	private Carte2DBuilderDSL cb = Carte2DBuilderDSL.newCarte2D(4,5);
	
	// @Test
	void createCarteWithdimensions() {
		carte2d = (Carte2D) Carte2DBuilderDSL
				     .newCarte2D( 4, 5 )
					 .build();	
		assertEquals(4, carte2d.getDimensions()[0]);
		assertEquals(5, carte2d.getDimensions()[1]);
		
		assertEquals(Tresor.class, carte2d.terrain.getFixedItem(Position2D.create(1, 1)).getClass());
	}
	
	// @Test
	void setTerrain() {
		
		cb.withTerrain2DArray(Morphologie.PLAINE)
			.montagneAt( Position2D.create(1, 1) )
			.endTerrain();
		// nothing to check like this, cb is send as parameter to Terrain2DBuilder( cann to new Terrain)
		// move problem to constructor call, same as static (need other library)
		// verify( cb. ?? )		
	}
} */

