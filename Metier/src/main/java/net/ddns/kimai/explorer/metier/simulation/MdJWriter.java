package net.ddns.kimai.explorer.metier.simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** like for builder, need a SPI to call the persistence layer persistence
import net.ddns.kimai.inputoutput.IWriter;

public class MdJWriter implements IWriter {
	
	protected ArrayList<String> content = new ArrayList<>();
	String filename;
	
	public MdJWriter(String filename) {
		this.filename = filename;
	}
	
	public String toString() {
		return getContent();
	}
	
	public void reset() {
		this.content = new ArrayList<String>();
	}
	
	public void toFile() throws IOException {
		BufferedWriter writerB = null;
		try {
			writerB = new BufferedWriter(new FileWriter(filename));
			writerB.write( getContent() );
		} catch( IOException e ) {
			throw e;
		} finally {
			writerB.close();
		}
	}
	
	public void addContent(String line) {
		content.add(line);
	}
	
	protected String getContent() {
		StringBuilder strB = new StringBuilder();
		for (String line : content) {
			strB.append(line + "\n");
		}
		return strB.toString();
	}
}
*/
