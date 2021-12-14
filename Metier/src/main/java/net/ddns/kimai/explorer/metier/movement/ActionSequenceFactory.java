package net.ddns.kimai.explorer.metier.movement;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;

// here show a lot of details , a package private FactoryHelper could hide details
// many constructors can be done private / package private now
// not so extensible, FactoryHelper certainly can help

// NoSuchElementException if one action is unknow, best to catch ? 
public class ActionSequenceFactory {
	
	// private ActionSequenceFactoryHelper details;
	
	public enum TYPE_ACTION_SEQUENCE {
		INPUT, RANDOM;
	}

	// NoSuchElementException if one action is unknow
	// nicer
	static public ActionSequence input( String seqCharacters ) {
		return from(seqCharacters, TYPE_ACTION_SEQUENCE.INPUT);
	}
	
	// can make the name I want : ActionSequenceFactory.input() / ...random()
	// or more generic, lookup in a HashMap like in others factories implementation
	static public ActionSequence from(String seqCharacter) {
		return from(seqCharacter, TYPE_ACTION_SEQUENCE.INPUT);
	}
	
	// to hide in Helper
	static public ActionSequence from(String seqCharacter, TYPE_ACTION_SEQUENCE type) {
		switch( type ) {
			case INPUT : return ActionSequenceInput.from(seqCharacter);
			//case RANDOM : return ActionSequenceRandom.from()
			default : throw new IllegalArgumentException();
		}
	}
}
