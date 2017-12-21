package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Button;

import edu.kit.wavelength.client.action.Action;

/**
 * A LabeldView is an adapter class that wraps a GWT Button.
 * 
 * It is labeled by text and can be blocked and unblocked to prevent the User
 * from interacting with it. In Addition its behavior can be changed.
 * This means that the name of the label and the action that is performed when
 * clicking this button can be changed.
 */
public class LabeledButton implements Blockable, Hideable, ExchangableBehaviour, Writable {

	/**
	 * Creates a labeled View.
	 * 
	 * @param text
	 *            labeled text
	 * @param action
	 *            the action that this View can invoke
	 */
	public LabeledButton(final String text, final Action action, final Button button) {

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
	public void exchangeBehavoiur(Action action) {
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
}
