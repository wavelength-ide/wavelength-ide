package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to leave the AutoExecution (or
 * ExerciseAutoExecution) state. The application is then in the StepByStep (or
 * ExerciseStepByStep) state.
 */
public class Pause implements Action {

	private ExecutionEngine execution;
	private UIState state;

	/**
	 * Constructs a new Pause Action
	 * 
	 * @param engine
	 *            The engine that should be signaled, to stop its calculations.
	 * @param state
	 *            The state of the UI that has to change.
	 */
	public Pause(ExecutionEngine engine, UIState state) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
