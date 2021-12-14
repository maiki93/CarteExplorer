package net.ddns.kimai.explorer.metier.builders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

/** Moved to ConfigurationJEU, builder functionality
 * Only used here */
class MoveSequenceTest {

	static final PositionOrientation defaultPosOrient = 
						PositionOrientation2D.create( 2, 2, 'E');
	
	
	private static final PositionOrientation afterAvanceEST =
						PositionOrientation2D.create( 3, 2, 'E');

	ActionSequence seqActions;
	ActorPropsInConfiguration moveSequence;
	
	@Test
	void creationInstance() {
		seqActions = ActionSequenceFactory.input("ADGA");
		moveSequence = new ActorPropsInConfiguration(defaultPosOrient, seqActions);
		
		assertEquals( defaultPosOrient, moveSequence.getPositionOrientation() );
	}
	
//	@Test
//	void nextMoveAvanceEST() {
//		seqActions = ActionSequenceFactory.input("ADGA");
//		moveSequence = new MoveSequence(defaultPosOrient, seqActions);
//		Optional<PositionOrientation> newPos = moveSequence.nextMove();
//		
//		assertEquals(afterAvanceEST, newPos.get());
//	}	
}
