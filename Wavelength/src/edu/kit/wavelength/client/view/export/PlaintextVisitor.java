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
 * 2. Nicht Obligatorische Klammern weglassen?
 * 3. Unicode aus vom Benutzer angegebenen Definitionen enfernen?
 */
public class PlaintextVisitor extends ResolvedNamesVisitor<StringBuilder> {

	public PlaintextVisitor(List<Library> libraries) {
		super(libraries);
	}

	@Override
	public StringBuilder visitApplication(Application app) {
		Objects.requireNonNull(app);
		
		StringBuilder leftSide = app.getLeftHandSide().acceptVisitor(this);
		StringBuilder rightSide = app.getRightHandSide().acceptVisitor(this);
		StringBuilder result = new StringBuilder();
		return result.append("(").append(leftSide).append(" ").append(rightSide).append(")");
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
		
		StringBuilder innerTerm = new StringBuilder(abs.acceptVisitor(this));
		StringBuilder result = new StringBuilder();
		return result.append("(\\").append(resolvedName).append(".").append(innerTerm).append(")");
	}
}
