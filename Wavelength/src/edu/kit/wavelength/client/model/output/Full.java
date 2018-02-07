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

}
