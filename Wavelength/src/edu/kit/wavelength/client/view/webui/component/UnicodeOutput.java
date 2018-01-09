package edu.kit.wavelength.client.view.webui.component;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Output;


public class UnicodeOutput implements Deactivatable, Hideable, Output {

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

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isShown() {
		// TODO Auto-generated method stub
		return false;
	}
}
