package net.ddns.kimai.explorer.executable.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.ControllerMetierImpl;

import net.ddns.kimai.explorer.inputparameterprovider.InputParameterFile;
import net.ddns.kimai.explorer.inputparameterprovider.IFileReader;

/**
 * Higher level testing 
 * - Not bad from here, but need access to a Frame to check the setup
 * It is not an integration test !!
 * 
 */
@ExtendWith(MockitoExtension.class)
class setupSimulationFromFile {

	@Mock
	private IFileReader fileReaderMock;

	@Spy //@Mock // before spy, nextEntry go to fileContent => null, readfile not called ?
	@InjectMocks
	private InputParameterFile serviceConfig;

	private ControllerMetierImpl controller;
	
	@BeforeEach
	void init() {
		// spy would allow more check on the business logic in controller ?
		controller = new ControllerMetierImpl(serviceConfig);
	}
	
	// was working before , now fileContent is null, 
	// because readFile is not called ??
	@Test
	void testSetupMocks() throws IOException {
		
		when( fileReaderMock.readFile() )
			.thenReturn( Arrays.asList( "C - 4 - 5",
										"T - 1 - 1",
										"T - 1 - 2 - 2",
										"M - 2 - 3",
										"M - 3 - 4",
										"A - Lara - 0 - 1 - E - ADGAD",
								   		"A - Indiana - 1 - 1 - O - DAGGA"));
		
		assertNotNull( serviceConfig );
		assertNotNull(controller);
		controller.readConfigurationInput();
		
		// do not manage to verify controller calls
		verify( serviceConfig, times(1) ).readFile();
		verify( serviceConfig, times(8) ).nextEntry();
		// verify ( controller ).initializeJeuFromConfiguration();
		//controller.runJeu();
		// need to check Carte
	}
	
	@Test
	void testSetupMocks2() throws IOException {
		
		when( fileReaderMock.readFile() )
			.thenReturn( Arrays.asList( "C - 4 - 5",
										"T - 1 - 1",
										"T - 1 - 2 - 2",
										"M - 2 - 3",
										"M - 3 - 4",
										"A - Lara - 0 - 1 - E - ADGAD",
								   		"A - Indiana - 1 - 1 - O - DAGGA"));
// used when spy is not used		
//		when( serviceConfig.nextEntry() ).thenReturn("C - 3 - 6")
//										 .thenReturn("T - 1 - 1")
//										 .thenReturn("A - Lara - 0 - 1 - E - ADGAD")
//										 .thenReturn(null);
			
		controller.readConfigurationInput();
		// do not manage to verify controller calls
		//verify( serviceConfig, times(1) ).readFile();
		verify( serviceConfig, times(8) ).nextEntry();
		// verify ( controller ).initializeJeuFromConfiguration();
		//controller.runJeu();
		// need to check Carte
	}
}
