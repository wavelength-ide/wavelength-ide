package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This action opens the main menu.
 */
public class HideComponent implements Action {

	// intended for: using the menu button, using the export button
	
	private Hideable hideable;
	
	/**
	 * Constructs a new OpenMainMenu Action.
	 * 
	 * @param menu
	 *            The Menu that should be shown.
	 */
	private HideComponent(Hideable hideable) {
		this.hideable = hideable;
	}

	@Override
	public void run() {
		if (hideable.isShown()) {
			hideable.hide();
		} else {
			hideable.show();
		}
	}

}
