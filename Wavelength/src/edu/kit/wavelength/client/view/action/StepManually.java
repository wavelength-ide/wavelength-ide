package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.view.App;

/**
 * Action that initiates a manual step on a particular redex in an output view.
 */
public class StepManually implements Action {

	private static App app = App.get();
	
	private Application redex;

	/**
	 * Creates the action with the redex to apply when the user clicks on a
	 * redex.
	 * 
	 * @param redex
	 *            The redex to apply
	 * 
	 */
	public StepManually(Application redex) {
		this.redex = redex;
	}

	/**
	 * Delegates the step to the Executor.
	 */
	@Override
	public void run() {
		app.executor().stepForward(redex);
		
		Control.updateControls();
	}

}
