package edu.kit.wavelength.client.view.api;

/**
 * A Lockable is a view that can be locked and unlocked.
 *
 * Being locked means that an object is still visible but cannot react to any
 * event. By unlocking an object it can react to events again.
 */
public interface Lockable {

	/**
	 * Lock this element, disabling actions on the element.
	 */
	void lock();

	/**
	 * Unlock this element, enabling actions on the element.
	 */
	void unlock();

	/**
	 * Checks whether the view is locked right now.
	 * 
	 * @return true if the view is locked and false otherwise.
	 */
	boolean isLocked();
}
