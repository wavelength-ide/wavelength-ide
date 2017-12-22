package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.exercise.Exercise;

/**
 * This action causes the application to transition to the ExerciseInput state.
 */
public class SelectExercise implements Action {

	private UIState state;
	private Exercise exercise;

	/**
	 * Constructs a new SelectExercise Action.
	 * 
	 * @param state
	 *            The state of the UI that has to change.
	 * @param exercise
	 *            the selected Exercise
	 */
	public SelectExercise(final UIState state, final Exercise exercise) {

	}

	@Override
	public void run() {
		// state.enterExercise();

	}
}
