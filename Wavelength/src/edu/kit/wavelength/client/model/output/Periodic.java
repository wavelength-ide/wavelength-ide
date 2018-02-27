package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutputSize} where every n-th term is displayed, for some n.
 *
 */
public final class Periodic implements OutputSize {

	private final int period;
	public static final char ID = 'p';

	/**
	 * Creates a periodic output size with the given period.
	 * 
	 * @param period
	 *            The period of terms to be displayed
	 */
	public Periodic(int period) {
		if (period < 0) {
			throw new IllegalArgumentException("Period may not be negative.");
		}
		this.period = period;
	}

	@Override
	public boolean displayLive(int step) {
		if ((step % period) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Integer> displayAtEnd(int totalSteps, int lastDisplayed) {
		if (totalSteps < lastDisplayed || totalSteps < 0) {
			throw new IllegalArgumentException();
		}
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
		// The final reduced term is supposed to be displayed no matter the
		// active output size.
		// The following statement determines whether this step has already been
		// displayed
		// either by step-by-step reduction or live display.
		if ((totalSteps % period) != 0 && lastDisplayed != totalSteps) {
			displaySteps.add(totalSteps);
		}
		return displaySteps;
	}

	@Override
	public String getName() {
		return "Periodically";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder(ID + String.valueOf(period));
	}

	public static Periodic fromSerialized(String serialized) {
		return new Periodic(Integer.valueOf(serialized));
	}

	@Override
	public int numToPreserve() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Periodic) {
			Periodic oPeriodic = (Periodic) other;
			if (oPeriodic.period == period) {
				return true;
			}
		}
		return false;
	}

}
