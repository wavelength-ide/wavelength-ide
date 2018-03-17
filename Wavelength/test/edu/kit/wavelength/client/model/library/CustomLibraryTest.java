package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;

public class CustomLibraryTest {

	@Test
	public void addElement() {
		CustomLibrary library = new CustomLibrary("testLib");
		assertFalse(library.containsName("a"));
		library.addTerm(new FreeVariable("a"), "a");
		assertTrue(library.containsName("a"));
		assertEquals(library.getTerm("a"), new FreeVariable("a"));
		assertFalse(library.containsName("b"));
		library.addTerm(new Abstraction("x", new BoundVariable(1)), "b");
		assertTrue(library.containsName("b"));
		assertEquals(library.getTerm("b"), new Abstraction("x", new BoundVariable(1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument1() {
		CustomLibrary library = new CustomLibrary("testLib");
		library.addTerm(null, "a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument2() {
		CustomLibrary library = new CustomLibrary("testLib");
		library.addTerm(new FreeVariable("a"), "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument3() {
		CustomLibrary library = new CustomLibrary("testLib");
		library.addTerm(new FreeVariable("a"), null);
	}
	
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void insertDuplicate() {
		CustomLibrary library = new CustomLibrary("testLib");
		library.addTerm(new FreeVariable("a"), "a");
		library.addTerm(new FreeVariable("b"), "a");
	}
}
