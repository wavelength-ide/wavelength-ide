package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.TextArea;

/**
 * A TextField is an adapter class for a GWT TextArea.
 * 
 * A TextField can only be read. It provides a means to display text to the User
 * and can be hidden and shown.
 */
public class TextField implements Hideable, Writable {

	/**
	 * Constructs a new and empty TextField.
	 * 
	 * @param textField
	 *            The GWT TextArea that this class wraps.
	 */
	public TextField(TextArea textField) {

	}

	@Override
	public void write(String input) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

}
