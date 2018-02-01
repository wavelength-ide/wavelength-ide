package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.DialogBox;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * The PopUpTextBox is an adapter class for the GWT {@link DialogBox}
 * 
 * It is represented by a PopUpWindow that holds a TextField and possibly some
 * Buttons (e.g. a close Button). It can be hidden or shown. Content can be written and read from the
 * TextField.
 */
public class PopUpWindow implements Writable, Hideable, Readable {

	/**
	 * Creates a new and empty PopUpTextBox.
	 * 
	 * @param dialogBox
	 *            the wrapped {@link DialogBox}
	 */
	public PopUpWindow(final DialogBox dialogBox) {
		
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