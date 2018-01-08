package edu.kit.wavelength.client.model.output;

import java.util.List;

/**
 * Output size where every n-th term is displayed, for some n.
 *
 */
public final class Periodic implements OutputSize {

	/**
	 * Creates a periodic output size with the given period.
	 * @param period The period of terms to be displayed
	 */
	public Periodic(int period) {
		
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
