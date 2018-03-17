package edu.kit.wavelength.client.model.library;

import java.util.ArrayList;
import java.util.List;

import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

/**
 * The CustomLibrary class represents a library which starts out empty, but to which any term and name can be added.
 * It is used by the Parser to store all name assignments defined by the user.
 */
public class CustomLibrary implements Library {

	private String name;
	private ArrayList<String> names;
	private ArrayList<LambdaTerm> terms;

	public static final char ID = 'c';

	public CustomLibrary(String name) {
		this.name = name;
		names = new ArrayList<String>();
		terms = new ArrayList<LambdaTerm>();
	}

	@Override
	public StringBuilder serialize() {
		StringBuilder res = new StringBuilder("" + ID);
		ArrayList<StringBuilder> comp = new ArrayList<>();
		comp.add(new StringBuilder(name));
		int size = names.size();
		for (int i = 0; i < size; ++i) {
			comp.add(new NamedTerm(names.get(i), terms.get(i)).serialize());
		}
		return res.append(SerializationUtilities.enclose(comp.toArray(new StringBuilder[0])));
	}

	/**
	 * Restores a {@link CustomLibrary} object from a serialization String.
	 * @param serialized The serialization from which to restore the library
	 * @return A CustomLibrary equal to the library processed to the input String
	 */
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
		int termIndex = names.lastIndexOf(name);
		if (termIndex != -1) {
			desiredTerm = terms.get(termIndex);
		}
		return desiredTerm;
	}

	@Override
	public boolean containsName(String name) {
		return names.contains(name);
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Adds a new lambda and its name to the library.
	 * 
	 * @param term
	 *            The lambda term to add to the library
	 * @param name
	 *            The name used to reference the term.
	 */
	public void addTerm(LambdaTerm term, String name) {
		if (name == null || name.isEmpty() || term == null) {
			throw new IllegalArgumentException();
		}
		names.add(name);
		terms.add(term);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		CustomLibrary naturalNumbers = (CustomLibrary) o;

		if (names.size() != naturalNumbers.names.size() || terms.size() != naturalNumbers.terms.size()) {
			return false;
		}
		for (int i = 0; i < names.size(); i++) {
			if (!names.get(i).equals(naturalNumbers.names.get(i))) {
				return false;
			}
			if (!terms.get(i).equals(naturalNumbers.terms.get(i))) {
				return false;
			}
		}
		return true;
	}
}
