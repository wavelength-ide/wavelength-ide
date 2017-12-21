package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;

/**
 * This action shows the sample solution of the current exercise.
 */
public class ShowSolution implements Action {

	private Hideable solution;
	// TODO wird die Engine hier wirklich gebraucht?
	private ExecutionEngine engine;

	/**
	 * Constructs a new ShowSolution Action
	 * 
	 * @param engine
	 *            TODO
	 * @param solution
	 *            The solution that is displayed to the User
	 */
	public ShowSolution(ExecutionEngine engine, Hideable solution) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
