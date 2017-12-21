package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.HidableVisualView;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Readable;
import edu.kit.wavelength.client.view.VisualView;

/**
 * In this state the application automatically calculates a given term.
 * 
 * The user cannot input any new term for calculation but is notified about
 * intermediate results of the calculation.
 */
public class AutoExecution extends AppState {
	
	private Readable input;
	private Blockable editor;
	private Blockable reductionOptions;
	private Blockable output;
	private Blockable runButton;
	private Blockable buttons;
	private Blockable footerButtons;

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
	public void enterExercise() {
		
	}

	@Override
	protected void enter() {

	}

	@Override
	protected void exit() {

	}
}
