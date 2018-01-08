package edu.kit.wavelength.client.view.execution;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public interface ExecutionObserver {
	void pushTerm(LambdaTerm t);
}
