package net.ddns.kimai.explorer.inputprovider;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader implements IFileReader {
	
	protected final String path;
	// not needed as member if only one call to readFile
	protected ArrayList<String> fileContent = new ArrayList<>();
	protected int index = 0; // only for nextline
	
	public InputFileReader(String path) {
		this.path = path;
	}
	
	public List<String> readFile() throws IOException {
		/* In lambda pdf page 250, may be more references
		Files.lines(Paths.get(fileName))
		.filter(line -> line.startsWith("ERROR"))
		.limit(40)
		.collect(toList());
		*/
		// to check different methods to open file 
		try (
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(System.getProperty("user.dir") + "/" + path),
					StandardCharsets.UTF_8)) ) {
		
			while (br.ready()) {
				fileContent.add(br.readLine());
			}
		}
		return fileContent;
	}

	// certainly to delete, should read file contents at once
//	@Override
//	public String nextLine() {
//		String line;
//		while( index < fileContent.size()) {
//			line = fileContent.get(index++).strip();
//			if( (! line.isEmpty()) && line.charAt(0) != '#' )
//				return line;
//		}
//		return null;
//	}
//	
	// Maybe a getLineWithFirstCharacter (for Carte/Simulation)
	
	// really needed ?
//	protected boolean isFileExists() {
//		File input = new File(System.getProperty("user.dir") + "/" + path);
//		if( input.exists())
//			return true;
//		return false;
//	}
}
