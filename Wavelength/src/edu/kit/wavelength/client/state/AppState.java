package edu.kit.wavelength.client.state;

/**
 * @author jpvdh
 *
 */
public abstract class AppState {

	public AppState nextState() {
		return null;
	}

	public void start() {

	}

	public void stop() {

	}

	public void pause() {

	}

	public void enterExercise() {

	}

	public void exitExercise() {

	}

	protected void enter() {

	}

	protected void exit() {

	}
}
