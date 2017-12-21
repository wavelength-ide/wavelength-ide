package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.action.Action;

/**
 * VisualViews implement the {@link Blockable} interface. Additionally they are represented by an {@link Image}
 * that depends on whether this is blocked or not.
 */
public class VisualView implements Blockable {

	@Override
	public void block() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unblock() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Creates a new VisualView.
	 * 
	 * @param imageWhenUnblocked image that is shown when this is not blocked
	 * @param imageWhenBlocked image that is shown when this is blocked
	 * @param action the action that this View can invoke
	 */
	public VisualView(final Image imageWhenUnblocked, final Image imageWhenBlocked, final Action action) {
		
	}
}
