package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This class translates the current output into Lisp code. Since it is only
 * a syntactic translation, it is not guaranteed that the generated output is
 * executable Lisp.
 */
public class LispExport implements Export {

	@Override
	public String getRepresentation(List<LambdaTerm> displayedTerms) {
		// format repr
		return null;
	}

	@Override
	public String getName() {
		return "Lisp";
	}

}
