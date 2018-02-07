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
	 * @param cutoff How many terms are to be shown at the beginning and the end
	 */
	public Shortened(int cutoff) {
		this.cutoff = cutoff;
	}

	@Override
	public boolean displayLive(int step) {
		return step < cutoff;
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
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

}
