package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to transition from Input (ExerciseInput)
 * state or AutoExecution (ExerciseAutoExecution) state to StepByStep
 * (ExerciseStepByStep) state.
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
		// TODO Auto-generated method stub
	}
}
