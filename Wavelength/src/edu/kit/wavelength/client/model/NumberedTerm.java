package edu.kit.wavelength.client.model;

import edu.kit.wavelength.client.model.term.LambdaTerm;

class NumberedTerm {
	private LambdaTerm term;
	private int number;

	public NumberedTerm(LambdaTerm term, int number) {
		this.term = term;
		this.number = number;
	}

	public LambdaTerm getTerm() {
		return term;
	}

	public int getNumber() {
		return number;
	}
}
