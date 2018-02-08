package edu.kit.wavelength.client.view;

/**
 * Observer that receives updates containing the most recent id of a
 * serialization.
 */
public interface SerializationObserver {
	/**
	 * Updates the observer.
	 * 
	 * @param id
	 *            identifier belonging to the most recent serialization
	 */
	void updateSerialized(String id);
}
