package edu.kit.wavelength.client.view.exercise;

/**
 * This class is a concrete implementation of the {@link Exercise} interface.
 * The needed method's return values are set in the constructor.
 */
public class ConcreteExercise implements Exercise {

	private String name;
	private String task;
	private String solution;
	private String predefinitions;

	/**
	 * Creates a new Exercise.
	 * 
	 * @param name
	 *            - name of the exercise
	 * @param task
	 *            - problem task to display
	 * @param solution
	 *            - intended solution for the problem
	 * @param predefinitions
	 *            - initial code to load into the editor
	 */
	public ConcreteExercise(final String name, final String task, final String solution, final String predefinitions) {
		if (name == null || task == null || solution == null || predefinitions == null) {
			throw new IllegalArgumentException("Invalid parameters!");
		}
		this.name = name;
		this.task = String.join("<br/>", task.split("\n"));
		this.solution = solution;
		this.predefinitions = predefinitions;
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

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(this.name);
	}

}
