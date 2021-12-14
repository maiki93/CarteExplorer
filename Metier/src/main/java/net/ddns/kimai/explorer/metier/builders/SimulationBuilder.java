package net.ddns.kimai.explorer.metier.builders;

// Could be an implementation in CarteExplorer
import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.simulation.CarteExplorer;
import net.ddns.kimai.explorer.metier.simulation.InputActionSeqOfActors;
import net.ddns.kimai.explorer.metier.simulation.MoveAndRecolteService;
import net.ddns.kimai.explorer.metier.simulation.MoveService;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.simulation.Simulator;
import net.ddns.kimai.explorer.metier.utils.Pair;

import java.util.Map;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.carte.CarteException;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;

public class SimulationBuilder {

	protected final ConfigurationJeu cfgJeu;
	protected final Carte carte;	
	
//	protected static final String ERROR_CARTE_NOT_INITIALIZED = "La Carte n'a pas été initialisée. "
//			+ "Vérifiez l'ordre dans le ficher d'input";
	
	public SimulationBuilder(Carte carte, ConfigurationJeu cfgJeu) throws CarteException {
		this.carte = carte;
		this.cfgJeu = cfgJeu;
	}
	
	public Simulator build() {
		MoveService moveService =
			new MoveAndRecolteService ( carte,  
										new InputActionSeqOfActors( formatActorsActionToMoveService() ));
		return new CarteExplorer(carte, moveService);
	}
	
	// extract ok, but add many dependencies
	private Map<MovingActor, ActionSequence> formatActorsActionToMoveService() {
		return cfgJeu.getMovingActors().stream()  
			   .collect(Collectors.toMap( Pair::item,
					   					  pair -> pair.value().getActionSequence())); 
	}
}
