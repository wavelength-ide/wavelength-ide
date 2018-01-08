package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Action;

/**
 * This action sets the reduction order the user selected.
 */
public class SelectReductionOrder implements Action {

	@Override
	public void run() {
		App.get().changeReductionOrder();
	}

}
