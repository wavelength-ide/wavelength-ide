package edu.kit.wavelength.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.NumberedTerm;

public class UtilitiesTest {

	@Test
	public void numberedTermTest() {
		NumberedTerm term = new NumberedTerm(new FreeVariable("x"), 1);
		NumberedTerm deserial = new NumberedTerm(term.serialize().toString());
		assertEquals(term.getNumber(), deserial.getNumber());
		assertEquals(term.getTerm(), deserial.getTerm());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDeserialization() {
		new NumberedTerm("invalid123");
	}
	
}
