package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action causes the application to transition back in the {@link Input} (or
 * {@link ExerciseInput}) state.
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
