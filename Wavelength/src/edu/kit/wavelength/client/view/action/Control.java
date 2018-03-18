package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.Executor;

/**
 * Utility class for updating the UI elements according to the current UI state
 * and resetting the UI to default state.
 */
public final class Control {

	private static App app = App.get();

	/**
	 * Updates the UI elements according to the current UI state.
	 */
	public static void updateControls() {
		Executor exec = app.executor();
		boolean isRunning = exec.isRunning();
		boolean isPaused = exec.isPaused();
		boolean isTerminated = exec.isTerminated();
		boolean canStepForward = exec.canStepForward();

		app.backwardButton().setEnabled(isRunning || isPaused);
		app.forwardButton().setEnabled(isTerminated || canStepForward || isRunning);

		app.spinner().setStyleName("invisible", !isRunning);

		app.clearButton().setEnabled(!isTerminated);
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

	/**
	 * Clears the editor and output area. Terminates the running execution and
	 * updates the UI to default state.
	 */
	public static void wipe() {
		app.editor().write("");
		app.outputArea().clear();
		if (!app.executor().isTerminated()) {
			app.executor().terminate();
			updateControls();
		}
	}

}
