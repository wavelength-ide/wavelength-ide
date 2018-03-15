package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NaturalNumbersTest {

	@Test
	public void containsValidNumber() {
		NaturalNumbers library = new NaturalNumbers(false);
		for(int i = 0; i <= 1000; i++){
			assertTrue(library.containsName(Integer.toString(i)));
		}
		
		library = new NaturalNumbers(true);
		for(int i = 0; i <= 1000; i++){
			assertTrue(library.containsName(Integer.toString(i)));
		}
	}
	
	@Test
	public void containsInvalidNumber() {
		NaturalNumbers library = new NaturalNumbers(false);
		assertFalse(library.containsName("-1"));
		assertFalse(library.containsName("00"));
		assertFalse(library.containsName("008"));
		assertFalse(library.containsName("a"));
		assertFalse(library.containsName(""));
		assertFalse(library.containsName(null));
	}
}
