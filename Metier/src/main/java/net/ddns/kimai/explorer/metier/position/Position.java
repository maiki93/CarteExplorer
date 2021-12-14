package net.ddns.kimai.explorer.metier.position;

public interface Position {

	// generic for position
	int[] getCartesianCoordinates();
	// getEulerCoordinates
	
	public int hashCode();
	public boolean equals(Object obj);
	//public String toString();
}
