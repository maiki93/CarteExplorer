package net.ddns.kimai.explorer.metier;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.Simulator;

import net.ddns.kimai.explorer.metier.rendering.Renderer;
//import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.RandomGenerator;
import net.ddns.kimai.explorer.metier.builders.CarteBuilder;
import net.ddns.kimai.explorer.metier.builders.ComponentCarteFactoryProvider;
import net.ddns.kimai.explorer.metier.builders.SimulationBuilder;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeuBuilder;
import net.ddns.kimai.explorer.metier.parseinput.RandomizeItem;

// difficult to test because no getter, almost no return value (except Renderer)
// with clear public interface, it is mainly mocked/ spied
public class ControllerMetierImpl implements ControllerMetier {

	// important access for setup.
	// This is 2 Responsabilities...
	// 1. Read input whith the help of ConfigurationProvider (only once). DataInput internal storage.
	// 2. Build at least one ConfigurationJeu (with random), by repetive calls to build()
	//          build may be used by MdJ to rebuild Simualtion( configBuilder build(); carteBuilder(), SimualtionBuilder() )
	//       clearly two interface (setup/init, build ) 
	// may win a lot to inject (need interface : ..? function: read(), build() setup
	//     only class to use serviceConfig
	private final ConfigurationJeuBuilder cfgJeuB;
		
	// optional in case no file input not anymore
	// used only from ConfigurationJeuBuilder now (to move inside ?)
	//private InputConfigurationProvider serviceConfig;
	
	// app console would need to create a ConfigurationJeu
	//  app.initializeJeuFromInteractiveMode( ConfigJeu )
	
	// 1 mdj and listOfSimulation
	// private MoteurDeJeu mdj;
	
	// getFrame, should be implememented in Carte Interface
	// 1. In simulation Interface ? not its role
	private Simulator simulation;
	// 2. With only 1 carte, simpler is here
	// 3. MdJ certainly the best place
	private Carte carte; // access
	
	// contains ref. to Carte, not the opposite !
	private Renderer renderer;
	
	public ControllerMetierImpl(InputParameter serviceConfig) {
		// serviceConfig used only by ConfigurationJeuBuilder, no point to keep in controller ?
		cfgJeuB = new ConfigurationJeuBuilder( serviceConfig,
				   							   new RandomizeItem ( new RandomGenerator() ));
	}
	
	@Override
	public void readConfigurationInput() {
		// must call read explicitly now
		// still not a good separation from first reading input and build (fixed or random) items
		cfgJeuB.readInput();
	}
		
	// should find better name
	// called by MdJ, something to return or keep reference here ?
	@Override
	public void buildGenerateSetup() {
		// intermediate usage, from ConfigurationJeuBuilder to Carte/Simulation Builder.
		ConfigurationJeu config = cfgJeuB.build();
		// Builder indeed, component factory injection makes more flexible, more testable  
		CarteBuilder carteB = new CarteBuilder(config,
											   ComponentCarteFactoryProvider.createFactory( config.getDimensionCarte() ));
		carte = carteB.buildFromConfiguration();
		// not clear the dependence renderer <=> carte, may just point to a carte ?
		// or any Renderable / CompositeRenderable impl. 
		renderer = carteB.buildRenderer();
		simulation = new SimulationBuilder(carte, config).build();	
		// Validation of data, an other functionality, too much here
	}

	@Override
	public void runJeu() {
		simulation.runSimulation();
	}

	@Override
	public String getCurrentFrame() {
		// AppConsole could also make the transformation : String for Console
		return renderer.renderFrame().toString();
	}

	@Override
	public void getStatistiques() {
		// TODO Auto-generated method stub
	}
}