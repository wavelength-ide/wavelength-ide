package edu.kit.wavelength.client.model.library;

import java.util.Arrays;
import java.util.List;

import edu.kit.wavelength.client.model.reduction.ApplicativeOrder;
import edu.kit.wavelength.client.model.reduction.CallByName;
import edu.kit.wavelength.client.model.reduction.CallByValue;
import edu.kit.wavelength.client.model.reduction.NormalOrder;

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
		return Arrays.asList(new NaturalNumbers(), new TuplesAndLists(), new YCombinator());
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
		if (serialized == null || serialized.isEmpty())
			throw new IllegalArgumentException("serialized must be non-empty");

		String stripped = serialized.substring(1);
		
		switch (serialized.charAt(0)) {
		case 'b':
			return new Boolean();

		case 'c':
			return CustomLibrary.fromSerialized(stripped);

		case 'n':
			return new NaturalNumbers();

		case 't':
			return new TuplesAndLists();
			
		case 'y':
			return new YCombinator();
		}

		throw new IllegalArgumentException("serialized must represent a reduction order");
	}

	private Libraries() {
	}
}
