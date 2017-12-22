package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This Action causes the application to transition from {@link StepByStep} or
 * ({@link ExerciseStepByStep}) state to {@link AutoExecution} (or
 * {@link ExerciseAutoExecution}) state. It is only triggered, if the current
 * state is {@link StepByStep} (or {@link ExerciseStepByStep}) while pressing
 * the button.
 */
public class RunPausedExecution implements Action {

	private ExecutionEngine engine;
	private UIState state;

	/**
	 * Constructs a new RunPausedExecution Action
	 * 
	 * @param engine
	 *            The Engine that should continue its execution. It should use its
	 *            last calculated Term.
	 * @param state
	 *            The State of the UI that has to change.
	 */
	public RunPausedExecution(ExecutionEngine engine, UIState state) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
