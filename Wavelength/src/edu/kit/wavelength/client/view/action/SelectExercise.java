package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.exercise.Exercise;

/**
 * This class changes the view from standard input to exercise view to display
 * the selected exercise.
 */
public class SelectExercise implements Action {

	private Exercise exercise;

	/**
	 * TODO : besserer javadoc 
	 * Constructs a new SelectExercise Action.
	 * 
	 * @param exercise
	 *            the selected Exercise
	 */
	public SelectExercise(final Exercise exercise) {

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
