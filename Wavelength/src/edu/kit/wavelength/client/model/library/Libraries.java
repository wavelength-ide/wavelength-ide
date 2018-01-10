package edu.kit.wavelength.client.model.library;

import java.util.List;

/**
 * Static class giving access to all {@link Libraries} known to the model.
 *
 */
public final class Libraries {

	/**
	 * Returns an unmodifiable list of all {@link Libraries} known to the model.
	 * 
	 * @return An unmodifiable list that contains exactly one instance of every
	 *         {@link Library} known to the model
	 */
	public static List<Library> all() {
		return null;
	}

	/**
	 * Returns the {@link Library} referred to by the serialized string.
	 * 
	 * @param serialized
	 *            A string created by calling serialize on a {@link Library} known to the
	 *            Libraries class
	 * @return The {@link Library} referred to by the serialized string
	 */
	public static Library deserialize(String serialized) {
		return null;
	}

	private Libraries() {
	}
}
