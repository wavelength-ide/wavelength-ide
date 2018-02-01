package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutputSize} that only shows a certain number of terms at the beginning
 * and at the end.
 *
 */
public final class Shortened implements OutputSize {

	private final int cutoff;
	
	/**
	 * Creates a shortened output size policy with the given cutoff.
	 * @param cutoff How many terms are to be shown at the beginning and the end
	 */
	public Shortened(int cutoff) {
		this.cutoff = cutoff;
	}

	@Override
	public boolean displayLive(int step) {
		if (step < cutoff) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
		for (int i = totalSteps - cutoff; i <= totalSteps; i++) {
			if (i >= cutoff) {
				displaySteps.add(i);
			}
		}	
		return displaySteps;
	}

	@Override
	public String getName() {
		return "Shortened";
	}

	@Override
	public StringBuilder serialize() {
		return null;
	}

	@Override
	public int numToPreserve() {
		return cutoff;
	}

}
