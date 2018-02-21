package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

public class Terminate implements Action {

	private static App app = App.get();
	
	@Override
	public void run() {
		app.executor().terminate();
		app.outputArea().clear();
		
		Control.updateControls();
	}

}
