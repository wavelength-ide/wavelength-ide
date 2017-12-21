package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to leave the AutoExecution (or
 * ExerciseAutoExecution)state when the user presses the pause button. The
 * application is then in the StepByStep (or ExerciseStepByStep) state.
 */
public class Pause implements Action {

	private ExecutionEngine execution;
	private UIState state;

	public Pause(ExecutionEngine engine, UIState state) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
