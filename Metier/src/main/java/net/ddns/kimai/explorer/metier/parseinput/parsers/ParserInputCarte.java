package net.ddns.kimai.explorer.metier.parseinput.parsers;

// interface
import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputCarte;

import net.ddns.kimai.explorer.metier.carte.item.FactoryItem;
import net.ddns.kimai.explorer.metier.utils.Dimension;

//public class ParserInputCarte extends ParserLineInput<Carte,Dimension> {
public class ParserInputCarte extends ParserLineInput {

	private DataInputCarte data;
	
	public ParserInputCarte(String line) {
		super(line);
		@SuppressWarnings("unchecked")
		Class<? extends Carte> clazz = (Class<? extends Carte>) FactoryItem.getSupplier( elements.get(0) );
		data = new DataInputCarte(clazz, getDimension() );
		// no random for Carte
	}
		
	@Override
	public DataInputCarte getData() {
		return data;
	}

	private Dimension getDimension() {
		// inspects size of line
		int dim = elements.size() - 1;
		if (dim != 2) 
			throw new ParserException("Seulement Dimension 2 implémentée, dim = " + dim);
		
		return Dimension.of( Integer.parseInt(elements.get(1) ),
							 Integer.parseInt(elements.get(2) ));
	}
}
