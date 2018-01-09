package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * A VisualButton is an adapter class that wraps a GWT Button.
 *
 * It is represented by an {@link Image}. The representing image changes if the
 * Button is blocked or not. In Addition it can be hidden form the UI.
 */
public class VisualButton implements Deactivatable, Hideable, Clickable {

	/**
	 * Creates a new VisualButton.
	 *
	 * @param button
	 *            the wrapped {@link Button}
	 * @param imageWhenUnblocked
	 *            The image that is shown when the Button is not blocked.
	 * @param imageWhenBlocked
	 *            The image that is shown when the Button is blocked.
	 * 
	 */
	public VisualButton(final Button button, final Image imageWhenUnblocked, final Image imageWhenBlocked) {

	}

	@Override
	public void block() {

	}

	@Override
	public void unblock() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	@Override
	public void setAction(Action action) {

	}

	@Override
	public boolean isShown() {
		return false;
	}
}
