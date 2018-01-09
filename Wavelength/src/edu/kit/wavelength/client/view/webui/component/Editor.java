package edu.kit.wavelength.client.view.webui.component;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * This Editor is the main means for the user to input content into the
 * application.
 * 
 * The User can input the Terms he wants to calculate. In Addition text can be
 * written into the Editor to communicate with the User. The Editor can be
 * blocked too to prevent the User from changing its content.
 */
public class Editor implements Readable, Writable, Hideable, Lockable, Serializable {

	/**
	 * Creates a new Editor from the specified serialized string.
	 * @param serialized serialized string, as created in serialize
	 * @return new Editor
	 */
	public static Editor deserialize(String serialized) {
		return null;
	}
	
	@Override
	public String read() {
		return null;
	}

	@Override
	public void write(String input) {

	}

	@Override
	public void lock() {

	}

	@Override
	public void unlock() {

	}

	@Override
	public String serialize() {
		return null;
	}
	@Override
	public boolean isLocked() {
		return false;
	}
	@Override
	public void hide() {
		
	}
	@Override
	public void show() {
		
	}
	@Override
	public boolean isShown() {
		return false;
	}
}
