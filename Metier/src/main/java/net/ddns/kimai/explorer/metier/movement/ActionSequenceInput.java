package net.ddns.kimai.explorer.metier.movement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.simulation.MovingAction;

// here for dealing with RandomAction
// certainly would win flexibility with an interface of it

// moved into explorerCarte, module very cohersive now
//     but here dependency on ActionFactory (outside ths package)
// Injection required to break this dependence, 
// and/or Interface nextAction() , used by 

// try change :
// Make a static Constructor fromString()
// 

// make package protected, test
class ActionSequenceInput implements ActionSequence {
	
	// boolean randomSequence
	// private problem with Test, injection is a solution ?
	// with Queue or Deque make  the purpose
	private final Deque<MovingAction> actions;
	
	// not tested, but cannot be called
	private ActionSequenceInput(List<MovingAction> actions) {
		this.actions = new ArrayDeque<MovingAction>( actions );	
	}
	
	// Cleaner, many changes to do in Test
	// Static method in a interface can return derived type (option, RANDOM)
	// Not need to explicity need to create an Object, can cache it, RandomActionSequence may reference only one Object and next return a random number
	
	static ActionSequenceInput from(String seqCharacter) {
		// cetainly possible to improve the readabilty !
		// If invalidCaraters "X" => List created but [null]
		// If                 "GXD" => List created but [Gau, null, Droite]
		// => error in construcotr new ArrayDequeue
		List<MovingAction> actions = seqCharacter.codePoints()
									 .mapToObj( c -> String.valueOf((char) c))
									 .map(str -> str.charAt(0))
									 .map(ActionFactory::getAction)
									 .collect(Collectors.toCollection(LinkedList::new));
		return new ActionSequenceInput( actions );
	}
	
	@Override
	public Optional<MovingAction> nextAction() {
		// mapOrThrow... something like this
		try {
			return Optional.of(actions.removeFirst());
		} catch( NoSuchElementException e)  {
			return Optional.empty();
		}
	}
	
	//public boolean isEmpty() {
	//	return actions.isEmpty();
	//}
}
