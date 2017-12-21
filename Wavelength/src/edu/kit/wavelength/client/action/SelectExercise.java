package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;

/**
 * This action causes the application to transition to the ExerciseInput state.
 */
public class SelectExercise implements Action {

	private UIState state;
	// TODO braucht noch eine Referenz auf die Aufgaben

	/**
	 * Constructs a new SelectExercise Action.
	 * 
	 * @param state
	 *            The state of the UI that has to change.
	 */
	public SelectExercise(UIState state) {

	}

	@Override
	public void run() {
		// state.enterExercise();

	}
}
