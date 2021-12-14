package net.ddns.kimai.explorer.metier.parseinput;

import java.util.ArrayList;

import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;

// Split of responsabilities : Peading the data (parsing from String) and return DataInput
// Data storage : DataInput family of class 

// Parametric base class, solve many of the weak compile type checking
// not nice to use if we want a base reference ParserLinePinput<?,?>
//abstract public class ParserLineInput<T,V> {
abstract public class ParserLineInput {

	// can store 2D, 3D here ? recreated every line...
	protected static final String DELIMITER = "-"; 
	// protected int dimension[];
	protected ArrayList<String> elements = new ArrayList<>();

	abstract public DataInput<?,?,?> getData();
	
	protected ParserLineInput(String line) {
		splitLine(line.strip());
	}
	
	protected void splitLine(String line) {
		elements = new ArrayList<>();
		String[] elts = line.split(DELIMITER);
		for (String elt : elts) {
			elements.add(elt.trim());
		}
	}

	protected Position getPosition() {
		return new Position2D( Integer.parseInt(elements.get(1)),
	   						   Integer.parseInt(elements.get(2)));
	}
}
