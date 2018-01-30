package edu.kit.wavelength.client.view.export;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;

/*
 * TODO:
 * 1. StringBuilder zu ressourcen intensiv?
 */
/**
 * A basic Visitor to create a string representing a given lambda term. The
 * Visitor tries to set a minimal number of brackets to correctly describe the
 * lambda term.
 * 
 * It also provides some means to vary the representation of a lambda term by
 * overwriting strategy methods.
 */
public class BasicExportVisitor extends ResolvedNamesVisitor<StringBuilder> {

	protected final String lambda;

	/*
	 * A flag that determines if an abstraction or application should be
	 * surrounded by brackets. All flags should be false at the end of a visit
	 * method.
	 */
	protected boolean bracketsForApp;
	protected boolean bracketsForAbs;

	/**
	 * Creates a new Visitor for {@link LambdaTerm}s.
	 * 
	 * It will build a String (represented by a {@link StringBuilder}) from the
	 * lambda term with a custom representation for the lambda-letter.
	 * 
	 * @param libraries
	 *            the libraries of the application that are used in this term
	 * @param lambdaRepresentation
	 *            the string representation of the lambda letter
	 */
	public BasicExportVisitor(List<Library> libraries, String lambdaRepresentation) {
		super(libraries);
		Objects.requireNonNull(lambdaRepresentation);

		lambda = lambdaRepresentation;
		bracketsForApp = false;
		bracketsForAbs = false;
	}

	@Override
	public StringBuilder visitApplication(Application app) {
		Objects.requireNonNull(app);

		// Store bracketsFlag for later use.
		final boolean brackets = bracketsForApp;
		resetFlags();

		// An abstraction inside a application should get brackets.
		bracketsForAbs = true;
		StringBuilder leftSide = app.getLeftHandSide().acceptVisitor(this);

		bracketsForAbs = true;
		// If the right term of an application is an application it should get
		// brackets because implicit brackets bind from left to right.
		bracketsForApp = true;
		StringBuilder rightSide = app.getRightHandSide().acceptVisitor(this);

		StringBuilder result = new StringBuilder();
		result.append(leftSide).append(" ").append(rightSide);
		if (brackets) {
			result.insert(0, "(").append(")");
		}
		return result;
	}

	@Override
	public StringBuilder visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		resetFlags();
		return formatText(new StringBuilder(term.getName()));
	}

	/*
	 * TODO: Markus fragen, was hier los ist.
	 */
	@Override
	public StringBuilder visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);

		resetFlags();
		return formatText(new StringBuilder(app.getName()));
	}

	@Override
	public StringBuilder visitFreeVariable(FreeVariable var) {
		Objects.requireNonNull(var);

		resetFlags();
		return new StringBuilder(var.getName());
	}

	@Override
	protected StringBuilder visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);
		Objects.requireNonNull(resolvedName);

		resetFlags();
		return new StringBuilder(resolvedName);
	}

	@Override
	protected StringBuilder visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);
		Objects.requireNonNull(resolvedName);

		// Store bracketsFlag for later use and reset flags.
		final boolean brackets = bracketsForAbs;
		resetFlags();

		StringBuilder absVariable = new StringBuilder(resolvedName);

		// An application inside an abstraction should get brackets.
		bracketsForApp = true;
		StringBuilder innerTerm = new StringBuilder(abs.getInner().acceptVisitor(this));

		StringBuilder result = new StringBuilder();
		result.append(formatLambda(absVariable)).append(innerTerm);

		if (brackets) {
			result.insert(0, "(").append(")");
		}
		return result;
	}

	/**
	 * Resets all Flags for brackets. Flags should be false at the end of each
	 * visit method.
	 */
	protected final void resetFlags() {
		bracketsForAbs = false;
		bracketsForApp = false;
	}

	/**
	 * A strategy method to allow inheriting classes to define the
	 * representation of text in the constructed String.
	 * 
	 * @param text
	 *            the text of the lambda term
	 * @return the text as it should be represented in the final string
	 */
	protected StringBuilder formatText(StringBuilder text) {
		assert (text != null);

		return text;
	}

	/**
	 * A strategy method to allow inheriting classes to define the
	 * representation of the left part from an abstraction in the constructed
	 * String.
	 * 
	 * @param absVariable
	 *            the variable of the abstraction of the lambda term
	 * @return the left part of an abstraction as it should be represented in
	 *         the final string
	 */
	protected StringBuilder formatLambda(StringBuilder absVariable) {
		assert (absVariable != null);

		return absVariable.insert(0, lambda).append(".");
	}
}
