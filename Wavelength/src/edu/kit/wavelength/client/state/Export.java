package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;

/**
 * In this state the application displays the shown output in the requested format.
 * 
 * All UI elements are disabled, the user can only copy the export output or exit this state.
 */

public class Export extends AppState {
	
	private AppState originState;
	
	private Hideable blockScreen;
	private Hideable exportWindow;
	private Writable exportOutput;

	
	@Override 
	public void exitExport() {
		
	}

	@Override
	protected void enter() {

	}

	@Override
	protected void exit() {

	}

}
