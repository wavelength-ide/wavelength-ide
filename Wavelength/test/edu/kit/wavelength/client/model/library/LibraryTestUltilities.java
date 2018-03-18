package edu.kit.wavelength.client.model.library;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.ExecutionException;
import edu.kit.wavelength.client.model.output.ResultOnly;
import edu.kit.wavelength.client.model.reduction.NormalOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.GetChurchNumberVisitor;
import edu.kit.wavelength.client.model.term.IsChurchNumberVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.parsing.ParseException;

public class LibraryTestUltilities {

	/**
	 * Reduces a given term until there is no more valid redex.
	 * 
	 * @param term
	 *            the term to reduce
	 * @param order
	 *            the reduction order after the term should be reduced
	 * @param libraries
	 *            the libraries to consider when parsing the term
	 * @return the fully reduced lambda term
	 * @throws ParseException
	 *             if the term could not be parsed
	 */
	public static LambdaTerm reduce(String term, ReductionOrder order, List<Library> libraries) throws ParseException {

		ExecutionEngine engine = new ExecutionEngine(term, order, new ResultOnly(), libraries);

		while (true) {
			if (engine.isFinished()) {
				return engine.getDisplayed().get(engine.getDisplayed().size() - 1);
			} else {
				try {
					engine.stepForward();
				} catch (ExecutionException ex) {
					fail("An error occurred during execution.");
				}
			}
		}
	}

	public static LambdaTerm reduce(String term, List<Library> libraries) throws ParseException {
		return reduce(term, new NormalOrder(), libraries);
	}
	
	public static void assertRepresents(int i, LambdaTerm t) {
		assertTrue(t.acceptVisitor(new IsChurchNumberVisitor()));
		assertEquals(new Integer(i), t.acceptVisitor(new GetChurchNumberVisitor()));
	}
	
	public static LambdaTerm apply(PartialApplication app, int arg1, int arg2) {
		LambdaTerm interm = apply(app, arg1);
		assertThat(interm, instanceOf(PartialApplication.class));
		return apply((PartialApplication)interm, arg2);
	}
	
	public static LambdaTerm apply(PartialApplication app, int arg) {
		return app.accept(LambdaTerm.churchNumber(arg));
	}
}
