package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutputSize} where every term is displayed live.
 *
 */
public final class Full implements OutputSize {
	
	public static final char ID = 'f';

	@Override
	public boolean displayLive(int step) {
		return true;
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		// Using full output size, every reduced term has already been displayed live,
		// so this method will always return an empty list.
		return new ArrayList<Integer>();
	}

	@Override
	public String getName() {
		return "Full";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}

	@Override
	public int numToPreserve() {
		return 1;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Full;
	}

}
