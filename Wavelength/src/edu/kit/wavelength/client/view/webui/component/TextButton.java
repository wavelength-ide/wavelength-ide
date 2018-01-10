package edu.kit.wavelength.client.view.webui.component;

import java.io.IOException;
import java.nio.CharBuffer;

import com.google.gwt.user.client.ui.Button;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * A LabeldButton is an adapter class that wraps a GWT {@link Button}.
 *
 * It is labeled by text and can be locked and unlocked to prevent the User
 * from interacting with it. In Addition its behavior can be changed. This means
 * that the name of the label and the action that is performed when clicking
 * this button can be changed.
 */
public class TextButton implements Lockable, Hideable, Writable, Readable, Clickable {

	/**
	 * Creates a new LabeledButton.
	 *
	 * @param button
	 *            the wrapped {@link Button}
	 * @param text
	 *            The label and name of this Button
	 * 
	 */
	public TextButton(final Button button, final String text) {

	}

	@Override
	public void lock() {

	}

	@Override
	public void unlock() {

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
	public void setAction(Action action) {

	}



	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void write(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String read() {
		return null;
	}

}
