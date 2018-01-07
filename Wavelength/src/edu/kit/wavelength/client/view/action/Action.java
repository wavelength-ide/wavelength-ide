package edu.kit.wavelength.client.view.action;

/**
 * The Action interface encapsulates all events that can occur from the user
 * interacting with the UI.
 * 
 * An Action class is the event handler for one UI event. It is triggered by
 * interacting with the dedicated UI element.
 */
public interface Action {
	
	/**
	 * Called when the Action is triggered.
	 */
	void run();
}
