package net.ddns.kimai.explorer.metier.simulation;

public interface Simulator {

	long runSimulation();
	// certainly not the best palce
	void checkInitialConfiguration(); //throws MoteurDeJeuException;
	
	//void makeOneStep(); //throws MoteurDeJeuException;
	//void setWriter(IWriter writer);
	// other business Model, must be implemented appart	
}
