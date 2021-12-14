package net.ddns.kimai.explorer.metier.movement;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.ddns.kimai.explorer.metier.simulation.MovingAction;

// implemented as static, strange/difficult to test
// singleton, easier ?? to test
// works as package private (only for tests), for the moment
class ActionFactory {
	
	static private HashMap<Character, MovingAction> factoryAction = new HashMap<>();
	// when is it executed ? not working as expected...
	// TODO check init of static variables
	static {
		factoryAction.put('A', Avancer.AVANCER );
		factoryAction.put('G', TournerGauche.GAUCHE);
		factoryAction.put('D', TournerDroite.DROITE );
		//factoryAction.put('R', factory.getRandom() )
	}

	// only usedi n Test, once...
	// throw RuntimeException if a match is not found
	static MovingAction getAction(Character c) {
		return Optional.ofNullable( factoryAction.get(c) )
				       .orElseThrow( ()-> new NoSuchElementException() );
	}
	
//	public static MovingAction addAction(Character c, MovingAction newAction) {
//		// if R => Reserved keyword
//		return factoryAction.putIfAbsent(c, newAction);
//	}
	
	//public static MovingAction getRandomAction() {
	//	return Avancer.AVANCER;
	//}
	
// alternative	
//	static HashMap<Character, Supplier<MovingAction> > factoryAction2;
//	static {
//		factoryAction2 = new HashMap<>();
//		factoryAction2.put('A', Avancer::new);
//	}

}
