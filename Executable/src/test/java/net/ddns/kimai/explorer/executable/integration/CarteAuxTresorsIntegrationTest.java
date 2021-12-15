package net.ddns.kimai.explorer.executable.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.appconsole.AppConsoleOneRun;
import net.ddns.kimai.explorer.metier.InputConfigurationProvider;
import net.ddns.kimai.explorer.inputprovider.FileConfigurationProvider;
import net.ddns.kimai.explorer.inputprovider.InputFileReader;
import net.ddns.kimai.explorer.metier.ControllerMetier;
import net.ddns.kimai.explorer.metier.ControllerMetierImpl;

/**
 * Integration test, include all real objects and implementation
 * include all layers  explicitly.
 * 
 *  Make same construction as in the executable, 
 *  expects output: in console, in file
 *  
 *   Many exception to test here
 */
class CarteAuxTresorsIntegrationTest {

	private static final String INPUT_FILE = "test-demo.txt";
	
	private AppConsoleOneRun appConsole;
	private static final String initialFrame =  "        Me         M        In\n"
	         				                  + "         .        La         M\n"
	        				                  + "         .         .         .\n"
	        				                  + "      T(2)      T(3)         .\n";
	
	private static final String finalFrame =    "         .         M        In\n"
               								  + "         .         .         M\n"
             								  + "         .     La(1)         .\n"
             								  + " Me(1)T(1)      T(2)         .\n";
	
	// can simulate the console ? java.io.Console	
	@BeforeEach
	void init() {
		InputFileReader inputF = new InputFileReader( INPUT_FILE );
		InputConfigurationProvider confP = new FileConfigurationProvider(inputF);
		ControllerMetier controller = new ControllerMetierImpl(confP);
		appConsole = new AppConsoleOneRun(controller);
	}
	
	@Test
	void getFrameAfterInitialization() {
		appConsole.initializeJeuFromConfiguration();
		//appConsole.
		// First use we want to get a String
		String frameString = appConsole.getCurrentFrame();
		
		assertEquals( initialFrame, frameString);
	}
	
	@Test
	void getFrameAfterRun() {
		appConsole.initializeJeuFromConfiguration();
		appConsole.runJeu();
		// First use we want to get a String
		String frameString = appConsole.getCurrentFrame();	
		assertEquals( finalFrame, frameString);
	}

}
