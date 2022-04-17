package net.ddns.kimai.explorer.executable;

import net.ddns.kimai.explorer.userinterface.ConsoleUI;

import net.ddns.kimai.explorer.metier.ControllerMetier;
import net.ddns.kimai.explorer.metier.ControllerMetierImpl;
import net.ddns.kimai.explorer.metier.InputParameter;

import net.ddns.kimai.explorer.inputparameterprovider.InputParameterFile;
import net.ddns.kimai.explorer.inputparameterprovider.InputParameterConsole;
import net.ddns.kimai.explorer.inputparameterprovider.InputFileReader;

// A controller expects checked InitializationException (check filename, check input)
// unchecked SimulationRuntimeException (error in processing, savingFrame)  
public class CarteAuxTresors
{
    public static void main( String[] args )
    {
    	ConsoleUI consoleApp = null;
    	InputParameter serviceConfig;
    	
        System.out.println( "Bienvenue à la carte aux trésors !" );
        
    	String inputfileName = readArguments(args);
    	
    	// interactive mode
    	if ( inputfileName == null || inputfileName.isEmpty()) {
    		System.out.println("no filename enter interactive mode");
    		serviceConfig =
    			new InputParameterConsole(System.in, 
    														System.out);	
    	// input in file
    	} else {
    		System.out.println("fichier entrée: "+ inputfileName);
    		String outfileName = createOutputFilename(inputfileName);
    		System.out.println("fichier de sortie: "+ outfileName);
    		// Create Dependencies 
    		InputFileReader	fr = new InputFileReader(inputfileName);
    		serviceConfig = new InputParameterFile(fr);
    	}
    	
    	// common => could be in AppConsole
    	// Inject into the Controller, fully configured for the run
    	
    	// possibility to inject ConfigurationBuilder into Controller
    	// strange to use from here, but nice for testing. could be done in AppConsole by setter ?
     	// Few reason to change implementation ? don't know, deal with fixed/random up to now
    	ControllerMetier controller = new ControllerMetierImpl( serviceConfig );
    	// charge le primary adapter, not bad the idea to pass Console ?
    	consoleApp = new ConsoleUI(controller);
    	consoleApp.initializeJeuFromConfiguration();
    	
    	// Validation explicit ?
    	
    	// following should be consoleApp.run()
    	// String mapRendered;
    	try {
    		// consoleApp.initializeJeuFromConfiguration();
    		System.out.println("== Initial Frame");
    		System.out.println(consoleApp.getCurrentFrame());
    		// adapter.printFrame()
    		System.out.println("Données chargées, la simulation peut commencer");
    		consoleApp.runJeu();
    		
    		System.out.println("== Final Frame");
    		System.out.println(consoleApp.getCurrentFrame());
    		// to console, it is specific to this adapter
    		// adapter.printStats()
    		
    		//MoteurDeJeu mdj = new MoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
    		//mdj.setWriter(output);
    		
    		//mdj.renderConfiguration();
    		//mdj.runJeu();
    		// print all the buffer to File
    		//mdj.writeToFile();
    		//System.out.println( mdj.printStats() );
    		
    	// exception on initialization
    	//} catch( ParserException | CarteException | AventurierException e) {
    	//	System.out.println("Error in setup :" + e.getMessage());
    	//	return;
    	
    	// exception on executin of the simulation
    	//} catch( MoteurDeJeuException e) {
    	//	System.out.println("Error during the game :" + e.getMessage());
    		
    	// unchecked exception, print/log message and stackTrace	
    	} catch( Exception e ) {
    		System.out.println("Unexpected exception : " + e.getMessage());
    		e.printStackTrace();
    	}
		  
        System.out.println( "\nRelance-moi pour trouver plus de trésors !" );
    }

	private static String readArguments(String[] args) {        
        if ( args.length == 0 || 
        	args[0].equals("-h") || args[0].equals("-help") ) {
        	printHelp();
        	return null;
        }
        return args[0];
	}
    
    static String createOutputFilename(String in) {
    	String out = in.replace(".in", ".out");
    	System.out.println("sortie : " + out);
    	return out;
    }
    
    static void printHelp() {
    	System.out.println("usage: ./App [-h] [--help] fileInput");
    }
}
