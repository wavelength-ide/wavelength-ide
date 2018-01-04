package edu.kit.wavelength.client.view;

/**
 * This Editor is the main means for the user to input content into the
 * application.
 * 
 * The User can input the Terms he wants to calculate. In Addition text can be
 * written into the Editor to communicate with the User. The Editor can be
 * blocked too to prevent the User from changing its content.
 */
public class Editor implements Readable, Writable, Blockable {

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
}
