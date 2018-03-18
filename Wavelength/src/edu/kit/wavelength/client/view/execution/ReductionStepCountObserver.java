package edu.kit.wavelength.client.view.execution;

/**
 * Receives the current amount of reduction steps.
 */
public interface ReductionStepCountObserver {
	/**
	 * Receives the current amount of reduction steps.
	 * @param count - new count
	 */
	void update(int count);
}
