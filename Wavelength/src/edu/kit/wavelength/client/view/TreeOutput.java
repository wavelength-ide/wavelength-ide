package edu.kit.wavelength.client.view;

import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * TODO: welches Interface geben wir vor?
 */
public class TreeOutput implements Blockable, Output {

	@Override
	public void block() {		
	}

	@Override
	public void unblock() {		
	}

	@Override
	public void addTerm(LambdaTerm term) {	
		// hier findet Übersetzung von LT nach Output statt 
	}

	@Override
	public void popTerm() {		
	}
}
