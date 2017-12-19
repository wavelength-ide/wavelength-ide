package edu.kit.wavelength.client.state;

/**
 * Represents the state of the application by implementing a state machine.
 *
 * AppState is the superclass for all other states and has default
 * implementations for handling events. The main purpose of a state is to handle
 * the views correctly after big events.
 * 
 * A state is collecting all the different UI changes that have to be handled after a 
 * big event. In doing so these UI changes are assembled at one class and not scattered
 * over many classes. In addition, this state class ensures that only legal state transitions
 * occur.
 * 
 * TODO: how do we define big events?
 */
public abstract class AppState {

	//TODO: AppState braucht eine referenz auf den AppControler
	/**
	 * TODO: set the next state in the state class by calling setState() of the
	 * AppController or let the AppControler get its next State by calling
	 * nextState() form its state?
	 *
	 * Called after handling an event.
	 * Functions exit() and enter() of the old and new state are automatically called.
	 *
	 * @return the designated next state of the application after the event was processed
	 */
	public AppState nextState() {
		return null;
	}

	/**
	 * Called when a calculation should be started or continued
	 */
	public void start() {

	}

	/**
	 * Called when a calculation should be canceled
	 */
	public void stop() {

	}

	/**
	 * Called when a calculation should be paused and run in debugging mode
	 */
	public void pause() {

	}

	/**
	 * Called when the application should enter to exercise mode
	 */
	public void enterExercise() {

	}

	/**
	 * Called when the application should exit exercise mode
	 */
	public void exitExercise() {

	}
	
	/**
	 * Called when the calculation of the applications should be exported to text
	 */
	public void enterExport() {
		
	}
	
	/**
	 * Called when exported text of a calculation is no longer needed
	 */
	public void exitExport() {
		
	}

	/**
	 * Called automatically whenever the application enters this state
	 */
	protected abstract void enter();

	/**
	 * Called automatically whenever the applications exits this state
	 */
	protected abstract void exit();
}
