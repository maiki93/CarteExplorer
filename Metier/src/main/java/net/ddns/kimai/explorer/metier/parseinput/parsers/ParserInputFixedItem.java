package net.ddns.kimai.explorer.metier.parseinput.parsers;

import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.FactoryItem;
import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputFixedItem;
import net.ddns.kimai.explorer.metier.parseinput.datainput.RandomProperties;

// possibility to merge ParserInputItem< CollectableItem, DataInputCollectableItem >
public class ParserInputFixedItem extends ParserLineInput {

	private DataInputFixedItem data;
	
	public ParserInputFixedItem(String line) {
		super(line);
		
		@SuppressWarnings("unchecked")
		Class<? extends FixedItem> clazz = (Class<? extends FixedItem>) FactoryItem.getSupplier( elements.get(0) );
		// random
		if( elements.get(1).equals("R") ) {
			data = new DataInputFixedItem(clazz, new RandomProperties( Integer.parseInt( elements.get(2) ) ));
		// fixed
		} else {
			data = new DataInputFixedItem(clazz, getPosition());
		}
	}

	@Override
	public DataInputFixedItem getData() {
		return data;
	}	
}
