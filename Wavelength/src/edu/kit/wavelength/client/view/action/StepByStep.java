package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to transition from {@link Input} ({@link ExerciseInput})
 * state or {@link AutoExecution} ({@link ExerciseAutoExecution}) state to {@link StepByStep}
 * ({@link ExerciseStepByStep}) state.
 */
public class StepByStep implements Action {

	// TODO wird hier die execution Engine neu Konstruiert? Antwort: Vermutlich nicht!
	private ExecutionEngine execution;
	private UIState state;

	/**
	 * Constructs a new StepByStep Action.
	 * 
	 * @param state
	 *            The state of the UI that has to change.
	 */
	public StepByStep(UIState state) {

	}

	@Override
	public void run() {
	}
}
