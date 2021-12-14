package net.ddns.kimai.explorer.metier.movement;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;

import static net.ddns.kimai.explorer.metier.movement.Avancer.*;
import static net.ddns.kimai.explorer.metier.movement.TournerGauche.*;
import static net.ddns.kimai.explorer.metier.movement.TournerDroite.*;

// Now only static factory is public, nextAction() in Interface
// if keep privacy, no access to actions
// - nextAction

// Should test the "normal constructor" ? no need if cannot be called

@DisplayName("SequenceActionInput")
public class ActionSequenceInputTest {

	private static final String sequenceValid = "ADGAD";
	private static final String sequenceEmpty = "";
	private static final String sequenceInvalid1 = "X";
	private static final String sequenceInvalid2 = "GXD";
	private ActionSequence seqActions;
	
	@Test
	@DisplayName("initialized with a valid string, sequence is not empty")
	void initializationWithValidString() {
		seqActions = ActionSequenceFactory.input( sequenceValid );
		assertSeqIsNotEmpty();
	}
	
	@Test
	@DisplayName("initialized with an empty String, sequence is empty")
	void initializationWithEmptyString() {
		seqActions = ActionSequenceFactory.input( sequenceEmpty );
		assertSeqIsEmpty();
	}
	
	@Test
	@DisplayName("initialized with a invalid string, throw NoSuchElementException")
	void initializationWithInvalidString() {
		assertThrows( NoSuchElementException.class, 
				() -> ActionSequenceFactory.input( sequenceInvalid1 ));
	}
	
	@Test
	@DisplayName("initialized with a string containing 1 invalid character, throw nosuchElementException")
	void initializationWith1InvalidCharacter() {
		assertThrows( NoSuchElementException.class, 
					  () -> ActionSequenceFactory.input( sequenceInvalid2 ));
	}
	
	// Should Test the constructor ?
	@Nested
	@DisplayName("with Empty Sequence, all calls to nextAction return Optional empty")
	class NextActionEmptySequence {
		@BeforeEach
		void init() {
			seqActions = ActionSequenceFactory.input( sequenceEmpty );
		}
		
		@Test
		void checkSequenceIsEmpty() {
			assertSeqIsEmpty();
		}
		
		@Test
		void nextActionReturnEmptyOptional() {
			assertThat( seqActions.nextAction() ).isEqualTo( Optional.empty() );
		}
	}

	@Nested
	@DisplayName("with Valid String ADGAD, nextAction")
	class NextAction{

		@BeforeEach
		void init() {
			seqActions = ActionSequenceFactory.input( sequenceValid );
		}

		@Test
		@DisplayName("first action is Avancer")
		void readFirstAction() {
			assertThat( seqActions.nextAction() ).isEqualTo( Optional.of(AVANCER) );
		}
		
		@Test
		@DisplayName("all 5 actions are valid")
		void checkFiveAction() {
			assertThatConsumeAllActions();
			//assertThat( seqActions.nextAction() ).isEqualTo( Optional.of(AVANCER) );
		}
		
		@Test
		@DisplayName("6th action is empty")
		void emptyWhenSequenceIsEmpty() {
			assertThatConsumeAllActions();
			assertThat( seqActions.nextAction() ).isEqualTo( Optional.empty() );
		}
		
//		@Test
//		void readStringOfValidActionChekOrder() {
//			// other tests
//			// assertEquals( 3, ActionFactory.factoryAction.size());
//			seqActions = ActionSequenceInput.from(  sequenceValid );
//			assertThatConsumeAllActions();
//		}
		
//		@Test
//		void thenSeqIsEmpty() {
//			// same as beofre
//			// seqActions = ActionSequenceInput.from(  sequenceTestOk );
//			assertThatConsumeAllActions();
//			assertSeqIsEmpty();		
//		}
		
//		@Test
//		void nextMoveReturnEmptyoptional() {
//			// same as beofre
//			seqActions = ActionSequenceInput.from(  sequenceTestOk );
//			assertThatConsumeAllActions();
//			
//			assertEquals( Optional.empty(), seqActions.nextAction());
//			
//		}
//		
//		@Test
//		void emptyOptionalIfSequenceIsEmpty() {
//			seqActions = ActionSequenceInput.from(""); 
//			assertEquals( Optional.empty(), seqActions.nextAction());
//		}
	}
	
//	@Test
//	void emptyOptionalWhenSequenceIsEmpty() {
//		seqActions = new ActionSequence("G");
//		assertEquals( Optional.of(TournerGauche.GAUCHE), seqActions.nextAction());
//		assertEquals( Optional.empty(), seqActions.nextAction());
//	}

// Private to Outer class
	private void assertThatConsumeAllActions() {
		assertEquals( AVANCER, seqActions.nextAction().get() );
		assertEquals( DROITE, seqActions.nextAction().get() );
		assertEquals( GAUCHE, seqActions.nextAction().get() );
		assertEquals( AVANCER, seqActions.nextAction().get() );
		assertEquals( DROITE, seqActions.nextAction().get() );
	}
	
	// no more function interface, n
	private void assertSeqIsEmpty() {
		assertThat( seqActions.nextAction() ).isEmpty();
	}
	
	private void assertSeqIsNotEmpty() {
		assertThat( seqActions.nextAction() ).isNotEmpty();
	}
	
}
