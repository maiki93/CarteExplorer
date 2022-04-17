package net.ddns.kimai.explorer.metier.builders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.InputParameter;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeuBuilder;
import net.ddns.kimai.explorer.metier.parseinput.RandomizeItem;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;

// max of injection , use of mock/spy
// test only fixed input , not random
@ExtendWith(MockitoExtension.class)
class ConfigurationJeuBuilderTest {

    @Mock
    InputParameter serviceConfig;
    // @Spy, not point to much internal now
    // FactoryParser factoryParser;
    @Mock
    RandomizeItem randomizer;
    // return value
    ConfigurationJeu configJeu;
    // under test
    ConfigurationJeuBuilder configB;
    
    @BeforeEach
    void init() {
    	configB = new ConfigurationJeuBuilder(serviceConfig, randomizer);
    	// config.setDimensionCarte( Dimension.of(4,5) );
    }
    
    @Test
    void initialize() {
        assertThat( configB ).isNotNull();
        assertThat( serviceConfig ).isNotNull();
    }
    
    @Test
    void readDimensionCarte() {
    	when( serviceConfig.nextEntry() ).thenReturn( "C - 4 - 5" )
    									 .thenReturn( null );
    	configB.readInput();
    	configJeu = configB.build();
    	//then
    	assertThat( configJeu.getDimensionCarte() ).isEqualTo( Dimension.of(4,5));
    }
    
    @Nested
    @DisplayName("Not Random with a dimension carte set to 4 - 5")
    class NotRandomWithDimension45 {
    	
    	@BeforeEach
    	void init() {
    		when( serviceConfig.nextEntry() ).thenReturn( "C - 4 - 5" )
			 								 .thenReturn( null );
    		configB.readInput();
    	}
    
	    @Test
	    void readOneMontagneAtFixedPosition() {
	    	// given entry line, NULL VERY IMPORTANT else infinite loop, To improve.
	    	when( serviceConfig.nextEntry() ).thenReturn( "M - 2 - 3")
	    									 .thenReturn( null );
	    	// when
	    	configB.readInput(); // fill internal storage
	    	configJeu = configB.build(); // create item and fill config
	    	// check data config
	    	assertThat( configJeu.getFixedItems() ).hasSize(1);
	    			// .contains( Pair.of(any(FixedItem.class), Position2D.create(2,3)) );
	    	assertThat( configJeu.getFixedItems().get(0).item()).isEqualTo( Morphologie.MONTAGNE);
	    	assertThat( configJeu.getFixedItems().get(0).value()).isEqualTo( Position2D.create(2,3));
	    }
	    
	    @Test
	    void readTwoMontagneAtFixedPosition() {
	    	// given entry line, NULL VERY IMPORTANT else infinite loop, To improve.
	    	when( serviceConfig.nextEntry() ).thenReturn( "M - 2 - 3")
	    									 .thenReturn( "M - 1 - 1")
	    									 .thenReturn( null );
	    	configB.readInput();
	    	configJeu = configB.build();
	    	// check data config
	    	assertThat( configJeu.getFixedItems() ).hasSize(2);
	    	assertThat( configJeu.getFixedItems().get(0).item())
	    						 .isExactlyInstanceOf( Morphologie.MONTAGNE.getClass());
	    }
	    
	    @Test
	    void read2plus3CollectalbeAtFreePosition() {
	    	// given entry line, NULL VERY IMPORTANT else infinite loop, To improve.
	    	when( serviceConfig.nextEntry() ).thenReturn( "T - 1 - 1 - 2")
	    									 .thenReturn( "T - 2 - 2 - 3")
	    									 .thenReturn( null );
	    	// when
	    	configB.readInput();
	    	configJeu = configB.build();
	    	// then	    	
	    	assertThat( configJeu.getCollectableItems() ).hasSize(5);
	    	assertThat( configJeu.getCollectableItems().get(0).item() ).isInstanceOf( Tresor.class );
	    }
	    
	    @Test
	    void read2AventurierAtFreePosition() {
	    	// given entry line, NULL VERY IMPORTANT else infinite loop, To improve.
	    	when( serviceConfig.nextEntry() ).thenReturn( "A - Lara - 1 - 2 - E - AGGD")
	    									 .thenReturn( "A - Indiana - 2 - 3 - S - GAD")
	    									 .thenReturn( null );
	    	// when
	    	configB.readInput();
	    	configJeu = configB.build();
	    	// then	    	
	    	assertThat( configJeu.getMovingActors() ).hasSize(2);
	    	assertThat( configJeu.getMovingActors().get(0).item().getNom() ).isEqualTo("Lara");
	    	assertThat( configJeu.getMovingActors().get(0).value().getPositionOrientation() )
	    					.isEqualTo(PositionOrientation2D.create(1, 2, 'E'));
	    	// equals not implemented
	    	//assertThat( configJeu.getMovingActors().get(0).value().getActionSequence() )
			//				.isEqualTo( ActionSequenceFactory.from("AGGD"));
	    	
	    	// example usefull to include with Pair 
			// assertThat(config.getDimensionCarte()).isEqualTo( Dimension.of(4,5) );
			// assertThat(config.getFixedItems())
			//			.hasSize(2)
			//			.contains( Pair.of( Morphologie.MONTAGNE, Position2D.create(2, 1)))
			//			.contains( Pair.of( Morphologie.MONTAGNE, Position2D.create(3, 2)));

	    }
	// end with Dimension 4 5
    }
	    
}