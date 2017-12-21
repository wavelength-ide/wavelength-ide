package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.HidableVisualView;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Readable;
import edu.kit.wavelength.client.view.VisualView;

/**
 * This is the default state of the application. The user can input a term for calculation.
 */
public class Input extends AppState {

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
