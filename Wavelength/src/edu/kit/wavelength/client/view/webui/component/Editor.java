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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isShown() {
		// TODO Auto-generated method stub
		return false;
	}
}
