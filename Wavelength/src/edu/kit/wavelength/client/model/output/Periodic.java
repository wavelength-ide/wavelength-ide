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
	 * @param period The period of terms to be displayed
	 */
	public Periodic(int period) {
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
		ArrayList<Integer> displaySteps = new ArrayList<Integer>();
		if ((totalSteps % period) != 0) {
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

}
