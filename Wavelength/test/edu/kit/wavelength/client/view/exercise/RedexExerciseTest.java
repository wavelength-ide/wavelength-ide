package edu.kit.wavelength.client.view.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.reduction.NormalOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.reduction.TestTermGenerator;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.exercise.BoundVariableResolver;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class RedexExerciseTest {

	final int testIterations = 10000;
	private TestTermGenerator generator;
	private Parser testParser;
	private BasicExportVisitor toString;
	private BoundVariableResolver resolver;
	
	@Before
	public void setUp() {
		testParser = new Parser(new ArrayList<Library>());
		generator = new TestTermGenerator();
		toString = new BasicExportVisitor(new ArrayList<Library>(), "λ");
		resolver = new BoundVariableResolver();
	}
	
	@Test
	public void serializationTest() {
		for (ReductionOrder reduction: ReductionOrders.all()) {
			RedexExercise exercise = new RedexExercise(reduction, new TestTermGenerator());
			assertEquals(exercise.getName(), Exercises.deserialize(exercise.serialize().toString()).getName());
		}
	}
	
	@Test
	public void basicRegexExerciseTest() {
		RedexExercise ex = new RedexExercise(new NormalOrder(), new TestTermGenerator());
		assertFalse(ex.getName().isEmpty());
		assertFalse(ex.getPredefinitions().isEmpty());
		assertFalse(ex.getTask().isEmpty());
		assertFalse(ex.getSolution().isEmpty());
		assertTrue(ex.getName().contains(new NormalOrder().getName()));
		try {
			testParser.parse(ex.getPredefinitions());
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void allReductionTest() {
		for (ReductionOrder reduction: ReductionOrders.all()) {
			RedexExercise exercise = new RedexExercise(reduction, new TestTermGenerator());
			assertFalse(exercise.getName().isEmpty());
			assertFalse(exercise.getTask().isEmpty());
			assertFalse(exercise.getSolution().isEmpty());
			assertTrue(exercise.hasPredefinitions());
			assertFalse(exercise.getPredefinitions().isEmpty());
			assertTrue(exercise.getSolution().contains(reduction.getName()));
			int termEndIndex = exercise.getSolution().lastIndexOf("\"");
			if (termEndIndex != -1) { // = the solution contains a redex
				String solutionRedex = exercise.getSolution().substring(1, termEndIndex - 1);
				try {
					testParser.parse(solutionRedex);
				} catch (ParseException e) {
					fail();
				}
				String noIndices = exercise.getPredefinitions().replaceAll("[0-9]", "");
				String noIndicesRedex = solutionRedex.replaceAll("[0-9]", "");
				assertTrue(noIndices.contains(noIndicesRedex));
			} else {
				exercise.getSolution().contains("no Regex");
			}
		}
	}
	
	@Test
	public void repeatedRegexExerciseTest() {
		for (int i = 0; i < testIterations; i++) {
			allReductionTest();
		}
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void nullTermResolverTest() {
		resolver.resolveVariables(null, new FreeVariable("x"));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void nullSubTermResolverTest() {
		resolver.resolveVariables(new FreeVariable("x"), null);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void invalidTermResolverTest() {
		resolver.resolveVariables(new FreeVariable("x"), new FreeVariable("y"));
	}
	
	@Test
	public void nameRessolverTest() {
		NamedTerm subNameTerm = new NamedTerm("name", new Application(new FreeVariable("y"),
				new Application(new Abstraction("x", new BoundVariable(1)), new BoundVariable(1))));
		LambdaTerm term = new Abstraction("v", subNameTerm);
		LambdaTerm resolvedName = resolver.resolveVariables(term, subNameTerm);
		assertTrue(resolvedName instanceof NamedTerm);
		NamedTerm resName = (NamedTerm) resolvedName;
		assertEquals(resName.getInner(), new Application(new FreeVariable("y"),
				new Application(new Abstraction("x", new BoundVariable(1)), new FreeVariable("v"))));
	}
	
	@Test
	public void basicResolverTest() {
		LambdaTerm term = new Abstraction("x",
				new Application(new Abstraction("y", new BoundVariable(1)), new BoundVariable(1)));
		BoundVariableResolver resolver = new BoundVariableResolver();
		LambdaTerm redex = new NormalOrder().next(term);
		assertTrue(redex != null);
		LambdaTerm resolvedSubTerm = resolver.resolveVariables(term, redex);
		assertTrue(resolvedSubTerm != null);
		String resolvedSubTermString = resolvedSubTerm.acceptVisitor(toString).toString();
		assertTrue(term.acceptVisitor(toString).toString().contains(resolvedSubTermString));
	}
	
	@Test
	public void randomizedResolverTest() {
		for (int i = 0; i < testIterations; i++) {
			for (ReductionOrder reduction: ReductionOrders.all()) {
				LambdaTerm cTerm = generator.getNewTerm(3, 10);
				LambdaTerm cRedex = reduction.next(cTerm);
				if (cRedex != null ) {
					LambdaTerm rSubTerm = resolver.resolveVariables(cTerm, cRedex);
					assertTrue(rSubTerm.acceptVisitor(new IsRedexVisitor()));
					String originalTerm = cTerm.acceptVisitor(toString).toString();
					String subterm = rSubTerm.acceptVisitor(toString).toString();
					String originalTermNoIndex = originalTerm.replaceAll("[0-9]", "");
					String subtermNoIndex = subterm.replaceAll("[0-9]", "");
					assertTrue(originalTermNoIndex.contains(subtermNoIndex));
				}
			}		
		}
	}
}
