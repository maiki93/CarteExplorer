package net.ddns.kimai.explorer.metier.parseinput.datainput;

// Group parameters associated to a random setup of the maps
public class RandomProperties {

	//private double porcent;
	private final int nb;
	
	public RandomProperties( int nbItem ) {
		this.nb = nbItem;
	}
	
	public int nbItem() {
		return nb;
	}
}
