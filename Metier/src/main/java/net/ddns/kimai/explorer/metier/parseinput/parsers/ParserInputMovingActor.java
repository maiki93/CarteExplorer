package net.ddns.kimai.explorer.metier.parseinput.parsers;

import net.ddns.kimai.explorer.metier.simulation.ActionSequence;
import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.carte.item.FactoryItem;

import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.parseinput.ParserLineInput;
import net.ddns.kimai.explorer.metier.parseinput.datainput.DataInputMovingActor;
import net.ddns.kimai.explorer.metier.parseinput.datainput.RandomProperties;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;
import net.ddns.kimai.explorer.metier.position.Orientation;
import net.ddns.kimai.explorer.metier.position.Orientation2D;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;

// Only ParserProvider/ Parsing logic need access. Private package for much of them
public class ParserInputMovingActor extends ParserLineInput {

	private DataInputMovingActor data;
	
	public ParserInputMovingActor(String line) {
		super(line);
		@SuppressWarnings("unchecked")
		Class<? extends MovingActor> clazz = (Class<? extends MovingActor>) FactoryItem.getSupplier( elements.get(0) );
		
		if( elements.get(1).equals("R") ) {
			data = new DataInputMovingActor(clazz, new RandomProperties( Integer.parseInt( elements.get(2) ) ));
		} else {
			data = new DataInputMovingActor(clazz, getActorsProps() );
			data.setNom( getName() );
		}
	}
		
	protected String getName() {
		return elements.get(1);
	}
	
	protected ActorPropsInConfiguration getActorsProps() {
		PositionOrientation2D posOrient = new PositionOrientation2D( getPosition(), getOrientation() );
		ActionSequence actions = ActionSequenceFactory.input( getActions() );
		return new ActorPropsInConfiguration(posOrient, actions);
	}
	
	protected Position getPosition() {
		return new Position2D( Integer.parseInt(elements.get(2)),
				 	           Integer.parseInt(elements.get(3)));
	}
	
	protected Orientation getOrientation() {
		return Orientation2D.fromChar( elements.get(4).charAt(0));
	}
	
	protected PositionOrientation getPositionAndOrientation() {
		return new PositionOrientation2D ( 
				this.getPosition(), this.getOrientation());		
	}
	
	protected String getActions() {
		return elements.get(5);
	}

	@Override
	public DataInputMovingActor getData() {
		return data;
	}	
}
