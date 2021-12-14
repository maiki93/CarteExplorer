package net.ddns.kimai.explorer.metier.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// it is also renderMdJ test
class MdJWriterTest {
/* Should be in external layer
	MdJWriter writer;
	
	private ArrayList<String> fileContent = 
			 new ArrayList<>( 
				Arrays.asList("C - 3 - 4",
							  "# Obstacles",
						      "M - 1 - 0",
	                          "M - 2 - 1",
	                          "T - 0 - 2 - 2",
	                          "#Commentaire",
							  "T - 1 - 3 - 3",
							  // Lara rencontre un obstacle puis prend la place d'indiana
							  "A - Lara - 1 - 1 - E - ADA", // Lara prend la place d'indiana
							  // Indiana prend le trésor à gauche, puis fait demi-tour, 
							  // mais place prise par Lara
							  "A - Indiana - 1 - 2 - O - AGGA",
							  // Toto veut sortir de la carte 
							  "A - tOto - 2 - 0 - N - ADADA"));

	// add access to aventurier... I test the Fake not the main class.. 
	private FakeMoteurDeJeu mdj;
	
	@BeforeEach
	void init() throws IOException, ParserException, CarteException, AventurierException {
		FakeInputFileReader input = new FakeInputFileReader("dummy");
		input.resetContent(fileContent);
		SimulationBuilder mdjB = new SimulationBuilder(input);
		mdjB.createComposants();
		mdj = new FakeMoteurDeJeu(mdjB.getCarte(), mdjB.getAventuriers());
		writer = new MdJWriter("dummy");
		mdj.setWriter( writer );
	}
	
	
	@Test
	void create() {
		MdJWriter writer2 = new MdJWriter("dummy");
		assertNotNull( writer2 );
	}
	
	@Test
	void addContent() {
		MdJWriter writer2 = new MdJWriter("dummy");
		writer2.addContent("toto");
		assertEquals( "toto\n", writer2.getContent());
	}
	
	@Test
	void writeTwoLine() {
		writer.addContent("toto");
		writer.addContent("titi");
		assertEquals( "toto\ntiti\n", writer.getContent());
	}
	
	@Test
	void renderInitialConfiguration() throws InvalidPositionCarteException {
		String allLines = mdj.renderConfiguration();
		String[] lines = allLines.split("\n");
		assertEquals(4, lines.length);
		assertEquals(".           M           A(tOto)     ", lines[0]);
		assertEquals(".           A(Lara)     M           ", lines[1]);
		assertEquals("T(2)        A(Indiana)  .           ", lines[2]);
		assertEquals(".           T(3)        .           ", lines[3]);
	}
	*/
}
