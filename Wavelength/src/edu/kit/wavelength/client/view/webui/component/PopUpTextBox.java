package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.DialogBox;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * The PopUpTextBox is an adapter class for the GWT DialogeBox
 * 
 * It is represented by a PopUpWindow that holds a TextField and possibly some
 * buttons (e.g. a close Button). It can be hidden or shown. Content can be written and read from the
 * TextField.
 */
public class PopUpTextBox implements Writable, Hideable, Readable {

	/**
	 * Creates a new and empty PopUpTextBox.
	 * 
	 * @param dialogBox
	 *            the wrapped {@link DialogBox}
	 */
	public PopUpTextBox(final DialogBox dialogBox) {
		
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
