package net.ddns.kimai.explorer.appconsole;

import net.ddns.kimai.explorer.metier.ControllerMetier;
//import net.ddns.kimai.metier.moteurdejeu.MoteurDeJeu;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;

// primary Adapter will use the ControllerMetier API to command the business
// Role is to drive the  model by input (user, test, script...)
// 
// It is split from Executable because this layer has only access to the model
//      and so need to inject ConfigurationProvider implementation
//
// For the moment, no logic in it, just forward calls
// Compared to a MVC pattern, controller is often responsible for calling the view
//     view HERE would be a String to print (on the console)
//                        a String to print in output file also
//
// If we want an interactive mode for ConfigurationJeu, responsible of more logic here ? 
public class AppConsoleOneRun {
	
	// adaptee interface
	private final ControllerMetier jeu;
	
	// in this layer, access to ConfigurationJeu (model layer)
	// 1. ask to ControllerMetier if FileProvider is fine.... change interface.. complex
	// 2. limit interactive mode only here  :  
		// fill internal ConfigJeu, ConfigJeu
		// jeu.initializeFromInteractiveMode( config)
	
	// in exemple: to pass Console as input
	public AppConsoleOneRun(ControllerMetier jeu) {
		this.jeu = jeu;
	}
		
	public void initializeJeuFromConfiguration() {
		// could return something for more interaction ?
		jeu.readConfigurationInput();	
		// let here, for passing previous test, may need a specific  interface
		// generate objects to prepare a simulation
		jeu.buildGenerateSetup();
	}
	
	// bad to move here the presentation getFrame() ?
	public int runJeu() {
		
		jeu.runJeu();
		// mdj.printErrorsInSetup()
		// mdj.printFrame()
		//System.out.println("Données chargées, la simulation peut commencer");
		//mdj.runJeu();
		// to console, it is specific to this adapter
		// mdj.printStats()
		//mdj.printLastFrameToConsole();
		
		
		// exception on initialization
    	//} catch( ParserException | CarteException | AventurierException e) {
    	//	System.out.println("Error in setup :" + e.getMessage());
    	//	return;
    	
    	// exception on executin of the simulation
    	//} catch( MoteurDeJeuException e) {
    	//	System.out.println("Error during the game :" + e.getMessage());
    		
    	// unchecked exception, print/log message and stackTrace	
    	//} catch( Exception e ) {
    	//	System.out.println("Unexpected exception...: " + e.getMessage());
    	//	e.printStackTrace();
    	//}
		return 0;
	}
	
	public String getCurrentFrame() {
		return jeu.getCurrentFrame();
	}
	
	// extends methods for a paticular use, here Console
	public void printLastFrameToConsole() {
		//getLastFrame();
	}
}
