package edu.kit.wavelength.client.view;

/**
 * Observer that receives updates with the most recent serialized URL.
 */
public interface SerializationObserver {
	/**
	 * Updates the observer.
	 * @param url The serialized URL
	 */
	void updateSerialized(String url);
}
