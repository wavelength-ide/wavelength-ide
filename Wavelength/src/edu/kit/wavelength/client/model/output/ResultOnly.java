package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link OutputSize} that displays no terms live and
 * only displays the very last term in the end.
 *
 */
public final class ResultOnly implements OutputSize {
	
	public static final char ID = 'r';

	@Override
	public boolean displayLive(int step) {
		// Since ResultOnly is only supposed to display the last term,
		// this method will always return false so no intermediate step is displayed
		return false;
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		if (lastDisplayed == totalSteps)
			return Collections.emptyList();

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
		return new StringBuilder("" + ID);
	}

	@Override
	public int numToPreserve() {
		return 1;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof ResultOnly;
	}
}
