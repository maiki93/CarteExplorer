package net.ddns.kimai.explorer.metier.parseinput.parsers;

import java.util.Map;
import java.util.function.Function;

import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;

// Probel witnh enumération, not easy too extends
// Strange bug in fileProviderwithMockTest, 
//    looks like Parser provided are static 
//       elements conserved their previous values

// from previous test
//"M", new ParserInputItem<FixedItem>(),
//"T", new ParserInputItem<CollectableItem>(),


// to include in ParserFactory provider ? make sense
public class HashParser {

//			"C", new ParserInputCarte(),
//			"A", new ParserInputMovingActor()
//		);
	
	
	static Map< String, Function<String, ParserLineInput>> mapParser = Map.of(
			// dimension and simulation, max steps e.g.
			"C", ParserInputCarte::new,
			"M", ParserInputFixedItem::new,
			"T", ParserInputCollectableItem::new,
			"A", ParserInputMovingActor::new
		);

	static public Function<String, ParserLineInput> getParser(String name) {
		return mapParser.get(name);
	}
}
