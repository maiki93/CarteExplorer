package net.ddns.kimai.explorer.metier.builders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.metier.simulation.Carte;
import net.ddns.kimai.explorer.metier.parseinput.ConfigurationJeu;
import net.ddns.kimai.explorer.metier.parseinput.ActorPropsInConfiguration;
import net.ddns.kimai.explorer.metier.carte.item.Aventurier;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.carte.item.Tresor;
import net.ddns.kimai.explorer.metier.movement.ActionSequenceFactory;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.position.PositionOrientation2D;
import net.ddns.kimai.explorer.metier.utils.Dimension;

// tests not clear. test final Carte or/and Test calls to factories 
// see changes with injection of factory provider
class CarteBuilderTest {

	//private static final int[] dimension = new int[] {3,3};
	private static final Dimension dimension = Dimension.of(3,3);
	
	static final ActorPropsInConfiguration posAction = new ActorPropsInConfiguration(
			PositionOrientation2D.create(1, 1, 'E'), 
			ActionSequenceFactory.input("ADAG"));

	static final ActorPropsInConfiguration posActionOut = new ActorPropsInConfiguration(
			PositionOrientation2D.create(1, 5, 'E'), 
			ActionSequenceFactory.input("ADAG"));

	// dependencies
	private ConfigurationJeu cfg;
	private FactoryComponent factory;
	private CarteBuilder carteB;
	
	@BeforeEach
	void init() {
		cfg = new ConfigurationJeu();
		cfg.setDimensionCarte( dimension );
		factory = new FactoryComponent2D( dimension );
		
		carteB = new CarteBuilder(cfg, factory);
	}
	
	@Test
	void setupTerrainWith2Montagnes() {
		cfg.addFixedItem(Morphologie.MONTAGNE, Position2D.create(1, 1));
		cfg.addFixedItem(Morphologie.MONTAGNE, Position2D.create(2, 2));
		
		carteB.buildFromConfiguration();
		
		assertEquals(Morphologie.MONTAGNE, carteB.terrain.getFixedItem(Position2D.create(1, 1)));
		assertEquals(Morphologie.MONTAGNE, carteB.terrain.getFixedItem(Position2D.create(2, 2)));
		assertEquals(Morphologie.PLAINE, carteB.terrain.getFixedItem(Position2D.create(0, 0)));
	}
	
	@Test
	void setupCollectableWith3Tresors() {
		cfg.addCollectableItem( new Tresor(), Position2D.create(1, 1));
		cfg.addCollectableItem( new Tresor(), Position2D.create(2, 2));
		
		carteB.buildFromConfiguration();
		
		assertEquals(Tresor.class, carteB.tresors.collect( Position2D.create(1,1)).get().getClass() );
		assertEquals(Tresor.class, carteB.tresors.collect( Position2D.create(2,2)).get().getClass() );
	}
	
	@Test
	void setupCollectableWith2Aventurier() {
		cfg.addMovingActors( new Aventurier("Lara"), posAction);
		cfg.addMovingActors( new Aventurier("Indiana"), posActionOut);
		
		Carte carte = carteB.buildFromConfiguration();
		
		assertEquals(2, carteB.actors.getActors().size() );
		assertEquals(2, carte.getActors().size() );
	}
	
}
