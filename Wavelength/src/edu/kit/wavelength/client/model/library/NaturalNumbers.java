package edu.kit.wavelength.client.model.library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A {@link Library} matching integer literals to church numerals
 * and providing functions for basic arithmetic operations.
 *
 * Note that the {@link ExecutionEngine} can try to
 * accelerate the calculation on a {@link LambdaTerm}
 * that uses this library.
 */
public final class NaturalNumbers implements Library {

	private final Pattern decimalPattern = Pattern.compile("[1-9][0-9]*|0");
	
	@Override
	public LambdaTerm getTerm(String name) {
		if (name == "0") {
			return new Abstraction("s", new Abstraction("z", new BoundVariable(1)));
		}
		LambdaTerm prev = new Application(new BoundVariable(2), new BoundVariable(1));
		
	}

	@Override
	public boolean containsName(String name) {
		Matcher numberMatcher = decimalPattern.matcher(name);
		if (numberMatcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return "Natural Numbers";
	}

	@Override
	public String serialize() {
		return null;
	}

}
