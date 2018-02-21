package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.execution.ControlObserver;

public class FinishExecution implements ControlObserver {
	
	@Override
	public void finish() {
		Control.updateControls();
	}

}
