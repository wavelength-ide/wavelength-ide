package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.AppController;
import edu.kit.wavelength.client.view.action.Action;

/**
 * This action sets the reduction order the user selected.
 */
public class SelectReductionOrder implements Action {

	@Override
	public void run() {
		AppController.get().changeReductionOrder();
	}

}
