package net.ddns.kimai.explorer.metier.carte;

// Tresor could be in the carte => collect() in CollectableItemsOnCarte (should extends ?)
// Tresor could be in backpack => stealBackPack() 
// Not clear if it is usefull this type/interface , or just convenient for setup of the model
public interface CollectableItem /* extends Cloneable */ {
	// sounds not good, need only for ConfigurationJeu, Parsing
	//CollectableItem clone();
}
