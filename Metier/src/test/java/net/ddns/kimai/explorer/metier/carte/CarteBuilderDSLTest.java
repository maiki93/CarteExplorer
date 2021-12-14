package net.ddns.kimai.explorer.metier.carte;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.carte.collectionitems.Carte2DBuilderDSL;

import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

// How to test a builder ? 
// Done in checking state of the created carte, use package accesible field in Carte2D, not nice
class CarteBuilderDSLTest {

	private Carte2D carte2d;
		
	@Test
	void createCarteWithdimensions() {
		carte2d = (Carte2D) Carte2DBuilderDSL
				     .newCarte2D( 4, 5 )
					 .build();	
		assertEquals(4, carte2d.getDimensions()[0]);
		assertEquals(5, carte2d.getDimensions()[1]);
	}
	
	@Test
	void createCarteWithTerrain() {
		carte2d = (Carte2D) Carte2DBuilderDSL
						.newCarte2D( 4, 5 )
						.withTerrain2DArray(Morphologie.class, Morphologie.PLAINE)
							.montagneAt( Position2D.create(2,2) )
							.montagneAt( Position2D.create(1,3) )
							.endTerrain()
						.build();
		
		assertEquals(Morphologie.MONTAGNE, carte2d.terrain.getFixedItem( Position2D.create(2, 2)));
		assertEquals(Morphologie.MONTAGNE, carte2d.terrain.getFixedItem( Position2D.create(1, 3)));
		assertEquals(Morphologie.PLAINE, carte2d.terrain.getFixedItem( Position2D.create(0, 0)));
	}
	
	@Test
	void createCarteWithTresor() {
		carte2d = (Carte2D) Carte2DBuilderDSL.newCarte2D( 4, 5 )
								 .withCollectableItems(Tresor.class)
								 	.tresorAt( Position2D.create(2, 1) )
								 	.tresorAt( Position2D.create(2, 2) )
								 	.tresorAt( Position2D.create(2, 2) )
								 	.endCollectableItems()
								 .build();
	
		assertEquals( Tresor.class, carte2d.tresors.collect( Position2D.create(2, 1)).get().getClass() );
		assertEquals( Tresor.class, carte2d.tresors.collect( Position2D.create(2, 2)).get().getClass() );
		assertEquals( Optional.empty(), carte2d.tresors.collect( Position2D.create(2, 1)) );
		assertEquals( Optional.empty(), carte2d.tresors.collect( Position2D.create(0, 0)) );
	}
	
	@Test
	void createCarteWithAventuriers() {
		carte2d = (Carte2D) Carte2DBuilderDSL.newCarte2D( 4, 5)
							     .withMovingActors()
							     	.withAventurier("Lara", PositionOrientation2D.create(1, 1, 'N'), "AGADA" )
							     	.withAventurier("Indiana", PositionOrientation2D.create(1, 2, 'E'), "AGADA" )
							     	.endMovingActors()
							     .build();
		assertNotNull( carte2d.getActors() );
		assertEquals(2,  carte2d.getActors().size() );
		assertEquals( "Lara", carte2d.getActors().get(0).getNom() );
		assertEquals( "Indiana", carte2d.getActors().get(1).getNom());
	}
	
}
