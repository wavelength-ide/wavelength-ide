package edu.kit.wavelength.client;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public interface UIState {
	void addTerm(LambdaTerm term);
	void popTerm();
	void clearOutput();
}
