package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.DialogBox;

import edu.kit.wavelength.client.action.Action;

/**
 * The PopUpTextBox is an adapter class for the GWT DialogeBox
 * 
 * It is represented by a PopUpWindow that holds a TextField and possibly some
 * buttons. It can be hidden or shown. Content can be written and read from the
 * TextField. In addition new Buttons can be added via the EntryInjectable
 * interface.
 */
public class PopUpTextBox implements Writable, Hideable, Readable, EntryInjectable {

	/**
	 * Creates a new and empty PopUpTextBox.
	 * 
	 * @param dialogBox
	 *            The GWT DialogBox that this class wraps.
	 * @param name
	 *            The title of the PupUpTextBox.
	 */
	public PopUpTextBox(DialogBox dialogBox, String name) {

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
	public String read() {
		return null;
	}

	@Override
	public void addEntry(String name, Action action) {

	}
}
