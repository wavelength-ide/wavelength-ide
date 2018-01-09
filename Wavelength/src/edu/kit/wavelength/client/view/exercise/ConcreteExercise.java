package edu.kit.wavelength.client.view.exercise;

import java.util.List;

/**
 * This class is a concrete implementation of the {@link Exercise} interface.
 * The needed method's return values are set in the constructor.
 */
public class ConcreteExercise implements Exercise {

	private String name;
	private String task;
	private String solution;
	private String predefinitions;
	private List<String> testCases;
	
	public ConcreteExercise(final String name, final String task, final String solution, final String predefinitions, final List<String> testCases) {
		this.name = name;
		this.task = task;
		this.solution = solution;
		this.predefinitions = predefinitions;
		this.testCases = testCases;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getTask() {
		return this.task;
	}

	@Override
	public String getSolution() {
		return this.solution;
	}

	@Override
	public boolean hasPredefinitions() {
		return !predefinitions.isEmpty();
	}

	@Override
	public String getPredefinitions() {
		return this.predefinitions;
	}

}
