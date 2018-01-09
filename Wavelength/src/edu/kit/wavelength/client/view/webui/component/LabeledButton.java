package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.Button;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * A LabeldButton is an adapter class that wraps a GWT Button.
 *
 * It is labeled by text and can be blocked and unblocked to prevent the User
 * from interacting with it. In Addition its behavior can be changed. This means
 * that the name of the label and the action that is performed when clicking
 * this button can be changed.
 */
public class LabeledButton implements Deactivatable, Hideable, Writable, Clickable {

	/**
	 * Creates a new LabeledButton.
	 *
	 * @param button
	 *            the wrapped {@link Button}
	 * @param text
	 *            The label and name of this Button
	 * 
	 */
	public LabeledButton(final Button button, final String text) {

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

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
	public void setAction(Action action) {

	}

}
