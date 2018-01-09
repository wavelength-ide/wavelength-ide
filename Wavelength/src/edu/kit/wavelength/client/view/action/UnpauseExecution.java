package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This Action causes the application to transition from {@link StepByStep} or
 * ({@link ExerciseStepByStep}) state to {@link AutoExecution} (or
 * {@link ExerciseAutoExecution}) state. It is only triggered, if the current
 * state is {@link StepByStep} (or {@link ExerciseStepByStep}) while pressing
 * the button.
 */
public class UnpauseExecution implements Action {

	private App controller;

	/**
	 * Constructs a new RunPausedExecution Action
	 * 
	 * @param engine
	 *            The Engine that should continue its execution. It should use its
	 *            last calculated Term.
	 * @param state
	 *            The State of the UI that has to change.
	 */
	public UnpauseExecution(App controller) {

	}

	@Override
	public void run() {
	}
}
