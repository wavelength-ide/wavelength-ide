package edu.kit.wavelength.client.model;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * Interface implemented by observers of the ExecutionEngine class.
 *
 */
public interface Observer {
	/**
	 * Called when the ExecutionEngine starts running.
	 */
	void executionStarted();
	
	/**
	 * Called when the ExecutionEngine stops running.
	 */
	void executionStopped();
	
	/**
	 * Called when a new term should be displayed.
	 * @param ID The numeric ID of the term that should be displayed.
	 */
	void termToDisplay(LambdaTerm term);
	
	/**
	 * Called when the last term that has been displayed should be removed.
	 */
	void popTerm();
}
