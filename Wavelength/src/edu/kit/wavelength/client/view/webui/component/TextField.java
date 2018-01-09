package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.api.Readable;


/**
 * A TextField is an adapter class for a GWT TextArea.
 * 
 * A TextField can only be read. It provides a means to display text to the User
 * and can be hidden and shown.
 */
public class TextField implements Hideable, Writable, Readable {

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

	@Override
	public boolean isShown() {
		return false;
	}

	@Override
	public String read() {
		return null;
	}

}
