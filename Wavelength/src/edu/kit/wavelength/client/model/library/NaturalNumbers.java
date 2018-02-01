package edu.kit.wavelength.client.model.library;

import java.util.ArrayList;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A {@link Library} matching integer literals to church numerals and providing
 * functions for basic arithmetic operations.
 *
 * Note that the {@link ExecutionEngine} can try to accelerate the calculation
 * on a {@link LambdaTerm} that uses this library.
 */
public final class NaturalNumbers implements Library {

	private final String decimalRegex = "[1-9][0-9]*|0";
	private ArrayList<String> operationNames;
	private ArrayList<LambdaTerm> operationTerms;

	public NaturalNumbers() {
		operationNames = new ArrayList<String>();
		operationTerms = new ArrayList<LambdaTerm>();

	}

	@Override
	public LambdaTerm getTerm(String name) {
		if (name == null) {
			return null;
		}
		if (name.matches(decimalRegex)) {
			if (name == "0") {
				return new Abstraction("s", new Abstraction("z", new BoundVariable(1)));
			}
			int number = Integer.parseInt(name);
			LambdaTerm prev = new Application(new BoundVariable(2), new BoundVariable(1));
			while (number > 1) {
				prev = new Application(new BoundVariable(2), prev);
			}
			return new Abstraction("s", new Abstraction("z", new Application(new BoundVariable(1), prev)));
		} else {
			int index = operationNames.indexOf(name);
			if (index != -1) {
				return operationTerms.get(index);
			}
		}
		return null;
	}

	@Override
	public boolean containsName(String name) {
		if (name.matches(decimalRegex)) {
			return true;
		} else {
			if (operationNames.contains(name)) {
				return true;
			}
			return false;
		}
	}

	@Override
	public String getName() {
		return "Natural Numbers";
	}

	@Override
	public StringBuilder serialize() {
		return null;
	}

}
