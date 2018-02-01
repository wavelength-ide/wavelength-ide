package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutputSize} where every term is displayed live.
 *
 */
public final class Full implements OutputSize {

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
	public String serialize() {
		return null;
	}

	@Override
	public int numToPreserve() {
		return 1;
	}

}
