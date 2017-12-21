package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to transition from StepByStep or
 * (ExerciseStepByStep) state to AutoExecution (or ExerciseAutoExecution) state
 * when the user presses the run button. It is only triggered, if the current
 * state is StepByStep (or ExerciseStepByStep) while pressing the button.
 */
public class RunPausedExecution implements Action {

	private ExecutionEngine engine;
	private UIState state;

	public RunPausedExecution(ExecutionEngine engine, UIState state) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
