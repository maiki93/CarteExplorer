package net.ddns.kimai.explorer.metier.carte.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;

import net.ddns.kimai.explorer.metier.carte.ChercheurTresor;
import net.ddns.kimai.explorer.metier.carte.CollectableItem;
import net.ddns.kimai.explorer.metier.carte.FixedItem;


// In CarteExplorer, how to know that Aventurier have this interface ?
// if extension of behavior by inheritance/interface is not working, 
//    may try by composition
public class Aventurier implements MovingActor, ChercheurTresor {

	private String nom;
	// problem with derived class if static ?
	private static List<FixedItem> obstacle = Arrays.asList( Morphologie.MONTAGNE );
	// ChercheurTresor
	private final List<CollectableItem> backpack = new ArrayList<>();
	
	// added for test in HashParser, must be public for now
	// package private would be a solution, or internal Builder ?
	public Aventurier() {}
	
	public Aventurier(String nom) /*throws InvalidNameAventurierException*/ {
		//if ( nom.contains("-") )
		//	throw new InvalidNameAventurierException(
		//			"Le caractere '-' est interdit dans les noms des aventurier(e)s");
		this.nom = nom;
	}
	
	@Override
	public String getNom() {
		return nom;
	}
	
	// added during during parer... to check how is done in BuilderDSL
	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public boolean isAnObstacle(FixedItem type) {
		return obstacle.contains(type);
	}
	
// ChercheurTresor interface
	@Override
	public void recolteTresor(CollectableItem itemFound) {
		backpack.add(itemFound);
	}
	
	@Override
	public List<CollectableItem> backpack() {
		return backpack;
	}

// renderer service, toFrame() ?
	// @Override
	public String formatFrame() {
		return backpack.size() > 0 
			   ? getNom().substring(0, 2) + "(" + backpack().size() + ")"
		       : getNom().substring(0,2);
	}
}
