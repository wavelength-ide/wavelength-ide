package edu.kit.wavelength.client.view;

/**
 * Observer that receives updates with the most recent serialized URL.
 */
public interface SerializationObserver {
	/**
	 * Updates the observer.
	 * @param s - serialized URL
	 */
	void updateSerialized(String s);
}
