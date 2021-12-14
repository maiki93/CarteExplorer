package net.ddns.kimai.explorer.metier.parseinput.parsers;

import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.item.FactoryItem;
import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCollectableItem;
import net.ddns.kimai.explorer.metier.parseinput.datainput.RandomProperties;

// possibility to merge ParserInputItem< CollectableItem, DataInputCollectableItem >
public class ParserInputCollectableItem extends ParserLineInput {

	private DataInputCollectableItem data;
	
	public ParserInputCollectableItem(String line) {
		super(line);
		
		@SuppressWarnings("unchecked")
		Class<? extends CollectableItem> clazz = (Class<? extends CollectableItem>) FactoryItem.getSupplier( elements.get(0) );
		// random
		if( elements.get(1).equals("R") ) {
			data = new DataInputCollectableItem(clazz, new RandomProperties( Integer.parseInt( elements.get(2) ) ));
		// fixed
		} else {
			data = new DataInputCollectableItem(clazz, getPosition() );
			int nbItem = 1;
			if( elements.size() == 4 )
				nbItem = Integer.parseInt( elements.get(3) );
			data.setNbItem( nbItem );
		}
	}

	@Override
	public DataInputCollectableItem getData() {
		return data;
	}
}
