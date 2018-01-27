package edu.kit.wavelength.client.model.output;

import java.util.List;

/**
 * {@link OutputSize} where every term is displayed live.
 *
 */
public final class Full implements OutputSize {

	@Override
	public boolean displayLive(int step) {
		return false;
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		return null;
	}

	@Override
	public String getName() {
		return "Full";
	}

	@Override
	public String serialize() {
		return null;
	}

}
