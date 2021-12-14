package net.ddns.kimai.explorer.metier.parseinput;

public class ConfigurationProviderException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ConfigurationProviderException() {
		super();
	}
	
	public ConfigurationProviderException(String message) {
		super(message);
	}
	
	public ConfigurationProviderException(Throwable e) {
		super(e);
	}
}
