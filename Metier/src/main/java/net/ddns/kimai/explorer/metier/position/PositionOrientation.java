package net.ddns.kimai.explorer.metier.position;

public interface PositionOrientation {

	Position getPosition();
	Orientation getOrientation();
	
	int hashCode();
	boolean equals(Object obj);
	String toString();
}
