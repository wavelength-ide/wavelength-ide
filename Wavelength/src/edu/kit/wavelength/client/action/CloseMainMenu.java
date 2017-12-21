package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.view.Hideable;

/**
 * This action hides the main menu when the user presses the main menu button.
 */
public class CloseMainMenu implements Action {

	private Hideable mainMenu;
	
	@Override
	public void run() {
		// mainMenu.hide();		
	}

}
