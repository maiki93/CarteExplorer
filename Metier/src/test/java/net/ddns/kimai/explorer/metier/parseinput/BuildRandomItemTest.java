package net.ddns.kimai.explorer.metier.parseinput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.InputConfigurationProvider;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.utils.Pair;
import net.ddns.kimai.explorer.metier.utils.RandomGenerator;

// to update with ConfigurationBuilder, here will test specific rules / function
// specific to test randomizeItem and logic of building items

// call ConfigurationJeuBuilder.build()
//     1. init from String, it will generate DataInput's
//     2. split DataInput's in own class ( package private ?), with getters
//     3. other ?

// =>  will call buildListitem() => randomizer() class
// generate usable ConfigurationJeu

// Very similar to ConfigurationJeuBuilderTest ( test fixed )
// Not possible to test exactly build(), cannot mock dataCarte/Fixed.. and private.
//                                       even not getter
// => again split 2 behaviors class / 1 data collection class

// Keep version with text input, here. to replace with version VSplit when code split in 2 classes
// nested tests, to include multple paths, alternative/missing entries
@ExtendWith(MockitoExtension.class)
public class BuildRandomItemTest {
	//complexity of mocking, different possibilities 
	// => too much responsabilities mock random, String input, ? DataInput impossible...
	
	// mock for input
	@Mock
	InputConfigurationProvider serviceConfig;
	
	// mock randomGenerator
	@Mock
	private RandomGenerator rdm;
	
	// want to use full implementation Spy or normal object ?
	// implementation already tested in other test, so no need of random ?
	// 1. if mock, just need to stub List<Position>
	// 2. if normal object, stub random generator
	@InjectMocks
	RandomizeItem randomizer;
	
	// class under test, may Spy it ? call to private only
	ConfigurationJeuBuilder builder; // = new ConfigurationJeuBuilder( serviceConfig, randomizer );
	// results if builder.builder() to assert
	
	// @Spy // Spy to allow to stub setDimensionCarte, ok default constructor
	ConfigurationJeu config;
	
	@BeforeEach
	void init() {
		//randomizer = spy()
		// need spy because of Dimension, better if constructor with dimension
		//builder = Mockito.spy( new ConfigurationJeuBuilder( serviceConfig, randomizer ));
		
		builder = new ConfigurationJeuBuilder( serviceConfig, 
				                               randomizer );
	}

	@Test
	void randomObstacle() {
		// given, carte entry necessary
		// to setup  randomizer.dimension correctly
		when( serviceConfig.nextEntry() ).thenReturn( "C - 4 - 5")
										 .thenReturn( "M - R - 2")
		 								 .thenReturn( null );
		// possibility to mock randomGenerator
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 3, 4 );
		builder.readInput(); // stage 1 read input
		// when
		config = builder.build(); // stage 2 builder
		// then
		assertThat( config.getFixedItems() )
						  .hasSize(2)
						  .contains( Pair.of( Morphologie.MONTAGNE, Position2D.create(1, 2)),
							         Pair.of( Morphologie.MONTAGNE, Position2D.create(3, 4)) );
		assertTrue( checkNoOverlap( config.getFixedItems()) );
		// buildListItemù is private
	}
	
	@Test
	void randomObstacleAndCollectableItems() {
		// given, carte entry necessary
		// to setup  randomizer.dimension correctly
		when( serviceConfig.nextEntry() ).thenReturn( "C - 4 - 5")
										 .thenReturn( "M - R - 2")
										 .thenReturn( "T - R - 3")
		 								 .thenReturn( null );
		// possibility to mock randomGenerator
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 3, 4, // Montagnes
													1, 1, // free
													1, 2, // forbidden
													2, 2, //  free
													3, 3); // free
		builder.readInput(); // stage 1 read input
		// when
		config = builder.build(); // stage 2 builder
		// then
		assertThat( config.getFixedItems() )
						  .hasSize(2)
						  .contains( Pair.of( Morphologie.MONTAGNE, Position2D.create(1, 2)),
							         Pair.of( Morphologie.MONTAGNE, Position2D.create(3, 4)) );
		assertTrue( checkNoOverlap( config.getFixedItems()) );
		
		// then
		verify ( rdm, times(12) ).nextInt( anyInt());
		assertThat( config.getCollectableItems() )
						  .hasSize(3);
//						  .contains( Pair.of( any(Tresor.class), Position2D.create(1, 1)),
//								     Pair.of( any(Tresor.class), Position2D.create(2, 2)),
//								     Pair.of( any(CollectableItem.class), Position2D.create(3, 3)) );
				
		assertThat( config.getCollectableItems() )
						  .extracting( Pair::value )
						  .contains( Position2D.create(1, 1), 
								     Position2D.create(2, 2),
								     Position2D.create(3, 3) )
						  .doesNotContain( Position2D.create( 1, 2), // rejected
								           Position2D.create( 0, 0) )
						  .hasSize(3);
	}
	
	@Test
	void randomAventurierAndObstacleAndCollectableItems() {
		// given, carte entry necessary
		// to setup  randomizer.dimension correctly
		when( serviceConfig.nextEntry() ).thenReturn( "C - 4 - 5")
										 .thenReturn( "M - R - 2")
										 .thenReturn( "T - R - 3")
										 .thenReturn( "A - R - 3")
		 								 .thenReturn( null );
		// possibility to mock randomGenerator
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 3, 4, // Montagnes
													1, 1, 2, 2, 3, 3, // collectable
													2, 1, // free
													3, 4, // montagne, rejected
													2, 2, // collectable ok, free
													2, 1, // rejected already an aventurier
													4, 3 ); // free); // free
		builder.readInput(); // stage 1 read input
		// when
		config = builder.build(); // stage 2 builder
		// then
		verify ( rdm, times(20) ).nextInt( anyInt()); // + 0 call due to Orientation (Action) 
		assertThat( config.getMovingActors() ).hasSize(3);
	}
	
	

/*
	@Test
	void randomAventurier() {
		//given
		String line = "A - R - 6";
		// when
		ConfigurationJeuParserHelper.parse(config, line);
		// then
		assertThat( config.getMovingActors() ).hasSize(6);
		// Aventurier cannot overlap
		// not working V == ActorInConfig, not a Position
		assertTrue( checkNoOverlapAventurier( config.getMovingActors()) );
	}
	
	@Test
	void randomAllEntries() {
		String line1 = "M - R - 5";
		String line2 = "T - R - 6";	
		String line3 = "A - R - 2";
		
		ConfigurationJeuParserHelper.parse(config, line1);
		ConfigurationJeuParserHelper.parse(config, line2);
		ConfigurationJeuParserHelper.parse(config, line3);
		
		// then
		assertThat( config.getFixedItems() ).hasSize(5);
		assertThat( config.getCollectableItems() ).hasSize(6);
		assertThat( config.getMovingActors() ).hasSize(2);
		
		assertTrue( checkNoOverlap( config.getFixedItems()) );
		assertTrue( checkNoOverlapAventurier( config.getMovingActors()) );
		
	// corrected, but strange behavior in placeIsFree with ==
		assertTrue( checkNoOverlapMontagneAndTresor( config.getFixedItems(), config.getCollectableItems() ) );	   
	}
*/
	
	
	// works only for Fixed and collectableItems
	private <I,V> boolean checkNoOverlap(List<Pair<I,V>> list) {
		
		Map<V, List<Pair<I,V>>> map = 
					list.stream()
						.collect(Collectors.groupingBy( pair -> pair.value() ));
		
		for(Map.Entry<V, List<Pair<I,V>>> entry : map.entrySet()) {
			if( entry.getValue().size() > 1 )
				return false;
		}
		return true;
	}

/*
	private <I,V> boolean checkNoOverlapAventurier(List<Pair<I,V>> list) {
		
		Map<Position, List<Pair<I,V>>> map = 
					list.stream()
						.collect(Collectors.groupingBy( 
								pair -> ((ActorPropsInConfiguration) pair.value()).getPositionOrientation().getPosition() ));
		
		for(Map.Entry<Position, List<Pair<I,V>>> entry : map.entrySet()) {
			if( entry.getValue().size() > 1 )
				return false;
		}
		return true;
	}
	
	private <I,R> boolean checkNoOverlapMontagneAndTresor(List<Pair<I,Position>> listMontagne, List<Pair<R,Position>> listTresor) {
		List<Position> positionMontagne = 
				listMontagne.stream()
							.map( Pair::value )
							.collect(Collectors.toList());
		
		boolean anyMatch = 
			listTresor.stream() 
					  .anyMatch( pair ->  positionMontagne.contains ( pair.value() ) );
		return ! anyMatch;
	}
*/
}
