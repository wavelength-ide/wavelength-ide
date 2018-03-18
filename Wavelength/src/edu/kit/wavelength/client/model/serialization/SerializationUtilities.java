package edu.kit.wavelength.client.model.serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Static class that provides tools for serializing and deserializing aggregate
 * data.
 *
 */
public class SerializationUtilities {
	private SerializationUtilities() {
	}

	/**
	 * Deserializes a string that was produced by the enclose method into its
	 * components.
	 * 
	 * @param input
	 *            The serialized string
	 * @return The components that were serialized
	 */
	public static List<String> extract(String input) {
		ArrayList<String> result = new ArrayList<>();

		int currentIndex = 0;

		while (currentIndex < input.length()) {
			// Count gives how many digits the number that gives the length of the
			// serialized string has
			int count;
			try {
				count = Integer.valueOf(input.substring(currentIndex, currentIndex + 1));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Input does not have the correct format");
			}

			++currentIndex;

			// There is no space for the number
			if (currentIndex + count > input.length())
				throw new IllegalArgumentException("Input does not have the correct format");

			// ActualLength gives the length of the serialized string
			int actualLength;

			try {
				actualLength = Integer.valueOf(input.substring(currentIndex, currentIndex + count));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Input does not have the correct format");
			}

			currentIndex += count;

			// There is no space for the content
			if (currentIndex + actualLength > input.length())
				throw new IllegalArgumentException("Input does not have the correct format");

			result.add(input.substring(currentIndex, currentIndex + actualLength));

			currentIndex += actualLength;
		}

		return result;
	}

	/**
	 * Creates a compound serialized string from a set of serialized strings that
	 * can be deserialized with extract.
	 * 
	 * @param content
	 *            The components
	 * @return A string that is a serializations of all component strings
	 */
	public static StringBuilder enclose(StringBuilder... content) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < content.length; ++i) {
			String lens = String.valueOf(content[i].length());
			
			// Each component consists of three parts: The length of the length,
			// the length, and the component itself.
			result.append(String.valueOf(lens.length()));
			result.append(lens);
			result.append(content[i]);
		}
		return result;
	}

	/**
	 * Deserializes a list created by serializeList.
	 * 
	 * @param serialized
	 *            The serialization string created by serializeList
	 * @param deserialize
	 *            The deserialization method for a single list element
	 * @return A list of deserialized elements
	 */
	public static <T> List<T> deserializeList(String serialized, Function<String, T> deserialize) {
		return SerializationUtilities.extract(serialized).stream().map(deserialize).collect(Collectors.toList());
	}

	/**
	 * Serializes a list of elements of the same type.
	 * 
	 * @param list
	 *            The list of elements
	 * @param serialize
	 *            The serialize method for a single element
	 * @return A serialization string for the entire list
	 */
	public static <T> StringBuilder serializeList(List<T> list, Function<T, StringBuilder> serialize) {
		return SerializationUtilities.enclose(list.stream().map(serialize).toArray(StringBuilder[]::new));
	}
}
