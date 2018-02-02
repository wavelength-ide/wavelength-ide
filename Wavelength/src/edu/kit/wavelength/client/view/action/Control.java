package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

public final class Control {
	
	private static App app = App.get();
	
	public static void updateStepControls() {
		boolean isPaused = app.executor().isPaused();
		
		boolean canStepForward = isPaused && app.executor().canStepForward();
		app.forwardButton().setEnabled(canStepForward);
		app.unpauseButton().setEnabled(canStepForward);
		app.reductionOrderBox().setEnabled(canStepForward);
		
		boolean canStepBackward = isPaused && app.executor().canStepBackward();
		app.backwardsButton().setEnabled(canStepBackward);
	}
	
}
