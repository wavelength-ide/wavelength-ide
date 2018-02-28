package edu.kit.wavelength.client.model.reduction;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.exercise.TermGenerator;

public class ReductionOrderTest {

	ReductionOrder normal = new NormalOrder();
	ReductionOrder applicative = new ApplicativeOrder();
	ReductionOrder byValue = new CallByValue();
	ReductionOrder byName = new CallByName();
	
	Parser testParser = new Parser(new ArrayList<Library>());
	TermGenerator generator = new TestTermGenerator();
	
	@Test
	public void freeVariableTest() {
		FreeVariable free = new FreeVariable("v");
		assertEquals(normal.next(free), null);
		assertEquals(applicative.next(free), null);
		assertEquals(byValue.next(free), null);
		assertEquals(byName.next(free), null);
	}
	
	@Test
	public void boundVariableTest() {
		BoundVariable bound = new BoundVariable(1);
		assertEquals(normal.next(bound), null);
		assertEquals(applicative.next(bound), null);
		assertEquals(byValue.next(bound), null);
		assertEquals(byName.next(bound), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermNormalTest() {
		normal.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermApplicativeTest() {
		applicative.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermByValueTest() {
		byValue.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermByNameTest() {
		byName.next(null);
	}
	
	@Test
	public void insideAbstractionNormalTest() throws ParseException {
		for (int i = 0; i < 100; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(1, 10 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application innerRedex = normal.next(innerTerm);
			Application absRedex = normal.next(absTerm);
			assertEquals(innerRedex, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionApplicativeTest() throws ParseException {
		for (int i = 0; i < 100; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(1, 10 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application innerRedex = applicative.next(innerTerm);
			Application absRedex = applicative.next(absTerm);
			assertEquals(innerRedex, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionCbVTest() throws ParseException {
		for (int i = 0; i < 100; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(1, 10 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application absRedex = byValue.next(absTerm);
			assertEquals(null, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionCbNTest() throws ParseException {
		for (int i = 0; i < 100; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(1, 10 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application absRedex = byName.next(absTerm);
			assertEquals(null, absRedex);
		}
	}
	
	
}
