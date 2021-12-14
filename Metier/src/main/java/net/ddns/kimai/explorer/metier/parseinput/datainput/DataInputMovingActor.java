package net.ddns.kimai.explorer.metier.parseinput.datainput;

import java.lang.reflect.InvocationTargetException;

import net.ddns.kimai.explorer.metier.simulation.MovingActor;
import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.parseinput.DataInput;

public class DataInputMovingActor extends DataInput<MovingActor, ActorPropsInConfiguration, RandomProperties> {
	// additional attribute, Actor have a name
	private String nom;
	
	public DataInputMovingActor( Class<? extends MovingActor> clazz, ActorPropsInConfiguration propsItem ) {
		this.classItem = clazz;
		this.propsItem = propsItem;
		this.random = false;
	}
	
	public DataInputMovingActor( Class<? extends MovingActor> clazz, RandomProperties propsRandom ) {
		this.classItem = clazz;
		this.propsRandom = propsRandom;
		this.random = true;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public MovingActor createItem() {
		MovingActor actor = null;
		try {
			actor = (MovingActor) getItemClass().getDeclaredConstructor().newInstance();
			actor.setNom( getNom() );
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Error in creating MovingActor for DataInput");
		}
		return actor;
	}
	
}
