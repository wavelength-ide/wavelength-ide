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
	public static final char ID = 's';

	/**
	 * Creates a shortened output size policy with the given cutoff.
	 * 
	 * @param cutoff
	 *            How many terms are to be shown at the beginning and the end
	 */
	public Shortened(int cutoff) {
		if (cutoff < 1) {
			throw new IllegalArgumentException("Cutoff may not be negative or 0.");
		}
		this.cutoff = cutoff;
	}

	@Override
	public boolean displayLive(int step) {
		return step < cutoff;
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
		// Adds > lastDisplayed, > cutoff , > totalSteps . cutoff
		// This loop adds the indices of all available steps following the first
		// step to display.
		// This first step's index is defined as the maximum of three values:
		// The index of the step following the last displayed step, to avoid
		// displaying the same step twice,
		// even if step-by-step reduction was used
		// The first cutoff term's index, to avoid displaying a already
		// displayed term again,
		// and the index just larger than the number of totalSteps - the cutoff,
		// to ensure that no more than the value of cutoff-1 terms are displayed
		for (int i = Math.max(lastDisplayed + 1, Math.max(cutoff, totalSteps - cutoff + 1)); i <= totalSteps; i++) {
			displaySteps.add(i);
		}
		return displaySteps;
	}

	@Override
	public String getName() {
		return "Shortened";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(ID + String.valueOf(cutoff));
	}

	public static Shortened fromSerialized(String serialized) {
		return new Shortened(Integer.valueOf(serialized));
	}

	@Override
	public int numToPreserve() {
		return cutoff;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Shortened) {
			Shortened otherShortened = (Shortened) other;
			if (otherShortened.cutoff == cutoff) {
				return true;
			}
		}
		return false;
	}

}
