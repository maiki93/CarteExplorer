package net.ddns.kimai.explorer.metier.parseinput;

import java.util.ArrayList;
import java.util.List;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;
import net.ddns.kimai.explorer.metier.utils.RandomGenerator;

// provide functionalities for generating random map of items
// make an interafce similar to the previous static ConfigurationJeuHelper
// to see if possible for lazy init (random may not be used)
public class RandomizeItem {
	
	// injected for stubbing, 
	// and generaly usefull for random number generation
	private final RandomGenerator rdm;
	
	// not sure it is needed, convenient for now
	// not available at creation, if want to kee pinjected
	private Dimension dim = null;
	
	enum Strategy {
		MAX_ONE_ITEM, MULTIPLE_ITEM;
	}
	
	public RandomizeItem( RandomGenerator randomGenerator ) {
		this.rdm = randomGenerator;
	}
	
	void setDimension( Dimension dimension ) {
		this.dim = dimension;
	}
	
	List<Position> generateRandomPosition( int nbItem, Strategy strategyItem ) {
		return generateRandomPosition(nbItem, strategyItem, List.of());
	}
// general interface, nbItem contained in DataInput
//                    DataInput contains the type also, 
//                    			? use here ? or keep in Builder
//                            
	List<Position> generateRandomPosition( int nbItem, Strategy strategyItem, 
										   List<Position> forbidden ) {
		// strong assertion for debug mode
		assert dim != null : "Dimension is not set";
		
		List<Position> randomPosition = new ArrayList<>( nbItem );
		// border case, but should not be called ?? need really to test ?
		if( nbItem == 0)
			return randomPosition;
		
		while( randomPosition.size() < nbItem ) {
			Position pos = generateRandomPosition();
			// exclude forbidden Position
			if( forbidden.contains( pos ) )
				continue;
			// if exclude multiple items at the same position
			if( ( strategyItem == Strategy.MAX_ONE_ITEM ) 
					&& ( randomPosition.contains( pos ) ) )
				continue;
			// if pass, insert 
			randomPosition.add( pos );
		}
		return randomPosition;
	}

	// why not public, or package private ? same for ActorProp ?
	private Position generateRandomPosition() {
		int x = rdm.nextInt( dim.getElement(0));
		int y = rdm.nextInt( dim.getElement(1));
		return Position2D.create(x, y);
	}

	// not used here, only by ConfigurationJeuBuilder
	public ActorPropsInConfiguration randomActorProps(Position position) {
		PositionOrientation posOrient = 
				PositionOrientation2D.create( position.getCartesianCoordinates()[0], //rdm.nextInt(dim.getElement(0)),
											  position.getCartesianCoordinates()[1], //rdm.nextInt(dim.getElement(1)),	
											  'N');
		
		ActionSequence action = ActionSequenceFactory.from("AA"); //, TYPE_ACTION_SEQUENCE.RANDOM 
	
		return new ActorPropsInConfiguration( posOrient, action );
	}
}
