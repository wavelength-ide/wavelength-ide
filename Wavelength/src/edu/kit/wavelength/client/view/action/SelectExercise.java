package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This action will try to load a new exercise and alerts the user that the
 * content of the Editor would be overwritten.
 */
public class SelectExercise implements Action {

	private Exercise exercise;

	/**
	 * Constructs a new action handler for the selection of an exercise.
	 *
	 * @param exercise
	 *            the selected Exercise that should be loaded
	 */
	public SelectExercise(final Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * Opens a PopupWindow if the content of the editor would be overwritten.
	 * Allows the user to choose whether he wants to continue.
	 * Otherwise just loads the selected exercise.
	 */
	@Override
	public void run() {
		App.get().enterExerciseMode();
		// TODO: action for okay button -> how pass the selected exercise
	}
}
