package edu.kit.wavelength.client.model.library;

import java.util.List;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.output.ResultOnly;
import edu.kit.wavelength.client.model.reduction.NormalOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.LambdaTerm;
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
				engine.stepForward();
			}
		}
	}

	public static LambdaTerm reduce(String term, List<Library> libraries) throws ParseException {
		return reduce(term, new NormalOrder(), libraries);
	}
}
