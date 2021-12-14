package net.ddns.kimai.explorer.metier.position;

public interface Orientation {

	Orientation getOrientation();
	Position avance(Position pos);
	Orientation tourneDroite();
	Orientation tourneGauche();
}
