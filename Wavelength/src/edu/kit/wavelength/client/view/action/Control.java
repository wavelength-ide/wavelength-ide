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
		
		app.backwardsButton().setEnabled(isRunning || isPaused);
		app.forwardButton().setEnabled(isTerminated || isPaused && canStepForward || isRunning && canStepForward);
		
		app.spinner().setStyleName("invisible", !isRunning);
		
		app.terminateButton().setEnabled(!isTerminated);
		app.pauseButton().setVisible(!isPaused);
		app.pauseButton().setEnabled(isRunning);
		app.unpauseButton().setVisible(isPaused);
		app.unpauseButton().setEnabled(canStepForward);
		
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
