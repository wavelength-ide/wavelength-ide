package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * An ImageButton is an adapter class that wraps GWT's {@link Button} class.
 *
 * It is represented by an {@link Image}. The representing image depends on whether the
 * Button is deactivated or not. In addition it can be hidden from the UI.
 */
public class ImageButton implements Lockable, Hideable, Clickable {

	/**
	 * Creates a new VisualButton.
	 *
	 * @param button
	 *            the wrapped {@link Button}
	 * @param imageWhenActivated
	 *            The image that is shown when the Button is active.
	 * @param imageWhenDeactivated
	 *            The image that is shown when the Button is deactivated.
	 * 
	 */
	public ImageButton(final Button button, final Image imageWhenActivated, final Image imageWhenDeactivated) {

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
	public void setAction(Action action) {

	}

	@Override
	public boolean isShown() {
		return false;
	}

	@Override
	public boolean isLocked() {
		return false;
	}
}
