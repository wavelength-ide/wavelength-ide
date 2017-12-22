package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.Hideable;

/**
 * In this state the user can analyze the calculation of a term in depth.
 *
 * The user cannot input a new term and the calculation only advances one step at a time.
 */
public class StepByStep extends AppState {
	
	private Blockable editor;
	private Blockable reductionOptions;
	private Hideable blockScreen; 
	private Blockable output;
	private Blockable runButton;
	private Blockable buttons;
	
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
