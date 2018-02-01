package edu.kit.wavelength.client.model.library;

import java.util.ArrayList;

import edu.kit.wavelength.client.model.term.LambdaTerm;

public class CustomLibrary implements Library {

	private String name;
	private ArrayList<String> names;
	private ArrayList<LambdaTerm> terms; 
	
	public CustomLibrary(String name) {
		this.name = name;
		names = new ArrayList<String>();
		terms = new ArrayList<LambdaTerm>();
	}
	
	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LambdaTerm getTerm(String name) {
		LambdaTerm desiredTerm = null;
		int termIndex = names.indexOf(name);
		if (termIndex != -1) {
			desiredTerm = terms.get(termIndex);
		}
		return desiredTerm;
	}

	@Override
	public boolean containsName(String name) {
		if (names.contains(name))  {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a new lambda and its name to the library. 
	 * The term will only be added if  the library does not already contain a term with this name.
	 * @param term The lambda term to add to the library
	 * @param name The name used to reference the term.
	 */
	public void addTerm(LambdaTerm term, String name) {
		if (name.length() < 1) {
			throw new IllegalArgumentException();
		}
		if (containsName(name) == false) {
			names.add(name);
			terms.add(term);
		}
	}
	

}
