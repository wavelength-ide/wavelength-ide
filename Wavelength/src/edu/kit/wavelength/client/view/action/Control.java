package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.Executor;

public final class Control {
	
	private static App app = App.get();
	
	public static void updateControls() {
		Executor exec = app.executor();
		boolean isRunning = exec.isRunning();
		boolean isPaused = exec.isPaused();
		boolean isTerminated = exec.isTerminated();
		boolean canStepForward = exec.canStepForward();
		boolean canStepBackward = exec.canStepBackward();
		
		app.backwardsButton().setEnabled(canStepBackward);
		app.forwardButton().setEnabled(isTerminated || canStepForward);
		
		app.terminateButton().setEnabled(!isTerminated);
		app.pauseButton().setVisible(isTerminated || isRunning || !canStepForward);
		app.pauseButton().setEnabled(isRunning);
		app.unpauseButton().setVisible(canStepForward);
		
		app.outputBlocker().setStyleName("notclickable", !isPaused);
		
		app.exportButtons().forEach(e -> e.setEnabled(!isTerminated));
		
		if (isRunning) {
			app.sharePanel().setVisible(false);
		}
	}
	
	public static void wipe() {
		app.editor().write("");
		app.outputArea().clear();
		if (!app.executor().isTerminated()) {
			app.executor().terminate();
			updateControls();
		}
	}
	
}
