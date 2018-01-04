package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.action.Action;
import edu.kit.wavelength.client.model.ExecutionEngine;

/**
 * This action sets the reduction order the user selected.
 */
public class SelectReductionOrder implements Action {

	private ExecutionEngine execution;

	/**
	 * Constructs a new SelectReductionOrder Action
	 * 
	 * @param execution
	 *            The engine that is signaled to set the reduction order
	 */
	public SelectReductionOrder(ExecutionEngine execution) {

	}

	@Override
	public void run() {
	
	}

}
