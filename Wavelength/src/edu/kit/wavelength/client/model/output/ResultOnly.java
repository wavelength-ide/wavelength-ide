package edu.kit.wavelength.client.model.output;

import java.util.List;

/**
 * {@link OutputSize} that displays no terms live and
 * only displays the very last term in the end.
 *
 */
public final class ResultOnly implements OutputSize {

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
		return "Result only";
	}

	@Override
	public String serialize() {
		return null;
	}

	@Override
	public int numToPreserve() {
		// TODO Auto-generated method stub
		return 0;
	}

}
