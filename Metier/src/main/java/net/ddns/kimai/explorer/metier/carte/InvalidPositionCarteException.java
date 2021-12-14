package net.ddns.kimai.explorer.metier.carte;

public class InvalidPositionCarteException extends CarteException {

	private static final long serialVersionUID = 1L;

	public InvalidPositionCarteException() {}
	
	public InvalidPositionCarteException(Throwable cause) {
		super(cause);
	}

	public InvalidPositionCarteException(String message) {
		super(message);
	}
}
