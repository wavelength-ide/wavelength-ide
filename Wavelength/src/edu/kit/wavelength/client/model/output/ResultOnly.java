package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
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
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
		displaySteps.add(totalSteps);
		return displaySteps;
	}

	@Override
	public String getName() {
		return "Result only";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("r");
	}

	@Override
	public int numToPreserve() {
		return 1;
	}

}
