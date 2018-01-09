package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class LoadExercise implements Action {

	/**
	 * Constructs a new action for changing the UI from standard input view to
	 * exercise view.
	 * 
	 * @param exercise
	 *            The selected Exercise that should be displayed to the user.
	 */
	public LoadExercise(final Exercise exercise) {

	}

	/**
	 * Reduces the editors width and displays the task in the enabled task view
	 * window. Also shows buttons for exiting this exercise view and for displaying
	 * the sample solution.
	 */
	@Override
	public void run() {

	}

}
