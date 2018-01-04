package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.output.OutputSize;

/**
 * This action sets the output size the user selected.
 */
public class SelectOutputSize implements Action {

	private ExecutionEngine execution;
	private OutputSize outputSize;

	/**
	 * Constructs a new SelectOutputSize Action
	 * 
	 * @param execution
	 *            The engine that is signaled to set the output size
	 */
	public SelectOutputSize(ExecutionEngine execution) {

	}

	@Override
	public void run() {

	}

}
