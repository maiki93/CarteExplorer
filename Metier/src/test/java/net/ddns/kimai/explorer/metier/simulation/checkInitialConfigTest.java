package net.ddns.kimai.explorer.metier.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import net.ddns.kimai.explorer.metier.simulation.MdJInitialConfigException;
//import net.ddns.kimai.explorer.metier.simulation.MoteurDeJeuException;

// will place aventurier and tresor on different position
class checkInitialConfigTest {
/*
	private ArrayList<String> fileContent = 
			 new ArrayList<>( 
				Arrays.asList("C - 3 - 4",
							  "# Obstacles",
						      "M - 1 - 0",
	                          "M - 2 - 1",
	                          "T - 0 - 2 - 2",
	                          "T - 1 - 0  -1"));
	
	// add access to aventurier... I test the Fake not the main class.. 
	private FakeMoteurDeJeu mdj;
	MdJWriter writer;
	FakeInputFileReader input;
	
	
	@BeforeEach
	void init() throws IOException, ParserException, CarteException, AventurierException {
		input = new FakeInputFileReader("dummy");
		input.resetContent(fileContent);
	}
	
	
	@Test
	void aventurierOnMontagneRaisesException() throws IOException, ParserException, CarteException, AventurierException {
		input.addContent("A - Lara - 2 - 1 - E - ADA");
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		Assertions.assertThrows(MdJInitialConfigException.class, ()-> {
			mdj.checkInitialConfiguration();
		});
	}
	
	@Test
	void aventurierOnMontagneDisqualifie() throws IOException, ParserException, CarteException, AventurierException {
		input.addContent("A - Lara - 2 - 1 - E - ADA");
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		// should be a test of Simulator
//		try {
//			mdj.checkInitialConfiguration();
//		} catch( MdJInitialConfigException e) {
//			assertEquals(0,mdj.aventuriers.size());
//		}
	}
	
	@Test
	void deuxAventuriersMemePositionRaisesException() throws ParserException, CarteException, AventurierException, IOException {
		input.addContent("A - Lara - 1 - 1 - E - ADA");
		input.addContent("A - Indiana - 1 - 1 - O - ADAG");
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		Assertions.assertThrows(MdJInitialConfigException.class, ()-> {
			mdj.checkInitialConfiguration();
		});
	}
	
	@Test
	void deuxAventuriersMemePositionSecondDisqualifie() throws ParserException, CarteException, AventurierException, IOException {
		input.addContent("A - Lara - 1 - 1 - E - ADA");
		input.addContent("A - Indiana - 1 - 1 - O - ADAG");
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		assertEquals(2,mdj.aventuriers.size());

//		try {
//			mdj.checkInitialConfiguration();
//		} catch( MdJInitialConfigException e) {
//			assertEquals(1,mdj.aventuriers.size());
//			assertEquals("Lara",mdj.getAventurier(0).getNom());
//		};
	}
	
	@Test
	void deuxAventuriersSurMontagneRaisesMdJException() throws IOException, ParserException, CarteException, AventurierException {
		input.addContent("A - Lara - 1 - 0 - E - ADA");
		input.addContent("A - Indiana - 1 - 0 - O - ADAG");
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		writer = new MdJWriter("dummy");
		mdj.setWriter( writer );
		assertEquals(2,mdj.aventuriers.size());
		
		Assertions.assertThrows(MoteurDeJeuException.class, ()-> {
			mdj.runJeu();
		});
	}	
*/
}
