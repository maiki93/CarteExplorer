package net.ddns.kimai.explorer.metier.simulation;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.carte.ChercheurTresor;
import net.ddns.kimai.explorer.metier.carte.collectionitems.Carte2DBuilderDSL;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

// It will be more an integration test
// Very few function to test : run() => call moveService.run()...

@DisplayName("Simulation Run, integration test")
class CarteExplorerTest {

	/** In  M  .       Lara Est:  ADAADA
	 *   . La  M       India Est: ADAAAGA 
	 *   .  .  .	   First move rejected (Montagne)	
	 *  T2 T2  .       Final In(0,3,E)  L(1,3,O) last move rejected
	 *                                           Overlap aventuriers
	 *                 1 Tresor collected each   
 	 */    
	Carte carte = Carte2DBuilderDSL.newCarte2D( 3, 4)
			 .withTerrain2DArray(Morphologie.class, Morphologie.PLAINE)
			 	.montagneAt( Position2D.create(1,0) )
			 	.montagneAt( Position2D.create(2,1) )
			 	.endTerrain()
			 .withCollectableItems(Tresor.class)
			 	.tresorAt( Position2D.create(0, 3) )
			 	.tresorAt( Position2D.create(0, 3) )
			 	.tresorAt( Position2D.create(1, 3) )
			 	.tresorAt( Position2D.create(1, 3) )
			 	.endCollectableItems()
			 .withMovingActors()
		     	.withAventurier("Lara", PositionOrientation2D.create(1, 1, 'E'),    "ADAADA" )
		     	.withAventurier("Indiana", PositionOrientation2D.create(0, 0, 'E'), "ADAAAGA" )
		     	.endMovingActors()
			 .build();
	
	MoveService moveService;
	
	CarteExplorer simulation;
	
	@BeforeEach
	void init() {
		List<MovingActor> list = carte.getActors();
		Map<MovingActor, ActionSequence> map = new LinkedHashMap<>();
		map.put( list.get(0), ActionSequenceFactory.input("ADAADA"));
		map.put( list.get(1), ActionSequenceFactory.input("ADAAAGA"));
		InputActionSeqOfActors inputAction = new InputActionSeqOfActors(map);
		
		moveService = new MoveAndRecolteService( carte, inputAction);
		simulation = new CarteExplorer( carte, moveService );	
	}
	
	@Test
	void initializationOftheSimulation() {
		assertNotNull( carte );
		assertNotNull( moveService );
		assertNotNull( simulation );
	}
	
	@Test
	void simualtionStopsNbStep7() {
		// given, nothing...
		long nb = simulation.runSimulation();
		
		assertEquals( 7, nb);
		//assertThat( carte.getActors())
	}
	
	@Test
	void finalPosition() {
		// given, nothing...
		long nb = simulation.runSimulation();
		// Lara
		assertThat( extractActorNb(0).getPositionOrientation() )
			.isEqualTo( PositionOrientation2D.create(1, 3, 'O'));
		// Indiana
		assertThat( extractActorNb(1).getPositionOrientation() )
			.isEqualTo( PositionOrientation2D.create(0, 3, 'E'));
	}
	
	@Test
	void collected1TresorEach() {
		long nb = simulation.runSimulation();
		//  Lara
		assertThat( ((ChercheurTresor) extractActorNb(0).getActor()).backpack().size() )
				.isEqualTo( 1 );
		// Indiana
		assertThat( ((ChercheurTresor) extractActorNb(1).getActor()).backpack().size() )
				.isEqualTo( 1 );
	}
	
	private MoveActorPosition extractActorNb( int nb ) {
		return carte.getMoveActorPosition().get( nb );
	}
}
