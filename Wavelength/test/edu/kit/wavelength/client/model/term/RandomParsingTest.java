package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class RandomParsingTest {

	private RandomLambdaFactory factory;
	private BasicExportVisitor exportVisitor;
	private Parser parser;

	/**
	 * Will set up the parser and the export visitor for the tests.
	 */
	@Before
	public void setUp() {

		exportVisitor = new BasicExportVisitor(Collections.emptyList(), "\\");
		parser = new Parser(new ArrayList<Library>());
	}

	@Test
	public void simpleTest() throws ParseException {
		factory = new RandomLambdaFactory(100);
		
		for (int i = 0; i < 100; i++) {
			LambdaTerm randomTerm = factory.generateLambdaTerm();
			System.out.println("Simple Nr." + i + " " + randomTerm.acceptVisitor(exportVisitor) + "\n");
			assertEquals(randomTerm, parser.parse(randomTerm.acceptVisitor(exportVisitor).toString()));
			assertEquals(randomTerm, LambdaTerm.deserialize(randomTerm.serialize().toString()));
		}
	}
	
	@Test
	public void moreParametersTest() throws ParseException {
		factory = new RandomLambdaFactory(50, 0.75, 0.9, 0.25);

		for (int i = 0; i < 100; i++) {
			LambdaTerm randomTerm = factory.generateLambdaTerm();
			System.out.println("Advance Nr." + i + " " + randomTerm.acceptVisitor(exportVisitor) + "\n");
			assertEquals(randomTerm, parser.parse(randomTerm.acceptVisitor(exportVisitor).toString()));
			assertEquals(randomTerm, LambdaTerm.deserialize(randomTerm.serialize().toString()));
		}
	}
}
