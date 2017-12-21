package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.action.Action;

/**
 * A VisualButton is an adpater class that wraps a GWT Button.
 * 
 * It is represented by an {@link Image}. The representing image changes if the
 * Button is blocked or not. In Addition it can be hidden form the UI.
 */
public class VisualButton implements Blockable, Hideable {

	
	/**
	 * Creates a new VisualView.
	 * 
	 * @param imageWhenUnblocked
	 *            image that is shown when this is not blocked
	 * @param imageWhenBlocked
	 *            image that is shown when this is blocked
	 * @param action
	 *            the action that this View can invoke
	 */
	public VisualButton(final Image imageWhenUnblocked, final Image imageWhenBlocked, final Action action,
			Button button) {

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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
