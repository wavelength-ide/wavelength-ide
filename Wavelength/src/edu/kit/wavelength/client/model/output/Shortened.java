package edu.kit.wavelength.client.model.output;

import java.util.List;

/**
 * Output size that only shows a certain number of terms at the beginning
 * and at the end.
 *
 */
final class Shortened implements OutputSize {
	
	/**
	 * Creates a shortened output size policy with the given cutoff.
	 * @param cutoff How many terms are to be shown at the beginning and the end
	 */
	public Shortened(int cutoff) {
		
	}

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

	@Override
	public String serialize() {
		return null;
	}

}
