package edu.kit.wavelength.client.view.execution;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * Observer that receives reduced terms. Necessary because Executor is concurrent with UI.
 */
public interface ExecutionObserver {
	/**
	 * Pushes the most recent displayed term.
	 * @param t term
	 */
	void pushTerm(LambdaTerm t);
}
