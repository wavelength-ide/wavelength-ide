package edu.kit.wavelength.client.view;

/**
 * A Blockable is a View that can be blocked and unblocked.
 * 
 * Being blocked means that an object is still visible but cannot react to any event.
 * By unblocking an object it can react to events again.
 */
public interface Blockable {
	
	/**
	 * Blocks this element.
	 */
	void block();
	
	/**
	 * Unblocks this element.
	 */
	void unblock();
}
