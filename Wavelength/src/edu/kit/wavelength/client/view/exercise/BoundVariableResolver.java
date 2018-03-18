package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.Visitor;

/**
 * This visitor is used by the {@link RegexExercise} class to ensure the sub
 * term making up the predefined term's redex can be exported to a String.
 *
 */
public class BoundVariableResolver implements Visitor<LambdaTerm> {

	private List<String> abstractedVariables;
	private LambdaTerm subTerm;
	private boolean inSubTerm;
	private int maxDeBruijn;
	private LambdaTerm resolvedSubTerm;

	/**
	 * Turn every bound variable in the entered sub term whose corresponding
	 * abstraction is outside the sub term into a FreeVariable with the the bound
	 * variables preferred name. The resulting sub term is a correct lambda term,
	 * containing no faulty indices.
	 * 
	 * @param term
	 *            The term containing the subterm
	 * @param subTerm
	 *            The sub term whose bound variables to replace
	 * @return The sub term with every bound variable adjusted.
	 */
	public LambdaTerm resolveVariables(LambdaTerm term, LambdaTerm subTerm) {
		if (term == null || subTerm == null) {
			throw new IllegalArgumentException("No parameter may be null");
		}
		abstractedVariables = new ArrayList<String>();
		this.subTerm = subTerm;
		inSubTerm = false;
		resolvedSubTerm = null;
		maxDeBruijn = 0;
		term.acceptVisitor(this);
		if (resolvedSubTerm == null) {
			throw new IllegalArgumentException("The input term does not contain the input sub term.");
		}

		return resolvedSubTerm;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		if (abs == subTerm) {
			inSubTerm = true;
		}
		if (inSubTerm) {
			maxDeBruijn++;
		}
		abstractedVariables.add(abs.getPreferredName());
		LambdaTerm localTerm = new Abstraction(abs.getPreferredName(), abs.getInner().acceptVisitor(this));
		if (abs == subTerm) {
			this.resolvedSubTerm = localTerm;
		}
		return localTerm;

	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		if (app == subTerm) {
			inSubTerm = true;
		}

		int currentMaxDeBruijn = maxDeBruijn;
		ArrayList<String> currentAbstractedVars = new ArrayList<String>(abstractedVariables);
		boolean inside = inSubTerm;

		LambdaTerm left = app.getLeftHandSide().acceptVisitor(this);

		maxDeBruijn = currentMaxDeBruijn;
		abstractedVariables = currentAbstractedVars;
		inSubTerm = inside;
		LambdaTerm right = app.getRightHandSide().acceptVisitor(this);
		LambdaTerm localTerm = new Application(left, right);

		if (app == subTerm) {
			resolvedSubTerm = localTerm;
		}
		return localTerm;
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		if (var.getDeBruijnIndex() > maxDeBruijn) {
			String varName = abstractedVariables.get(abstractedVariables.size() - var.getDeBruijnIndex());
			return new FreeVariable(varName);
		} else {
			return new BoundVariable(var.getDeBruijnIndex());
		}
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return new FreeVariable(var.getName());
	}

	@Override
	public LambdaTerm visitNamedTerm(NamedTerm term) {
		if (term == subTerm) {
			inSubTerm = true;
		}
		LambdaTerm localTerm = new NamedTerm(term.getName(), term.getInner().acceptVisitor(this));
		if (term == subTerm) {
			resolvedSubTerm = localTerm;
		}
		return localTerm;
	}

	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

}