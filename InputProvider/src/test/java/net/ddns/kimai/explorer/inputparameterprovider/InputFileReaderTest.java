package net.ddns.kimai.explorer.inputparameterprovider;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.ddns.kimai.explorer.inputparameterprovider.InputFileReader;

// Not unit testing, it depends on a real file
// Simple integration test, only for this layer ?
class InputFileReaderTest {

	// @Test
//	void readExistingFile() throws IOException {
//		InputFileReader reader = new InputFileReader("test.txt");
//		reader.readFile();
//		assertTrue( reader.nextLine().length() > 0 );
//	}
	
	@Test
	void readNotExistingFileRaisesException() {
		Assertions.assertThrows(IOException.class, ()-> {
			InputFileReader reader = new InputFileReader("notExisting.txt");
			reader.readFile();
		});
	}
	
//	@Test
//	void checkContentLineByLine() throws IOException {
//		InputFileReader reader = new InputFileReader("test.txt");
//		reader.readFile();
//		String line = reader.nextLine();
//		assertEquals("C - 3 - 4", line.trim());
//		line = reader.nextLine();
//		assertEquals("M - 1 - 0", line.trim());
//		line = reader.nextLine();
//		assertEquals("M - 2 - 1", line.trim());
//		line = reader.nextLine();
//		assertEquals("T - 0 - 3 - 2", line.trim());
//		line = reader.nextLine();
//		assertEquals("T - 1 - 3 - 3", line.trim());
//		line = reader.nextLine();
//		assertEquals("A - Lara - 1 - 1 - S - AADADAGGA", line.trim());
//		line = reader.nextLine();
//		line = reader.nextLine();
//		assertEquals(null, line);
//	}
	
}
