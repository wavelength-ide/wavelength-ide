package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to transition back in the Input (or
 * ExerciseInput) state.
 */
public class Stop implements Action {

	private UIState state;
	private ExecutionEngine engine;

	/**
	 * Constructs a new Stop Action.
	 * 
	 * @param state
	 *            The state of the Application that has to change.
	 * @param engine
	 *            The Engine that is signaled to stop its calculation.
	 */
	public Stop(UIState state, ExecutionEngine engine) {

	}

	@Override
	public void run() {
		// state.stop();
	}
}
