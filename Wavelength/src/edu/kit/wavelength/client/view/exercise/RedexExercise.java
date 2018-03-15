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
	private TermGenerator termGenerator;
	private String solution;
	private String predefinitions;
	
	private String firstRedex;
	
	public RedexExercise(ReductionOrder reduction) {
		this.myReductionOrder = reduction;
		termGenerator = new TermGenerator();
		//reset();
	}
	
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
		return "Does the displayed term contain a redex and if so which redex will be reduced first using " + myReductionOrder.getName()
		+ " reduction order?";
	}

	@Override
	public String getSolution() {
		if (firstRedex == null) {
			return "Using the " + myReductionOrder.getName()
			+  " reduction order, the displayed term does not contain a redex.";
		} else {
			return "\"" + firstRedex + " \" is the first redex to be reduced using the " + myReductionOrder.getName()
			+ " reduction order.";
		}
	}

	@Override
	public boolean hasPredefinitions() {
		return true;
	}
	
	@Override
	public String getPredefinitions() {
		return predefinitions;
	}
	
	/**
	 * Resets the exercise by randomly creating a new term and updating predefinition and solution.
	 */
	public void reset() {
		BasicExportVisitor toString = new BasicExportVisitor(new ArrayList<Library>(), "λ");
		LambdaTerm newTerm = termGenerator.getNewTerm(minTermDepth, maxTermDepth);
		predefinitions = newTerm.acceptVisitor(toString).toString();
		LambdaTerm firstRedexTerm = myReductionOrder.next(newTerm);
		if (firstRedexTerm == null) {
			firstRedex = null;
		} else {
			firstRedex = firstRedexTerm.acceptVisitor(toString).toString();
		}
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(getName());
	}
}