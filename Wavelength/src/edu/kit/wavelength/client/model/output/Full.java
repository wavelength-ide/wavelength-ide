package edu.kit.wavelength.client.model.output;

import java.util.List;

/**
 * Output size where every term is displayed live.
 *
 */
final class Full implements OutputSize {

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
		return null;
	}

}
