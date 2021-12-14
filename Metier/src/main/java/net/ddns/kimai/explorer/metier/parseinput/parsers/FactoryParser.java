package net.ddns.kimai.explorer.metier.parseinput.parsers;

import java.util.function.Function;

import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;

public class FactoryParser {
	
	// 2D, 3D ?
//	static public ParserLineInput<?, ?> createParser(String line) {
//		Function<String, ParserLineInput<?,?>> func = HashParser.getParser(line.substring(0,1));
//		// no need to parse here, but should be builde with a string  Function
//		ParserLineInput<?, ?> parser = func.apply(line);
//		// parser.parse(line);
//		return parser;
//	}

	static public ParserLineInput createParser(String line) {
		Function<String, ParserLineInput> func = HashParser.getParser(line.substring(0,1));
		// should check for null 
		// call constructor with String
		ParserLineInput parser = func.apply(line);
		return parser;
	}
}
