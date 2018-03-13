package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class RedexExercise implements Exercise {

	private int minTermDepth = 3;
	private int maxTermDepth = 8;
	
	private ReductionOrder myReductionOrder;
	private String solution;
	private String predefinitions;
	
	private String firstRedex;
	
	public RedexExercise(ReductionOrder reduction) {
		this.myReductionOrder = reduction;
	}
	
	@Override
	public String getName() {
		return "β-Redex - " + myReductionOrder.getName() + "*";
	}

	@Override
	public String getTask() {	
		return "Does the displayed term contain a redex and if so which redex will be reduced first using " + myReductionOrder.getName()
		+ " reduction order?";
	}

	@Override
	public String getSolution() {
		return solution;
	}

	@Override
	public boolean hasPredefinitions() {
		return true;
	}
	
	@Override
	public String getPredefinitions() {
		reset();
		return predefinitions;
	}
	
	/**
	 * Resets the exercise by randomly creating a new term and updating predefinition and solution.
	 */
	public void reset() {
		BasicExportVisitor toString = new BasicExportVisitor(new ArrayList<Library>(), "λ");
		TermGenerator generator = new TermGenerator();
		LambdaTerm newTerm = generator.getNewTerm(minTermDepth, maxTermDepth);
		if (firstRedex == null) {
			while (myReductionOrder.next(newTerm) == null) {
				newTerm = generator.getNewTerm(minTermDepth, maxTermDepth);
			}
		}
		predefinitions = newTerm.acceptVisitor(toString).toString();
		LambdaTerm firstRedexTerm = myReductionOrder.next(newTerm);
		if (firstRedexTerm == null) {
			firstRedex = null;
			solution = "Using the " + myReductionOrder.getName()
			+  " reduction order, the displayed term does not contain a redex.";
		} else {
			firstRedex = firstRedexTerm.acceptVisitor(toString).toString();
			solution =  firstRedex + " is the first redex to be reduced using the " + myReductionOrder.getName()
			+ " reduction order.";
		}
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(getName());
	}
}