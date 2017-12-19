package edu.kit.wavelength.client.view;

/**
 * A Blockable can be blocked and unblocked.
 * 
 * Being blocked means that an object is still visible but cannot react to any event.
 * By unblocking an object it can react to events again.
 * 
 * TODO: sollte Blockable von einem 'View' Interface erben?
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
