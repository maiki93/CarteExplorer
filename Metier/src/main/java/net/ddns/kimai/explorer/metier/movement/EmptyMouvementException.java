package net.ddns.kimai.explorer.metier.movement;

public class EmptyMouvementException extends MouvementException {

	private static final long serialVersionUID = 1L;

	public EmptyMouvementException() {
		super();
	}
	
	public EmptyMouvementException(String message) {
		super(message);
	}

}
