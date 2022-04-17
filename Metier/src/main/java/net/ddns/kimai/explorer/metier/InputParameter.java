package net.ddns.kimai.explorer.metier;

// purpose is to load ( maybe later write) Parameters from file/console (db, network...)
// SPI secondary port
public interface InputParameter {
	/** 
	 *  Return a input parameter of the simulation.
	 *  When no more data to provide return null
	 */
	String nextEntry();
}
