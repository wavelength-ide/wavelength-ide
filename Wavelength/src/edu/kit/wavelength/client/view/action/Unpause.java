package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

public class Unpause implements Action {

	private App app = App.get();
	
	@Override
	public void run() {
		app.executor().unpause();
		Control.updateControls();
	}

}
