package net.ddns.kimai.explorer.inputparameterprovider;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ddns.kimai.explorer.inputparameterprovider.InputParameterFile;
import net.ddns.kimai.explorer.inputparameterprovider.IFileReader;

/**
 * This class will depends on ParserInput (from business layer) 
 * 
 * Should write more in terms of behavior :
 * 	- call to config.addItems
 *	- call    config.setdimension..
 *  - and all calls to parsers as well
 *  rather than testing the state of config
 *  
 *  Class not well written
 *  Parsers not accessible, declared as local variable, bad for behavior test : Inject Parser in constructor
 *  ConfigurationJeu does not allow to check easily, limited state checking
 *  
 *  Update, much less responsabilities => moved to ConfigurationJeuBuilder
 *  need just to  focus on reading file, nb line, and errors
 */
// TODO exception with missing file or problem reading
// to see, readfile could be done in constructor
// to check readFile lazy initialization
@ExtendWith(MockitoExtension.class)
class InputParameterFileTest {

	@Mock //(name = "fileReader") // name optional, found without
	private IFileReader fileReaderMock;
	
	// under test, keep default behavior + spyable it
	// Spy also to be able to inject the dependencies automatically 
	// @Spy : not necessary (to build at least)
	@InjectMocks // allow to create the object automatically
	private InputParameterFile cfgP;
	
	@Test
	void createProviderWithInjectedMock() throws IOException {
		assertThat( fileReaderMock ).isNotNull();
		assertThat( cfgP ).isNotNull();
	}
	
	@Test
	void readEmptyFileNextEntryReturnNull() throws IOException {
		// given emptyFile empty list ?
		when( fileReaderMock.readFile() ).thenReturn(List.of());
		// when
		cfgP.readFile();
		// then, fileReader has been called and next . read only once
		verify( fileReaderMock, times(1) ).readFile();
		assertThat( cfgP.nextEntry()  ).isEqualTo(null);
	}
	
	@Test
	void readOneLine() throws IOException {
		// given
		when( fileReaderMock.readFile() ).thenReturn( Arrays.asList("C - 4 - 5"));
		
		cfgP.readFile();
		String line = cfgP.nextEntry();
		
		assertThat( line  ).isEqualTo("C - 4 - 5");
		assertThat( cfgP.nextEntry()  ).isEqualTo(null);
		// read only once
		verify( fileReaderMock, times(1) ).readFile();
	}
	
	@Test
	void readTwoLines() throws IOException {
		when( fileReaderMock.readFile() ).thenReturn( Arrays.asList("C - 4 - 5",
																	"M - 2 - 1"));
		cfgP.readFile();
		
		assertThat( cfgP.nextEntry()  ).isEqualTo("C - 4 - 5");
		assertThat( cfgP.nextEntry()  ).isEqualTo("M - 2 - 1");
		assertThat( cfgP.nextEntry()  ).isEqualTo( null );
		// read only once	
		verify( fileReaderMock, times(1) ).readFile();
	}
	
	@Test
	void readThreeLinesWithComments() throws IOException {
		when( fileReaderMock.readFile() ).thenReturn( Arrays.asList("#areComments",
																	"C - 4 - 5",
																	"  # spca before comment",
																	"M - 2 - 1",
																	"M - 3 - 2",
																	"# A last comment "));
		cfgP.readFile();
		assertThat( cfgP.nextEntry()  ).isEqualTo("C - 4 - 5");
		assertThat( cfgP.nextEntry()  ).isEqualTo("M - 2 - 1");
		assertThat( cfgP.nextEntry()  ).isEqualTo("M - 3 - 2");
		assertThat( cfgP.nextEntry()  ).isEqualTo( null );
		// read only once	
		verify( fileReaderMock, times(1) ).readFile();	
	}
}
