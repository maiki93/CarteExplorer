package net.ddns.kimai.explorer.metier.parseinput.datainput;

import java.lang.reflect.InvocationTargetException;

import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.parseinput.DataInput;
import net.ddns.kimai.explorer.metier.position.Position;

// DataInputItem<T> DataInput<T, Position, Random>
public class DataInputCollectableItem extends DataInput<CollectableItem, Position, RandomProperties> {
	// only for collectable
	private int nbItem;
	
	public DataInputCollectableItem( Class<? extends CollectableItem> clazz, Position position) {
		this.classItem = clazz;
		this.propsItem = position;
		this.random = false;
	}
	
	public DataInputCollectableItem( Class<? extends CollectableItem> clazz, RandomProperties randomP) {
		this.classItem = clazz;
		this.propsRandom = randomP;
		this.random = true;
	}

	@Override
	public int nbItem() {
		return nbItem;
	}

	public void setNbItem(int nbItem) {
		this.nbItem = nbItem;
	}
	
	public CollectableItem createItem() {
		CollectableItem item;
		try {
			item = (CollectableItem) getItemClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Error in creating CollectableItem from DataInput");
		}
		return item;
	}
}
