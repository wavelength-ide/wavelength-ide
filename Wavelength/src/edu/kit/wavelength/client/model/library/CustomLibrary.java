package edu.kit.wavelength.client.model.library;

import java.util.ArrayList;
import java.util.List;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

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
	public StringBuilder serialize() {
		StringBuilder res = new StringBuilder("c");
		ArrayList<StringBuilder> comp = new ArrayList<>();
		comp.add(new StringBuilder(name));
		int size = names.size();
		for (int i = 0; i < size; ++i) {
			comp.add(new NamedTerm(names.get(i), terms.get(i)).serialize());
		}
		return res.append(SerializationUtilities.enclose(comp.toArray(new StringBuilder[0])));
	}
	
	public static CustomLibrary fromSerialized(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() > 0;
		CustomLibrary res = new CustomLibrary(extracted.get(0));
		for (int i = 1; i < extracted.size(); ++i) {
			assert !extracted.get(i).isEmpty() && extracted.get(i).charAt(0) == 'n';
			NamedTerm parsed = NamedTerm.fromSerialized(extracted.get(i).substring(1));
			res.addTerm(parsed.getInner(), parsed.getName());
		}
		return res;
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
