package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Readable;

/**
 * This action causes the application to transition from Input (or
 * ExerciseInput) state to AutoExecution (or ExerciseAutoExecution) state. It
 * can only be triggered, if the current state is Input (or ExerciseInput).
 */
public class RunNewExecution implements Action {

	private ExecutionEngine engine;
	private UIState state;
	private Readable input;

	/**
	 * Constructs a new RunNewExecution Action.
	 * 
	 * @param engine
	 *            The engine that has to start its calculations.
	 * @param state
	 *            The state of the UI that has to change.
	 * @param input
	 *            The View that holds the Term that should be calculated by the
	 *            Engine.
	 */
	public RunNewExecution(ExecutionEngine engine, UIState state, Readable input) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
