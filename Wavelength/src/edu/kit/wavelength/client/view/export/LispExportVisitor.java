package edu.kit.wavelength.client.view.export;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;

/**
 * This class is a visitor to translate a lambda term into a string using Lisp
 * syntax. However it is not guaranteed that the generated representation is
 * executable Lisp code.
 */
public class LispExportVisitor extends BasicExportVisitor {

	private static final String LAMBDA = "lambda";

	public LispExportVisitor(List<Library> libraries) {
		// argument LAMBDA not needed, since this class overrides the
		// formatLambda method anyways
		super(libraries, LAMBDA);
	}

	@Override
	public StringBuilder visitApplication(Application app) {
		Objects.requireNonNull(app);

		StringBuilder leftSide = app.getLeftHandSide().acceptVisitor(this);
		StringBuilder rightSide = app.getRightHandSide().acceptVisitor(this);

		StringBuilder result = new StringBuilder();
		result.append("(").append(leftSide).append(" ").append(rightSide).append(")");
		return result;
	}

	@Override
	protected StringBuilder visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);
		Objects.requireNonNull(resolvedName);

		StringBuilder absVariable = new StringBuilder(resolvedName);
		StringBuilder innerTerm = new StringBuilder(abs.getInner().acceptVisitor(this));

		StringBuilder result = new StringBuilder();
		result.append("(").append(formatLambda(absVariable)).append(innerTerm).append(")");

		return result;
	}

	@Override
	protected StringBuilder formatLambda(StringBuilder absVariable) {
		assert (absVariable != null);

		absVariable.insert(0, " (").append(") ");
		return absVariable.insert(0, LAMBDA);
	}
}
