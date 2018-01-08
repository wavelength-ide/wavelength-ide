package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This action causes the application to transition to the {@link ExerciseInput} state.
 */
public class SelectExercise implements Action {

	private Exercise exercise;

	/**
	 * Constructs a new SelectExercise Action.
	 * 
	 * @param state
	 *            The state of the UI that has to change.
	 * @param exercise
	 *            the selected Exercise
	 */
	public SelectExercise(final Exercise exercise) {

	}

	@Override
	public void run() {
	}
}
