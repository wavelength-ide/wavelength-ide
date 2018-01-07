package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;

//TODO wer observed den neuen Term der ExecutionEngine?

/**
 * This action displays the next reduction step.
 */
public class StepForward implements Action {

	private ExecutionEngine execution;

	/**
	 * Constructs a new StepForward Action.
	 * 
	 * @param engine
	 *            The Engine that is signaled to calculate the next step.
	 */
	public StepForward(ExecutionEngine engine) {

	}

	@Override
	public void run() {
	}
}
