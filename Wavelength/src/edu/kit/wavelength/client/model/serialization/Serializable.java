package edu.kit.wavelength.client.model.serialization;

/**
 * Implemented by objects that may be serialized into a string.
 *
 */
public interface Serializable {
	/**
	 * Creates a string designating the object's state.
	 * 
	 * @return A string designating the object's state from which it can be restored
	 *         using a corresponding deserialization method
	 */
	StringBuilder serialize();
}
