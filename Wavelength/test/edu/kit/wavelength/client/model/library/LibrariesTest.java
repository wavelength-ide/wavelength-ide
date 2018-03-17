package edu.kit.wavelength.client.model.library;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.FreeVariable;

import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class LibrariesTest {
	
	@Test
	public void namesTest() {
		List<Library> libs = Libraries.all();
		String[] names = { "Accelerated Natural Numbers",
				"Natural Numbers",
				"Church Boolean",
				"Church Tuples and Lists",
				"Y-Combinator" };
		for (int i = 0; i < libs.size(); i++) {
			assertEquals(names[i], libs.get(i).getName());
		}
	}
	
	@Test
	public void customLibraryTest() {
		CustomLibrary cl1 = new CustomLibrary("firstTestLibrary");
		CustomLibrary cl1Clone = new CustomLibrary("firstTestLibraryClone");
		CustomLibrary cl2 = new CustomLibrary("secondTestLibrary");
		assertFalse(cl1.equals(null));
		assertTrue(cl1.equals(cl2));
		assertTrue(cl1.equals(cl1Clone));
		cl1.addTerm(new FreeVariable("x"), "x");
		cl1.addTerm(new FreeVariable("x"), "x");
		cl2.addTerm(new FreeVariable("x"), "notX");
		assertFalse(cl1.equals(cl2));	
	}

	@Test
	public void serializeStaticLibrary() {
		Boolean bool = new Boolean();
		assertTrue(Libraries.deserialize(bool.serialize().toString()) instanceof Boolean);
		TuplesAndLists tAndL = new TuplesAndLists();
		assertTrue(Libraries.deserialize(tAndL.serialize().toString()) instanceof TuplesAndLists);
		YCombinator yComb = new YCombinator();
		assertTrue(Libraries.deserialize(yComb.serialize().toString()) instanceof YCombinator);
	}

	@Test
	public void serializeNaturalNumbers() {
		NaturalNumbers nNumbers = new NaturalNumbers(false);
		assertEquals(nNumbers, Libraries.deserialize(nNumbers.serialize().toString()));
		nNumbers = new NaturalNumbers(true);
		assertEquals(nNumbers, Libraries.deserialize(nNumbers.serialize().toString()));
	}

	@Test
	public void serializeCustomLibrary() {
		CustomLibrary cLib = new CustomLibrary("ichBraucheEinenNamen");
		assertEquals(cLib, Libraries.deserialize(cLib.serialize().toString()));
		cLib.addTerm(new FreeVariable("a"), "a");
		assertEquals(cLib, Libraries.deserialize(cLib.serialize().toString()));
		cLib.addTerm(new FreeVariable("b"), "b");
		assertEquals(cLib, Libraries.deserialize(cLib.serialize().toString()));
		
		CustomLibrary cLib2 = new CustomLibrary("ichBraucheEinenName");
		assertNotEquals(cLib, Libraries.deserialize(cLib2.serialize().toString()));
		
		cLib.addTerm(new FreeVariable("b"), "b");
		cLib.addTerm(new FreeVariable("a"), "a");
		assertNotEquals(cLib, Libraries.deserialize(cLib2.serialize().toString()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument1() {
		Libraries.deserialize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument2() {
		Libraries.deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgument3() {
		Libraries.deserialize("invalidString");
	}
}
