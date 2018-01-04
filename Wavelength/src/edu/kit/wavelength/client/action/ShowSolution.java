package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.exercise.Exercise;
import edu.kit.wavelength.client.view.Hideable;

/**
 * This action shows the sample solution of the current exercise.
 */
public class ShowSolution implements Action {

	private Hideable solutionView;
	private Exercise exercise;

	/**
	 * Constructs a new ShowSolution Action
	 * 
	 * @param exercise
	 *            The currently displayed exercise 
	 * @param solutionView
	 *            The UI element where the solution is displayed
	 */
	public ShowSolution(Exercise exercise, Hideable solutionView) {

	}

	@Override
	public void run() {

	}

}
