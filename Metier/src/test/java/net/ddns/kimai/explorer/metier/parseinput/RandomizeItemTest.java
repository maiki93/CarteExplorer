package net.ddns.kimai.explorer.metier.parseinput;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.RandomGenerator;

// allege the writing of enumeration
import static net.ddns.kimai.explorer.metier.parseinput.RandomizeItem.Strategy.*;

// Test this utility class, need to deal with random number
// this tests does not check if inside the Dimension ! not the point in fact
@ExtendWith(MockitoExtension.class)
class RandomizeItemTest {

	@Mock
	private RandomGenerator rdm;
	@InjectMocks
	private RandomizeItem rdmItem;
	// Value returned by generateRandomPosition 
	private List<Position> listPosition;
	
	@BeforeEach
	void init() {
		rdmItem.setDimension( Dimension.of(3, 4));
	}
	
	// not sure it is a godd idea to test an assertion
	// will depend on compiler option (debug, prod...)
//	@Test
//	void assertErrorWhenNoDimension() {
//		assertThrows(AssertionError.class, () -> {
//			rdmItem.generateRandomPosition(0, MAX_ONE_ITEM);
//		});
//	}
	
	@Test
	void ZeroItemRequired() {
		listPosition = rdmItem.generateRandomPosition( 0, MAX_ONE_ITEM);
		assertThat( listPosition ).isNotNull().hasSize(0);	
	}
	
	@Test
	void TwoItemsMaxOneEmptyForbidden() {
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 3 ,4 );
		
		listPosition = rdmItem.generateRandomPosition( 2, MAX_ONE_ITEM);
		
		assertThat( listPosition ).isNotNull()
								  .hasSize(2)
								  .contains( Position2D.create(1, 2), 
										     Position2D.create(3, 4));
		verify( rdm, times(4) ).nextInt( anyInt() );
	}
	
	@Test
	void TwoItemsMaxOneSameRandomEmptyForbidden() {
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 1, 2, 3, 4 );
		
		listPosition = rdmItem.generateRandomPosition( 2, MAX_ONE_ITEM);
		
		assertThat( listPosition ).isNotNull()
								  .hasSize(2)
								  .contains( Position2D.create(1, 2), 
										     Position2D.create(3, 4));
		verify( rdm, times(6) ).nextInt( anyInt() );
	}
	
	@Test
	void TreeItemsMultipleSameRandomEmptyForbidden() {
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 1, 2, 3, 4 );
		
		listPosition = rdmItem.generateRandomPosition( 3, MULTIPLE_ITEM);
		
		assertThat( listPosition ).isNotNull()
								  .hasSize(3)
								  .contains( Position2D.create(1, 2),
										  	 Position2D.create(1, 2),
										     Position2D.create(3, 4));
		verify( rdm, times(6) ).nextInt( anyInt() );
	}
	
	@Test
	void TwoItemMaxOneAndForbiddenPosition() {
		List<Position> forbidden = List.of( Position2D.create(1, 1),
											Position2D.create(2, 2)  );
		
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 
													1, 1, // forbidden 
													2, 2, // forbidden
													1, 2, // will be rejected
													3, 4 );
		
		listPosition = rdmItem.generateRandomPosition( 2, MAX_ONE_ITEM, forbidden );
		assertThat( listPosition ).isNotNull()
		  						  .hasSize(2)
		  						  .contains( Position2D.create(1, 2), 
		  								  	 Position2D.create(3, 4));
		verify( rdm, times(10) ).nextInt( anyInt() );
	
	}
	
	@Test
	void TreeItemMultiItemAndForbiddenPosition() {
		rdmItem.setDimension( Dimension.of(3, 4));
		List<Position> forbidden = List.of( Position2D.create(1, 1),
											Position2D.create(2, 2)  );
		
		when( rdm.nextInt( anyInt() ) ).thenReturn( 1, 2, 
													1, 1, // forbidden 
													2, 2, // forbidden
													1, 2, // accepted again
													3, 4 );
		
		listPosition = rdmItem.generateRandomPosition( 3, MULTIPLE_ITEM, forbidden );
		assertThat( listPosition ).isNotNull()
		  						  .hasSize(3)
		  						  .contains( Position2D.create(1, 2),
		  								     Position2D.create(1, 2),
		  								  	 Position2D.create(3, 4));
		verify( rdm, times(10) ).nextInt( anyInt() );
	}
}
