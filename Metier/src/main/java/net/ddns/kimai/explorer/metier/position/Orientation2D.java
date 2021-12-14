package net.ddns.kimai.explorer.metier.position;

// ACHTUNG ! Enumeration MUST BE CLOCKWISE, 
//     for tourneGauche et tourneDroite algorithm
public enum Orientation2D implements Orientation {
	// creation of enum constants call the constructor
	NORD ('N') {
		@Override
		public Position avance(Position pos) {
			return new Position2D(pos.getCartesianCoordinates()[0], 
								  pos.getCartesianCoordinates()[1]-1);
		}
	},
	EST ('E') {
		@Override
		public Position avance(Position pos) {
			return new Position2D(pos.getCartesianCoordinates()[0]+1,
								  pos.getCartesianCoordinates()[1]);
		}
	},
	SUD ('S'){
		@Override
		public Position avance(Position pos) {
			return new Position2D(pos.getCartesianCoordinates()[0],
					              pos.getCartesianCoordinates()[1]+1);
		}
	},
	OUEST ('O') {
		@Override
		public Position avance(Position pos) {
			return new Position2D(pos.getCartesianCoordinates()[0]-1,
					              pos.getCartesianCoordinates()[1]);
		}
	};
	
	public abstract Position avance(Position pos);
	private char direction;

	
	private Orientation2D(char direction) {
		this.direction = direction;
	}
	
	public Orientation getOrientation() {
		return this;
	}
	
	public Orientation tourneDroite() {
		return 
			this.ordinal() < Orientation2D.values().length - 1
			? Orientation2D.values()[this.ordinal() + 1]
			: Orientation2D.NORD;
	}
	
	public Orientation tourneGauche() {
		return 
			this.ordinal() > 0 
			? Orientation2D.values()[this.ordinal() - 1]
			: Orientation2D.OUEST;
	}
	
	public static Orientation fromChar(char c) {
		for(Orientation2D  o : Orientation2D.values()) {
			if( o.direction == c) 
				return o;
		}
		return null;
	}

}
