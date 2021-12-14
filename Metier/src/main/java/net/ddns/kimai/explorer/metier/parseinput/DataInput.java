package net.ddns.kimai.explorer.metier.parseinput;

// Item, PropertiesItem, RandomProperties(only one class at first)
abstract public class DataInput<I, V, R> {
	// using Optional and check, variable not necessary
	protected boolean random = false; // if delete, can be changed to a clean interace
	
	protected Class<? extends I> classItem;
	protected V propsItem;
	protected R propsRandom;
	
	// with Optional, could avoid boolean random
	public boolean isRandom() { return random; } 
	public void setRandom(  boolean newValue ) {
		random = newValue;
	}

	public Class<? extends I> getItemClass() { return classItem; }
	public V getPropsItem() { return propsItem; }
	public R getPropsRandom() { return propsRandom; }
	
	protected abstract <T> T createItem();
	protected int nbItem() {return 1;}
	// does not make sense for Carte / Simulation
	// protected abstract Position getPosition();
}
