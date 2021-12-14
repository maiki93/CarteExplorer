package net.ddns.kimai.explorer.metier.movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.simulation.MovingAction;

import net.ddns.kimai.explorer.metier.position.Orientation;
import net.ddns.kimai.explorer.metier.position.Orientation2D;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

class MovingActionTest {

	private static final Position defaultPos = new Position2D(0,0);
	private static final Orientation defaultOrient = Orientation2D.NORD;
	private static final PositionOrientation defaultPosOrient = 
							new PositionOrientation2D(defaultPos, defaultOrient);
	
//	private static class NewAction implements MovingAction {
//		public static final NewAction NEW_ACTION = new NewAction();
//		
//		@Override
//		public PositionAndOrientation nextPosition(PositionAndOrientation actualPosition) {
//			return actualPosition;
//		}
//	}
	
	// very difficult to test the static HashMap
	//@BeforeEach
	//void init() { 
	//	ActionFactory.factoryAction.clear();
	//}
		
	@Test
	void creerActionByConstant() {
		MovingAction action = Avancer.AVANCER;
		assertEquals( -1, action.nextPosition(defaultPosOrient).getPosition().getCartesianCoordinates()[1]);
	}
	
	@Test
	void avancerNordDecrementY() {
		MovingAction action = new Avancer();
		assertEquals( -1, action.nextPosition(defaultPosOrient).getPosition().getCartesianCoordinates()[1]);
		assertEquals( 0, action.nextPosition(defaultPosOrient).getPosition().getCartesianCoordinates()[0]);
		assertEquals( Orientation2D.NORD, action.nextPosition(defaultPosOrient).getOrientation());	
	}
	
	@Test
	void tournerGaucheFromNordExpectsOuest() {
		MovingAction action = TournerGauche.GAUCHE;
		assertEquals( Orientation2D.OUEST, action.nextPosition(defaultPosOrient).getOrientation());		
	}
	
	@Test
	void tournerDroiteFromNordExpectsEst() {
		MovingAction action = TournerDroite.DROITE;
		assertEquals( Orientation2D.EST, action.nextPosition(defaultPosOrient).getOrientation());		
	}
	
	@Test
	void getRegisteredAction() {
		assertEquals( Avancer.AVANCER, ActionFactory.getAction('A'));
	}
	
	@Test
	void getUnregisteredActionReturnNull() {
		assertThrows( NoSuchElementException.class, 
					  ()-> ActionFactory.getAction('Y'));
	}
	
	//@Test
	// TODO void getRandomAction
	
//	@Test
//	void addingNewAction() {
//		int previous_size = ActionFactory.factoryAction.size();
//		ActionFactory.addAction('N', NewAction.NEW_ACTION);
//		assertEquals( NewAction.NEW_ACTION, ActionFactory.getAction('N'));
//		assertEquals( previous_size + 1, ActionFactory.factoryAction.size());
//	}
	
//	@Test
//	void adding2SimilarActionShouldKeepOneOnly() {
//		int previous_size = ActionFactory.factoryAction.size();
//		ActionFactory.addAction('N', NewAction.NEW_ACTION);
//		ActionFactory.addAction('N', NewAction.NEW_ACTION);
//		assertEquals( NewAction.NEW_ACTION, ActionFactory.getAction('N'));
//		assertEquals( previous_size + 1, ActionFactory.factoryAction.size());
//	}
}
