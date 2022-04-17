package net.ddns.kimai.explorer.inputparameterprovider;

import java.io.IOException;
import java.util.List;

import net.ddns.kimai.explorer.metier.InputParameter;

// SRP : read data in file and implement nextEntry() to get the next line
// to check carefully readFile lazyly done
public class InputParameterFile implements InputParameter {

	private final IFileReader fileReader;
	
	// internal data to make its work
	// to be filled with a typical async call on IO
	private List<String> fileContent = null;
	private int index = 0;
	 
	public InputParameterFile(IFileReader fileInput ) {
		this.fileReader = fileInput;
		// when to read ? here at creation ?
	}

	// avoid null by "EOF" or Optional.empty() better ?
	@Override
	public String nextEntry() {
		// lazy initialization, not bad
		if( fileContent == null )
			readFile();
		
		return nextLineSkippingComments();
	}
	
	// specific to file implementation, easy if called at construction
	public void readFile() {
		try {
			readContentOfFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// must read the data in file, when ? to make public ?
	private void readContentOfFile() throws IOException {
		fileContent = fileReader.readFile();
		this.index = 0;
	}
	
	private String nextLineSkippingComments() {
		String line;
		while( index < fileContent.size()) {
			line = fileContent.get(index++).strip();
			if( (! line.isEmpty()) && line.charAt(0) != '#' )
				return line;
		}
		return null;
	}
}
