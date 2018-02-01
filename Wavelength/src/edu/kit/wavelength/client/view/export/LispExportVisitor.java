package edu.kit.wavelength.client.view.export;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;

/**
 * This class is a visitor to translate a lambda term into a string using Lisp
 * syntax. However it is not guaranteed that the generated representation is
 * executable Lisp code.
 */
public class LispExportVisitor extends BasicExportVisitor {

	private static final String LAMBDA = "lambda";

	public LispExportVisitor(List<Library> libraries) {
		super(libraries, "lambda");
	}

	@Override
	protected StringBuilder formatLambda(StringBuilder absVariable) {
		assert (absVariable != null);

		absVariable.insert(0, " (").append(") ");
		return absVariable.insert(0, LAMBDA);
	}
}
