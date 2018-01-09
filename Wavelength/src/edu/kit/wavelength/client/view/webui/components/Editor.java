package edu.kit.wavelength.client.view.webui.components;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Deactivatable;
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
public class Editor implements Readable, Writable, Deactivatable, Serializable {

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
	public void block() {

	}

	@Override
	public void unblock() {

	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
