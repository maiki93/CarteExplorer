package net.ddns.kimai.explorer.inputprovider;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

// want to check than call to config.set.... are correct
// and error, should be better in Parser tests
// for testing input
// https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input

// 2 ways : 1. use System.in, System.out inside implementation
//             setIn, setOut to temporarly buffer in test
//          2. include generic Input / OutStream in implementation, 
//             and use Sytem.in, System.out (console) in normal execution. output file could be chosen also. 
@ExtendWith(MockitoExtension.class)
class InteractiveConsoleProviderTest {
	// @Mock, not used but may mock the call by Scanner
	private ByteArrayInputStream in;
	private ByteArrayOutputStream os;
	private PrintStream out;
	
	InteractiveConsoleConfigurationProvider interactive;
	
	@BeforeEach
	void init() {
		os = new ByteArrayOutputStream();
		out = new PrintStream( os );
	}
	
	@AfterEach
	void delete() throws IOException {
		os.close();
		out.close();
		in.close();
	}
	
	@Test
	void readOneEntry() {
		in = makeInput( "C - 5 - 6" ); //+ System.lineSeparator() + "end" );
		//System.setIn(in); // no need anymore
		// when
		String tmp;
		interactive = new InteractiveConsoleConfigurationProvider(in, out);
		tmp = interactive.nextEntry();
		assertThat( tmp ).isEqualTo("C - 5 - 6");
		// then
		// System.out.print( os.toString() ); to debug
		assertThat( os.toString() ).startsWith("== Interactive Mode" )
			.isEqualToNormalizingPunctuationAndWhitespace("== Interactive Mode\nAdd item :\n");		
	}
	
	@Test
	void read3EntriesAndEnd() {
		// configProvider will transform end => null
		in = makeInput( "C - 3 - 4 " + System.lineSeparator() 
					  + "M - 2 - 1" + System.lineSeparator()
					  + "M - 0 - 6" + System.lineSeparator()
					  + "end");
		// when
		String tmp;
		interactive = new InteractiveConsoleConfigurationProvider(in, out);
		tmp = interactive.nextEntry();
		assertThat( tmp ).isEqualTo("C - 3 - 4 ");
		tmp = interactive.nextEntry();
		assertThat( tmp ).isEqualTo("M - 2 - 1");
		tmp = interactive.nextEntry();
		assertThat( tmp ).isEqualTo("M - 0 - 6");
		tmp = interactive.nextEntry();
		assertThat( tmp ).isNull();
		// 4entries have been asked, take into account newline present/or not 
		assertThat( os.toString() ).containsPattern("(Add item : [\n]{0,1}){4}");	
	}
	
	@Test
	void printHelp() {
		in = makeInput("help" //+ System.lineSeparator()  
     				 + "end" );
		interactive = new InteractiveConsoleConfigurationProvider(in, out);
		interactive.nextEntry();
		
		assertThat( os.toString() ).contains("Add item")
								   .contains("help:")
		 						   .contains("ex: C");	
	}
	
	private ByteArrayInputStream makeInput( String str ) {
		return new ByteArrayInputStream( str.getBytes() );
	}

}
