package net.ddns.kimai.explorer.metier;

// purpose is to load (/ write) ConfigurationJeu from file/console (db, network...)
// API port secondary
public interface InputConfigurationProvider {
	/** 
	 *  Return a input parameter of the simulation.
	 *  When no more data to provide return null
	 */
	String nextEntry();
}
