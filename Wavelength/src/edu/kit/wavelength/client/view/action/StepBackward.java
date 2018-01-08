package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 *  TODO : javadoc erneuern
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
		engine.stepBackward();
	}

}
