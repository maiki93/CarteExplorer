package net.ddns.kimai.explorer.metier.position;

public class Position2D implements Position {

	int positionX;
	int positionY;
	
	public Position2D(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public Position2D(Position2D original) {
		this.positionX = original.getX();
		this.positionY = original.getY();
	}
	
	public Position2D() {
		this(0,0);
	}
	
	public static Position create(int positionx, int positiony) {
		return new Position2D(positionx, positiony);
	}
	
	@Override
	public int[] getCartesianCoordinates() {
		return new int[] { positionX, positionY };
	}
		
	int getX() {
		return positionX;
	}

	int getY() {
		return positionY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + positionX;
		result = prime * result + positionY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position2D other = (Position2D) obj;
		if (positionX != other.positionX)
			return false;
		if (positionY != other.positionY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return positionX + " - " + positionY;
	}
}
