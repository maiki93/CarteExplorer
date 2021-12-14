package net.ddns.kimai.explorer.metier.simulation;

import net.ddns.kimai.explorer.metier.simulation.Simulator;

// Responsibilites : Simulation executor (exec. on (pool) threads e.g.)
//                   Managing simulation(s), Statistiques (combine more than one)
// => Will need simulation parameters (nbStep, nRun, nbMovingPlayers..)
//  				 Intermediate wih user-input (inputHandler)

// Far too much, let's just integrate smoothly, one simulation
//     across all layers of the app for a end to end test
public class SimulationExecutor {
	
	private final Simulator simulation;
	// first stat
	private long nbStep;
	
	public SimulationExecutor(Simulator simulation) {
		this.simulation = simulation;
		this.nbStep = 0;
	}
	
	public long runSimulation() /* throws MoteurDeJeuException */  {
		long nbTour = simulation.runSimulation();
		// always one round more (without any mouvement)
		//this.compteurTourJeu = nbTour - 1;
		//try {
			//writer.addContent(renderConfiguration());
		//	renderConfiguration();
		//} catch (InvalidPositionCarteException e) {
		//	throw new MoteurDeJeuException(e);
		//}
		
		// fill statistiques
		this.nbStep = nbTour;
		return nbTour;
	}
	
	public long getStats() {
		return nbStep;
	}
	
	public String printStats() {
		StringBuilder report = new StringBuilder();
		report.append( String.format("Il y a eu %d tours %n", getStats()));
//		for(Aventurier aventurier: aventuriers) {
//			report.append( aventurier.getNom() + " a collecté " 
//						+ aventurier.getNbTresorsCollectes() + " trésors "
//						+ "et a fini en " + aventurier.getPositionAndOrientation() + "\n");
//		}
		//writer.addContent(report.toString());
		return report.toString(); 
	}
	
	// here too much mix between the configuration and the persistence file
	// proof it is very very unstable
	// one purpose  only => renderTheCurrentFrame
	// In Frame
	
//	public String renderConfiguration() throws InvalidPositionCarteException {
//		
//		Position pos;
//		String[] charsToPrint = new String[ carte.getLargeur()];
//		StringBuilder oneLineFrame = new StringBuilder( carte.getLargeur()*15 );
//		
//		StringBuilder fullFrame = new StringBuilder( carte.getLargeur()*15 * carte.getHauteur());
//		
//		// print the lines, then columns, 2D here
//		for(int j=0; j < carte.getHauteur(); j++) {
//			for(int i=0; i < carte.getLargeur(); i++) {
//				
//				pos = new Position2D(i, j);
//				// start by the morphologie
//				charsToPrint[i] = (carte.getItemOnTerrainAt(pos) == Morphologie.PLAINE)
//				? "."  
//				: carte.getItemOnTerrainAt(pos).toString(); // getLetter() ;
//				
//				// tresor
//				charsToPrint[i] = (carte.getNbCollectableAt(pos) > 0 )
//				? "T(" + carte.getNbCollectableAt(pos) + ")"
//				: charsToPrint[i];
//				
//				//carte.getActors()
//				// loop over aventurier
//				//for(Aventurier aventurier : aventuriers) {
//				//for( var actors : carte.getActors() ) {
//				/*
//					if( aventurier.getPosition().equals(pos))
//						charsToPrint[i] = (charsToPrint[i]== "." ) 
//						? "A(" + aventurier.getNom() + ")"
//						: charsToPrint[i] + "A(" + aventurier.getNom() + ")";
//				}*/					
//			}
//			// format the line and set toWriter
//			oneLineFrame.setLength(0);
//			for(int k= 0; k < carte.getLargeur(); k++) {
//				oneLineFrame.append(String.format("%-12s", charsToPrint[k]));
//			}
//			//writer.addContent( strB.toString() );
//			fullFrame.append( oneLineFrame );
//		}
//		return fullFrame.toString();
//	}
	
	/**
	public void writeToFile() throws MoteurDeJeuException, InvalidPositionCarteException {
		try {
			writer.addContent(printStats());
			writer.toFile();
		} catch (IOException e) {
			throw new MoteurDeJeuException(e);
		}
	}
	*/
}
	
