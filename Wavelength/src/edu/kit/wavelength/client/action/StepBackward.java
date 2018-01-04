package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.model.ExecutionEngine;

//TODO wer observed den neuen Term der ExecutionEngine?

/**
 * This action removes the last reduction step from the output area.
 */
public class StepBackward implements Action {

	private ExecutionEngine engine;

	/**
	 * Constructs a new StepBackward Action.
	 * 
	 * @param engine
	 *            The Engine that is signaled to calculate the last step.
	 */
	public StepBackward(ExecutionEngine engine) {

	}

	@Override
	public void run() {

	}

}
