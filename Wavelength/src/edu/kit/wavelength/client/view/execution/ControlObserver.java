package edu.kit.wavelength.client.view.execution;

/**
 * Observer that is notified when the Executor executes certain state transitions.
 */
public interface ControlObserver {
	/**
	 * Called when the Executor finishes its execution, i.e. the last term is reduced.
	 */
	void finish();
}
