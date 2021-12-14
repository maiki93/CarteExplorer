package net.ddns.kimai.explorer.metier.position;
 
public class PositionOrientation2D implements PositionOrientation {
	
	private final Position position;
	private final Orientation orientation;
	
	public PositionOrientation2D() {
		this.position = new Position2D(0,0);
		this.orientation = Orientation2D.NORD;
	}
	
	public PositionOrientation2D(Position position, Orientation orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	public PositionOrientation2D(PositionOrientation2D original) {
		this.position = new Position2D( original.getPosition().getCartesianCoordinates()[0],
									  original.getPosition().getCartesianCoordinates()[1] );
		this.orientation = original.getOrientation();
	}
	
	static public PositionOrientation2D create( int posX, int posY, char c) {
		return new PositionOrientation2D( new Position2D( posX, posY),
											Orientation2D.fromChar(c) );
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		PositionOrientation2D other = (PositionOrientation2D) obj;
		if (orientation != other.orientation)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return position.toString() + " - "+ orientation.toString();
	}
}
