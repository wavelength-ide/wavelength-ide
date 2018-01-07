package edu.kit.wavelength.client.view.webui.components;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This class represents a screen that is laid over the UI in order to prevent
 * the user from activating UI elements that are supposed to be blocked. Hiding
 * this means deactivating the blocking functionality.
 */
public class WindowFocus implements Hideable {

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

}
