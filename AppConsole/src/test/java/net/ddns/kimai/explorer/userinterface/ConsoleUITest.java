package net.ddns.kimai.explorer.userinterface;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.metier.ControllerMetier;

//import net.ddns.kimai.explorer.userinterface.ConsoleUI;

/**
 * Few logic, only forward.
 * Certainly some logic to move in this adapter
 */
@ExtendWith(MockitoExtension.class)
public class ConsoleUITest 
{
	// Controller already configured (inputfile name)
	@Mock
	ControllerMetier controller;	
	// class under test
	@InjectMocks
	private ConsoleUI adapter;
		
	@Test
	void forwardCallforInitialization() {
		adapter.initializeJeuFromConfiguration();
		verify( controller ).readConfigurationInput();	
	}
	
	@Test
	void forwardCallforRunningSimulation() {
		adapter.runJeu();
		verify( controller ).runJeu();
	}
	
	@Test
	void forwardCalltoGetFrame() {
		adapter.getCurrentFrame();
		verify( controller ).getCurrentFrame();
	}
}
