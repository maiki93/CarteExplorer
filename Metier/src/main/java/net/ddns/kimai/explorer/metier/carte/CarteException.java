package net.ddns.kimai.explorer.metier.carte;

// lambda checked exception not easy to deal
//public class CarteException extends Exception {
public class CarteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CarteException() {}
	
	public CarteException(String message) {
		super(message);
	}
	
	public CarteException(Throwable cause) {
		super(cause);
	}

	
}