package net.ddns.kimai.explorer.metier.parseinput.datainput;

import net.ddns.kimai.explorer.metier.parseinput.DataInput;
import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.utils.Dimension;

public class DataInputCarte extends DataInput<Carte, Dimension, RandomProperties> {
	
	public DataInputCarte( Class<? extends Carte> clazz, Dimension dim) {
		this.classItem = clazz;
		this.propsItem = dim;
	}

	@Override
	protected <Carte> Carte createItem() {
		// TODO Auto-generated method stub
		return null;
	}
}
