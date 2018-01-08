package edu.kit.wavelength.client.view.api;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public interface Output {
	
	void addTerm(LambdaTerm term);
	void popTerm();

}
