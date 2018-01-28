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
 * 2. Nicht Obligatorische Klammern weglassen? Habe ich (glaube ich) geschafft
 * 3. Unicode aus vom Benutzer angegebenen Definitionen enfernen?
 */
public class BasicExportVisitor extends ResolvedNamesVisitor<StringBuilder> {

	protected final String lambda;

	/**
	 * Creates a new Visitor for {@link LambdaTerm}s.
	 * 
	 * It will build a String (represented by a {@link StringBuilder}) from the
	 * lambda term with a custom representation for the lambda-letter.
	 * 
	 * @param libraries
	 *            the libraries of the application that are used in this term
	 * @param lambdaRepresentation
	 *            the string representation of the lambda-letter
	 */
	public BasicExportVisitor(List<Library> libraries, String lambdaRepresentation) {
		super(libraries);
		lambda = lambdaRepresentation;
	}

	@Override
	public StringBuilder visitApplication(Application app) {
		Objects.requireNonNull(app);

		StringBuilder leftSide = app.getLeftHandSide().acceptVisitor(this);
		StringBuilder rightSide = app.getRightHandSide().acceptVisitor(this);
		if (app.getLeftHandSide() instanceof Abstraction) {
			leftSide.insert(0, "(").append(")");
		}
		if (app.getRightHandSide() instanceof Abstraction || app.getRightHandSide() instanceof Application) {
			rightSide.insert(0, "(").append(")");
		}
		StringBuilder result = new StringBuilder();
		return result.append(leftSide).append(" ").append(rightSide);
	}

	@Override
	public StringBuilder visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		return new StringBuilder(term.getName());
	}

	@Override
	public StringBuilder visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);

		return new StringBuilder(app.getName());
	}

	@Override
	public StringBuilder visitFreeVariable(FreeVariable var) {
		Objects.requireNonNull(var);

		return new StringBuilder(var.getName());
	}

	@Override
	protected StringBuilder visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);
		Objects.requireNonNull(resolvedName);

		return new StringBuilder(resolvedName);
	}

	@Override
	protected StringBuilder visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);
		Objects.requireNonNull(resolvedName);

		StringBuilder innerTerm = new StringBuilder(abs.getInner().acceptVisitor(this));
		if (abs.getInner() instanceof Abstraction) {
			innerTerm.insert(0, "(").append(")");
		}
		StringBuilder result = new StringBuilder();
		return result.append(lambda).append(resolvedName).append(".").append(innerTerm);
	}
}
