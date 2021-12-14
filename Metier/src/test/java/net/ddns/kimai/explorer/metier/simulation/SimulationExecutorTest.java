package net.ddns.kimai.explorer.metier.simulation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Very basic,just a start before developping new behavior
@ExtendWith(MockitoExtension.class)
class SimulationExecutorTest {

	@Mock Simulator simulation;
	
	@InjectMocks SimulationExecutor mdj;
	
	@BeforeEach
	void init() {
		// given
		when( simulation.runSimulation() ).thenReturn( 10L );
	}
	
	@Test void forwardRunSimulationAndGetTheNulberOfSteps() {
		// when
		long nb = mdj.runSimulation();
		// then
		verify( simulation ).runSimulation();
		assertEquals(10, nb);
	}
	
	@Test void getStatsReturnNbOfSteps() {
		// when
		mdj.runSimulation();
		long stat = mdj.getStats();
		// then
		assertEquals( 10, stat);
	}
	
	@Test void printStatsFormat() {
		mdj.runSimulation();
		String reportStat = mdj.printStats();
		// then containsSequence("Il", "10", "tours");
		assertThat(reportStat).isNotEmpty()
							  .startsWith("Il")
							  .contains("10")
							  .contains("tours");
	}

}
