package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This action causes the application to leave a state in which the user is
 * displayed an export format.
 * 
 * TODO: exporter generic
 */
public class CloseExport implements Action {

	@Override
	public void run() {
		App.get().exportWindow().hide();
		App.get().uiBlocker().hide();
	}
}
