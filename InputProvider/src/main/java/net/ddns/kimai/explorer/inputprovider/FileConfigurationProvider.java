package net.ddns.kimai.explorer.inputprovider;

import java.io.IOException;
import java.util.List;

import net.ddns.kimai.explorer.metier.InputConfigurationProvider;

// Adapter implementationn
// can use tools provided by the model domain : Parsers

// update inserted in ConfigurationJeuBuilder, 
// will take care of 
// - parsing
// - creation item
// - building ConfigurationJeu

// SRP : read data in file and implement nextEntry() to get the next line
// to check carefully readFile lazyly done
public class FileConfigurationProvider implements InputConfigurationProvider {

	// it is a service
	private final IFileReader fileReader;
	
	// internal data to make its work
	// filled with a typical async call (IO)
	private List<String> fileContent = null;
	private int index = 0;
	 
	public FileConfigurationProvider(IFileReader fileInput ) {
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
	
	// to think about exception, 
	// 	there is an interface in business => 
	//		a specific (business) error fit well FilePersistenceException
	//      but call Parser (from inside layer) => should report this more speicific error as well                
	//@Override
	/*
	public ConfigurationJeu loadInitialConfiguration() {
		// It is a DTO
		ConfigurationJeu config = new ConfigurationJeu();
		String line;
		ParserLineInput pLine;
		
		try {
			readContentOfFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// read Carte dimension
		if( (line = nextLineSkippingComments()) != null ) {
			pLine = FactoryParser.createParser(line); 
			config.setDimensionCarte( (Dimension) pLine.getData().getPropsItem() );
		}
		// read all others : FixedObjects / CollectableItem / MovingActor
		while ( (line = nextLineSkippingComments()) != null ) {
			// use boolean, to improve with exception
			if( ! ConfigurationJeuParserHelper.parse(config, line) )
				throw new RuntimeException();
		}			
		return config;
	}
	*/
}
