package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This action will try to load a new exercise and alerts the user when the
 * content of the Editor would be overridden.
 */
public class SelectExercise implements Action {

	private Exercise exercise;

	/**
	 * TODO : besserer javadoc Constructs a new SelectExercise Action.
	 * 
	 * @param exercise
	 *            the selected Exercise
	 * @param controller
	 *            the AppController of the Application
	 */
	public SelectExercise(final Exercise exercise, final App controller) {

	}

	/**
	 * Opens a PopupWindow if the content of the editor would be overridden.
	 * Otherwise just loads the selected exercise.
	 */
	@Override
	public void run() {
	}
}
