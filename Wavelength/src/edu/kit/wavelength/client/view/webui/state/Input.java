package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * This is the default state of the application. The user can input a term for calculation.
 */
public class Input extends AppState {

	private Deactivatable editor;
	private Deactivatable reductionOptions;
	private Hideable blockScreen; 
	private Deactivatable output;
	private Deactivatable runButton;
	private Deactivatable buttons;

	@Override
	public void start() {

	}
	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void pause() {

	}
	
	@Override
	public void enterExport() {
		
	}

	@Override
	protected void enter() {

	}

	@Override
	protected void exit() {

	}
}
