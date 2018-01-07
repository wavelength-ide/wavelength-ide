package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action binds the library the user selected.
 */
public class SelectLibrary implements Action {

	// possibly some other model class that handles libraries
	private ExecutionEngine execution;

	/**
	 * Constructs a new SelectLibrary Action
	 * 
	 * @param execution
	 *            The engine that is signaled to set the used library
	 */
	public SelectLibrary(ExecutionEngine execution) {

	}

	@Override
	public void run() {

	}
}
