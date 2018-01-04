package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public interface Output {
	
	void addTerm(LambdaTerm term);
	void popTerm();

}
