package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * In this state the user can analyze the calculation of a term in depth.
 *
 * The user cannot input a new term and the calculation only advances one step at a time.
 */
public class StepByStep extends AppState {
	
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
