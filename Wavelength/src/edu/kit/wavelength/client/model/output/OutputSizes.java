package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Static class giving access to all {@link OutputSize}s known to the model.
 *
 */
public final class OutputSizes {

	/**
	 * Returns an unmodifiable list of all {@link OutputSize}s known to the model.
	 * @return An unmodifiable list containing all {@link OutputSize}s known to the
	 * model
	 */
	public static List<OutputSize> all() {
		ArrayList<OutputSize> allOutputs = new ArrayList<OutputSize>();
		allOutputs.add(new Full());
		allOutputs.add(new Periodic(50));
		allOutputs.add(new Shortened(10));
		allOutputs.add(new ResultOnly());
		return Collections.unmodifiableList(allOutputs);
	}
	
	/**
	 * Returns the {@link OutputSize} referred to by a given string.
	 * @param serialized The string to be deserialized
	 * @return The {@link OutputSize} that the given string represents, if known to the model
	 */
	public static OutputSize deserialize(String serialized) {
		if (serialized == null || serialized.isEmpty())
			throw new IllegalArgumentException("serialized must be non-empty");
		
		String stripped = serialized.substring(1);
		
		switch (serialized.charAt(0)) {
		case Full.ID:
			return new Full();
			
		case Periodic.ID:
			return Periodic.fromSerialized(stripped);
		
		case ResultOnly.ID:
			return new ResultOnly();
			
		case Shortened.ID:
			return Shortened.fromSerialized(stripped);
		}
		
		throw new IllegalArgumentException("serialized must represent an output size");
	}
	
	private OutputSizes() {
	}
}
