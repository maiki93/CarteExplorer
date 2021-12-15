package net.ddns.kimai.explorer.executable.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.inputprovider.InteractiveConsoleConfigurationProvider;
import net.ddns.kimai.explorer.metier.ControllerMetierImpl;

// Purpose of this test, in executable package (access to all adapters && business)
// simulate setup, until creation of a valid ConfigurationJeu, may include error in parsing
@ExtendWith(MockitoExtension.class)
class setupSimulationFromConsole {
	
	@Mock
	private InteractiveConsoleConfigurationProvider serviceConfig;
	
	private ControllerMetierImpl controller;
	
	@BeforeEach
	void init() {
		controller = Mockito.spy( new ControllerMetierImpl(serviceConfig) );
	}
	
	@Test
	void initialize() {
		assertNotNull( serviceConfig );
		assertNotNull( controller );
	}
	
	@Test
	void provideCarteDimension() {
		when( serviceConfig.nextEntry() ).thenReturn("C - 4 - 5")
										 .thenReturn( null );
		
		controller.readConfigurationInput();
		// then
		verify( serviceConfig , times(2) ).nextEntry();
		// if ocntroller spied, what to check ?
		// it is the first part, read  input
		verify( controller ).readConfigurationInput();
		// it is the second part build Carte / Simulation
		// verify( controller ).finalizeBuild( any(ConfigurationJeu.class) );
	}
}
