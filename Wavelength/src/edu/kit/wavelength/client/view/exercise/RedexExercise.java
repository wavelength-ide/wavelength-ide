package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

/**
 * Exercise which generates Lambda Terms randomly and asks the user to search
 * for reducible expressions.
 */
public class RedexExercise implements Exercise {

	private int minTermDepth = 3;
	private int maxTermDepth = 8;

	private ReductionOrder myReductionOrder;
	private TermGenerator termGenerator;
	private String predefinitions;

	private String firstRedex;

	/**
	 * Creates a new random redex exercise that uses the given
	 * {@link ReductionOrder}.
	 * 
	 * @param reduction
	 *            The {@link ReductionOrder} the user should use
	 */
	public RedexExercise(ReductionOrder reduction) {
		this.myReductionOrder = reduction;
		termGenerator = new TermGenerator();
	}

	/**
	 * Creates a new random redex exercise that uses the given
	 * {@link ReductionOrder} and the given {@link TermGenerator}.
	 * 
	 * @param reduction
	 *            The {@link ReductionOrder} the user should use
	 * @param generator
	 *            The {@link TermGenerator} used for term generation.
	 */
	public RedexExercise(ReductionOrder reduction, TermGenerator generator) {
		myReductionOrder = reduction;
		termGenerator = generator;
		reset();
	}

	@Override
	public String getName() {
		return "β-Redex - " + myReductionOrder.getName() + "*";
	}

	@Override
	public String getTask() {
		return "Does the displayed term contain a redex and if so which redex will be reduced first using "
				+ myReductionOrder.getName() + "?";
	}

	@Override
	public String getSolution() {
		if (firstRedex == null) {
			return "Using " + myReductionOrder.getName() + ", the displayed term does not contain a redex.";
		} else {
			return "\"" + firstRedex + " \" is the first redex to be reduced using " + myReductionOrder.getName() + ".";
		}
	}

	@Override
	public boolean hasPredefinitions() {
		if (this.predefinitions == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getPredefinitions() {
		return predefinitions;
	}

	/**
	 * Resets the exercise by randomly creating a new term and updating
	 * predefinition and solution.
	 */
	public void reset() {
		BoundVariableResolver resolver = new BoundVariableResolver();
		BasicExportVisitor stringMaker = new BasicExportVisitor(new ArrayList<Library>(), "λ");
		LambdaTerm newTerm = termGenerator.getNewTerm(minTermDepth, maxTermDepth);
		LambdaTerm redex = myReductionOrder.next(newTerm);
		while (firstRedex == null && redex == null) {
			newTerm = termGenerator.getNewTerm(minTermDepth, maxTermDepth);
			redex = myReductionOrder.next(newTerm);
		}
		predefinitions = newTerm.acceptVisitor(stringMaker).toString();
		if (redex == null) {
			firstRedex = null;
		} else {
			LambdaTerm resolvedRedex = resolver.resolveVariables(newTerm, redex);
			firstRedex = resolvedRedex.acceptVisitor(stringMaker).toString();
		}
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(getName());
	}
}