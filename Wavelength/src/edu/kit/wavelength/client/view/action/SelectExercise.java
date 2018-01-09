package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This action will try to load a new exercise and alerts the user when the
 * content of the Editor would be overwritten.
 */
public class SelectExercise implements Action {

	private Exercise exercise;

	/**
	 * Constructs a new action handler for the selection of an exercise.
	 * 
	 * @param exercise
	 *            The selected exercise.
	 */
	public SelectExercise(final Exercise exercise) {

	}

	/**
	 * Opens a PopupWindow if the content of the editor would be overwritten.
	 * Otherwise just loads the selected exercise.
	 */
	@Override
	public void run() {
	}
}
