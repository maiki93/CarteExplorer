package net.ddns.kimai.explorer.inputprovider;

import java.io.IOException;

// not used
public interface IWriter {

	String toString();
	void toFile() throws IOException;
	void addContent(String line);
	void reset();
}
