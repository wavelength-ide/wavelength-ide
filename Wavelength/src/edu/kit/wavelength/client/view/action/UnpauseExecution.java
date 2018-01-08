package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.AppController;

/**
 * This Action causes the application to transition from {@link StepByStep} or
 * ({@link ExerciseStepByStep}) state to {@link AutoExecution} (or
 * {@link ExerciseAutoExecution}) state. It is only triggered, if the current
 * state is {@link StepByStep} (or {@link ExerciseStepByStep}) while pressing
 * the button.
 */
public class UnpauseExecution implements Action {

	private AppController controller;

	/**
	 * Constructs a new RunPausedExecution Action
	 * 
	 * @param engine
	 *            The Engine that should continue its execution. It should use its
	 *            last calculated Term.
	 * @param state
	 *            The State of the UI that has to change.
	 */
	public UnpauseExecution(AppController controller) {

	}

	@Override
	public void run() {
		controller.unpause();
	}
}
