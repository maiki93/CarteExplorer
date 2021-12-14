package net.ddns.kimai.explorer.metier;

// Controller, high level because first interface met by a client
// follow main stages of a run :
// - initialization with reading of input
// - (creation of final items ?) can be hidden to a client. Mainly necessary in intern
// - running the simulation
// - providing data produced by the run, intermediate and final : Frame(Renderer)
// -                                     stats : (BD)
// - providing interface to send action (Game CAT))
public interface ControllerMetier {
	
	/** Process reading of input (file / console..), called once */
	void readConfigurationInput();
	/** build new maps for a simualtion, may be called more than once */
	void buildGenerateSetup();
	/** could/should move the building of items at this stage, if random, new Maps generated */
	void runJeu();
	
	String getCurrentFrame(); // return Frame, String most basic
	void getStatistiques(); // Statistique
	//void saveResults() write Restart..
	// if want ot interact directly with the system
	// SimulationBuilder getSimulationBuilder(); // get MdJ ? 
	
}
