package net.ddns.kimai.explorer.metier.builders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.Pair;

// Test with Mock
// input: ConfigurationJeu  is injected
// call : private FacotryProvider (factory of components )
//        private cannot be tested, package private/protected ok

// with FactoryProvider injected,
// not so much better, but could test some functions are never called
//         when implemented (test size of entries)

// => good idea, Dimension in Factory, CarteBuilder no care about dimensions

// public function to test
//     buildFromConfiguration()

@ExtendWith(MockitoExtension.class)
class CarteBuilderMockTest {

	// obliged to define the base interface FixedItem for creating List.of()
	private static final Pair<FixedItem, Position> montagne1 = 
			Pair.of(Morphologie.MONTAGNE, Position2D.create(1, 1));
	
	// It is the input
	// Because a mock cfg.actors is null and not the default empty List
	@Mock
	ConfigurationJeu cfg;
	
	// if Mock must stub, all call to create factory
	@Spy
	FactoryComponent factory = new FactoryComponent2D( Dimension.of(3,4) );
	
	// @InjectMocks
	// @Spy no default constructor
	CarteBuilder carteB;
	
	// explicit constructor, to set the correct dimension
	// could be in Factory !! 
	@BeforeEach
	void init() {
		// moved into factory
		// when( cfg.getDimensionCarte() ).thenReturn( Dimension.of(3,4) );
		carteB = new CarteBuilder(cfg, factory);
		// carteB = spy( new CarteBuilder(cfg) );
	}
	@Test
	void initalization() {
		assertNotNull( cfg );
		assertNotNull(carteB);
	}
	
	@Test
	void buildWith1Montagne() {
		when ( cfg.getFixedItems() ).thenReturn( List.of( montagne1 ) );
//		when ( factory.createTerrain(Morphologie.class,
//									 new int[] {3,4},
//									 Morphologie.PLAINE))
//						.thenReturn(new Terrain2DArray<Morphologie>(Morphologie.class, 3, 4, Morphologie.PLAINE ));

		carteB.buildFromConfiguration();
		verify( factory ).createTerrain(Morphologie.class, 
						   				//any(int[].class),
										//new int[] {3,4},
						   				Morphologie.PLAINE);
		// better if not called
		verify( factory  ).createActors( any(List.class) );
		verify( factory  ).createCollectables(  any(List.class) );
		// cannot test, not a Mock (DI), even spying carte
//		verify( carteB.factoryComponent )
//			.createTerrain(Morphologie.class,
//						   any(int[].class),
//						   Morphologie.PLAINE); //any(), any(), any());
		// need public interafce
		// verify( carteB ).setupTerrain();
	}

}
