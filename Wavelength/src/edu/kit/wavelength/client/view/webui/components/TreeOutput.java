package edu.kit.wavelength.client.view.webui.components;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Output;


public class TreeOutput implements Deactivatable, Output {

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
