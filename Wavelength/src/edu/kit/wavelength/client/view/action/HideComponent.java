package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This action generalizes interaction with UI components that do nothing but
 * being shown or hid.
 */
public class HideComponent implements Action {

	// intended for: using the menu button, using the export button, hiding the
	// solution

	private Hideable hideable;

	/**
	 * Constructs a new action handler for a UI component thats only purpose is to
	 * be shown or hid.
	 * 
	 * @param hideable
	 *            The UI component that is supposed to be hid or shown.
	 */
	private HideComponent(Hideable hideable) {
		this.hideable = hideable;
	}

	/**
	 * Hides the given component if currently displayed, displays it otherwise.
	 */
	@Override
	public void run() {
		if (hideable.isShown()) {
			hideable.hide();
		} else {
			hideable.show();
		}
	}

}
