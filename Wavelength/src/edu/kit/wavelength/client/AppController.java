package edu.kit.wavelength.client;

import edu.kit.wavelength.client.state.AppState;

/**
 * This class handles the current state of the application. The initial state is
 * the Input state.
 */
public class AppController implements UIState {

	private AppState state;

	public AppController() {
		// state = new Input();
	}

	public void start() {
		// state.start();
	}

	public void stop() {
		// state.stop();
	}

	public void pause() {
		// state.pause();
	}

	public void enterExercise() {
		// state.enterExercise();
	}

	public void exitExercise() {
		// state.exitExercise();
	}

	public void enterExport() {
		// state.enterExercise();
	}

	public void exitExport() {
		// state.exitExercise();
	}
}
