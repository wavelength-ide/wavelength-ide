package edu.kit.wavelength.client.view;

/**
 * Blockables can be blocked by others.
 * Being blocked means that an object is still visible but cannot react to any event.
 * By unblocking an object it can again react to events.
 * 
 *
 */
public interface Blockable {
	
	/**
	 * Block this.
	 */
	void block();
	
	/**
	 * Unblock this.
	 */
	void unblock();
}
