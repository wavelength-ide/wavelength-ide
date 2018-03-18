package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.execution.ControlObserver;

/**
 * This class is called when the execution has finished. It adjusts the UI
 * elements according to the new state.
 */
public class FinishExecution implements ControlObserver {

	@Override
	public void finish() {
		Control.updateControls();
	}

}
