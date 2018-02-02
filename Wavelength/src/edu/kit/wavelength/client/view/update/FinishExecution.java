package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.execution.ControlObserver;

public class FinishExecution implements ControlObserver {

	private static App app = App.get();
	
	@Override
	public void finish() {
		Control.updateStepControls();
		
		app.exportButtons().forEach(b -> b.setEnabled(true));
		
		app.unpauseButton().setVisible(true);
		app.pauseButton().setVisible(false);
	}

}
