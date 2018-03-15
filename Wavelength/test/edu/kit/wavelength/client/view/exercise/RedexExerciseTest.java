package edu.kit.wavelength.client.view.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.exercise.BoundVariableResolver;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class RedexExerciseTest {

	final int testIterations = 1000;
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
	public void basicResolverTest() {
		LambdaTerm term = new Abstraction("x",
				new Application(new Abstraction("y", new BoundVariable(1)), new BoundVariable(1)));
		BoundVariableResolver resolver = new BoundVariableResolver();
		LambdaTerm redex = new NormalOrder().next(term);
		assertTrue(redex != null);
		resolver.resolveVariables(term, redex);
		assertTrue(resolver.getResolvedSubTerm() != null);
		String resolvedSubTermString = resolver.getResolvedSubTerm().acceptVisitor(toString).toString();
		assertTrue(term.acceptVisitor(toString).toString().contains(resolvedSubTermString));
	}
	
	@Test
	public void randomizedResolverTest() {
		for (int i = 0; i < testIterations; i++) {
			for (ReductionOrder reduction: ReductionOrders.all()) {
				LambdaTerm cTerm = generator.getNewTerm(3, 10);
				LambdaTerm cRedex = reduction.next(cTerm);
				if (cRedex != null ) {
					resolver.resolveVariables(cTerm, cRedex);
					LambdaTerm rSubTerm = resolver.getResolvedSubTerm();
					
					System.out.println("original: " + cTerm.acceptVisitor(toString));
					System.out.println("resolved: " + rSubTerm.acceptVisitor(toString));
					System.out.println("----------------");
						
					assertTrue(rSubTerm.acceptVisitor(new IsRedexVisitor()));
					
					String originalTerm = cTerm.acceptVisitor(toString).toString();
					String subterm = rSubTerm.acceptVisitor(toString).toString();
					assertTrue(originalTerm.contains(subterm));
				}
			}		
		}
	}
}
