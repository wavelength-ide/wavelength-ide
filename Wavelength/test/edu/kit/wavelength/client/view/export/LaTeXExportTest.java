package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

public class LaTeXExportTest {

	Export export;

	@Before
	public void initialize() {
		export = new LatexExport();
	}

	@Test(expected = NullPointerException.class)
	public void nullTermsTest() {
		export.getRepresentation(null, Collections.emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void nullLibrariesTest() {
		export.getRepresentation(Collections.emptyList(), null);
	}

	@Test
	public void printEmptyListTest() {
		List<LambdaTerm> list = new ArrayList<LambdaTerm>();

		assertEquals("", export.getRepresentation(list, Collections.emptyList()));
	}

	@Test
	public void printOneElementTest() {
		List<LambdaTerm> list = new ArrayList<LambdaTerm>();
		list.add(new FreeVariable("a"));

		assertEquals("\\Rightarrow\\ a", export.getRepresentation(list, Collections.emptyList()));
	}

	@Test
	public void printListTest() {
		List<LambdaTerm> list = new ArrayList<LambdaTerm>();
		list.add(new FreeVariable("a"));
		list.add(new FreeVariable("b"));
		list.add(new FreeVariable("c"));

		assertEquals("\\Rightarrow\\ a\\\\\n\\Rightarrow\\ b\\\\\n\\Rightarrow\\ c",
				export.getRepresentation(list, Collections.emptyList()));
	}
}
