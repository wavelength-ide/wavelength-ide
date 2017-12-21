package edu.kit.wavelength.client.model;

import edu.kit.wavelength.client.model.terms.LambdaTerm;

/**
 * Interface implemented by observers of the ExecutionEngine class.
 * 
 * @author markus
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
}