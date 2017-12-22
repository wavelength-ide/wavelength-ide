package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Button;

import edu.kit.wavelength.client.action.Action;

/**
 * A LabeldButton is an adapter class that wraps a GWT Button.
 *
 * It is labeled by text and can be blocked and unblocked to prevent the User
 * from interacting with it. In Addition its behavior can be changed. This means
 * that the name of the label and the action that is performed when clicking
 * this button can be changed.
 */
public class LabeledButton implements Blockable, Hideable, Writable, ActionInjectable {

	/**
	 * Creates a new LabeledButton.
	 *
	 * @param text
	 *            The label and name of this Button
	 * @param button
	 *            The GWT Button that this class wraps.
	 */
	public LabeledButton(final String text, final Button button) {

	}

	@Override
	public void block() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unblock() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(String input) {
		// TODO Auto-generated method stub

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
	public void setAction(Action action) {
		// TODO Auto-generated method stub

	}
}
