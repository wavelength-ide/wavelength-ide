package edu.kit.wavelength.client.model.reduction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.exercise.TermGenerator;

public class ReductionOrderTest {

	int testIterations = 1000;
	
	ReductionOrder normal;
	ReductionOrder applicative;
	ReductionOrder byValue;
	ReductionOrder byName;
	
	Parser parser;
	TestTermGenerator generator;
	
	@Before
	public void setUp() {
		normal = new NormalOrder();
		applicative = new ApplicativeOrder();
		byValue = new CallByValue();
		byName = new CallByName();
		parser = new Parser(new ArrayList<Library>());
		generator = new TestTermGenerator();
	}
	
	@Test
	public void allTest() {
		List<ReductionOrder> all = ReductionOrders.all();
		assertEquals(all.size(), 4);
	}
	
	@Test
	public void serializationTest() {
		ReductionOrder deserialized = ReductionOrders.deserialize(normal.serialize().toString());
		assertEquals(normal.getName(), deserialized.getName());
		deserialized = ReductionOrders.deserialize(applicative.serialize().toString());
		assertEquals(applicative.getName(), deserialized.getName());
		deserialized = ReductionOrders.deserialize(byValue.serialize().toString());
		assertEquals(byValue.getName(), deserialized.getName());
		deserialized = ReductionOrders.deserialize(byName.serialize().toString());
		assertEquals(byName.getName(), deserialized.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullDeserialization() {
		ReductionOrders.deserialize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyDeserialization() {
		ReductionOrders.deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDeserialization() {
		ReductionOrders.deserialize("k");
	}
	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void nullTermNormalTest() {
		normal.next(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullTermApplicativeTest() {
		applicative.next(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullTermByValueTest() {
		byValue.next(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullTermByNameTest() {
		byName.next(null);
	}
	
	@Test
	public void insideAbstractionNormalTest() throws ParseException {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(1, 10 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application innerRedex = normal.next(innerTerm);
			Application absRedex = normal.next(absTerm);
			assertEquals(innerRedex, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionApplicativeTest() throws ParseException {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(0, 100 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application innerRedex = applicative.next(innerTerm);
			Application absRedex = applicative.next(absTerm);
			assertEquals(innerRedex, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionCbVTest() throws ParseException {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(0, 100 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application absRedex = byValue.next(absTerm);
			assertEquals(null, absRedex);
		}
	}
	
	@Test
	public void insideAbstractionCbNTest() throws ParseException {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm innerTerm = generator.getNewTerm(0, 100 , 1, 1);
			LambdaTerm absTerm = new Abstraction("x", innerTerm);
			Application absRedex = byName.next(absTerm);
			assertEquals(null, absRedex);
		}
	}
	
	@Test
	public void callByNameRedexTest() {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm redex = new Application(generator.getRandomAbstraction(), generator.getNewTerm(0, 100));
			assertEquals(redex, byName.next(redex));
		}
	}
	
	@Test
	public void callByNameApplicationTest() {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm left = generator.getNewTerm(0, 100);
			LambdaTerm right = generator.getNewTerm(0, 100);
			LambdaTerm term = new Application(left, right);
			LambdaTerm leftRedex = byName.next(left);
			LambdaTerm rightRedex = byName.next(right);
			if (term.acceptVisitor(new IsRedexVisitor())) {
				assertEquals(term, byName.next(term));
			} else if (leftRedex != null) {
				assertEquals(leftRedex, byName.next(term));
			} else {
				assertEquals(rightRedex, byName.next(term));
			}
		}
	}

	@Test
	public void callByValueAbsRedexTest() {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm abs = generator.getRandomAbstraction();
			LambdaTerm otherTerm = generator.getRandomAbstraction();
			LambdaTerm possibleRedex = new Application (abs, otherTerm);
			assertEquals(possibleRedex, byValue.next(possibleRedex));
		}	
	}
	
	@Test
	public void callByValueAppRedexTest() {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm abs = generator.getRandomAbstraction();
			LambdaTerm otherTerm = generator.getRandomApplication();
			LambdaTerm possibleRedex = new Application (abs, otherTerm);
			assertNotEquals(possibleRedex, byValue.next(possibleRedex));
		}
	}
	
	@Test
	public void callByValueVarRedexTest() {
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm abs = generator.getRandomAbstraction();
			LambdaTerm otherTerm = generator.getRandomFreeVariable();
			LambdaTerm possibleRedex = new Application (abs, otherTerm);
			assertNotEquals(possibleRedex, byValue.next(possibleRedex));
		}
	}
	
	@Test
	public void callByValueBoundVarRedexTest() {
		// tests whether a bound variable is determined to be a value
		// this case never occurs as long as the "next"-method is invoked on a valid term
		for (int i = 0; i < testIterations; i++) {
			LambdaTerm redex = new Application(generator.getRandomAbstraction(), new BoundVariable(1));
			assertEquals(byValue.next(redex), null);
		}
	}
	
	@Test
	public void callByValuePartialAppRedexTest() {
		PartialApplication[] pApps = { new PartialApplication.Addition(),
				new PartialApplication.Subtraction(), new PartialApplication.Exponentiation(),
				new PartialApplication.Multiplication(), new PartialApplication.Predecessor(),
				new PartialApplication.Successor() };
		for (PartialApplication partialApp: pApps) {
			LambdaTerm app = new Application(generator.getRandomAbstraction(), partialApp);
			assertEquals(app, byValue.next(app));
		}
	}
	
	@Test
	public void partialApplicationTest() {
		PartialApplication[] pApps = { new PartialApplication.Addition(),
				new PartialApplication.Subtraction(), new PartialApplication.Exponentiation(),
				new PartialApplication.Multiplication(), new PartialApplication.Predecessor(),
				new PartialApplication.Successor() };
		for (PartialApplication partialApp: pApps) { 
			assertEquals(normal.next(partialApp), normal.next(partialApp.getRepresented()));
			assertEquals(applicative.next(partialApp), applicative.next(partialApp.getRepresented()));
			assertEquals(byName.next(partialApp), byName.next(partialApp.getRepresented()));
			assertEquals(byValue.next(partialApp), byValue.next(partialApp.getRepresented()));
		}
	}
	
}
