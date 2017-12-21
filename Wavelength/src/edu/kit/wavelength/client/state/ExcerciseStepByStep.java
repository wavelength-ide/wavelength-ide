package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.ExchangableBehaviour;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Readable;

/**
 * In this state the user can check his solution in detail.
 * 
 * The user may not input any new solution but analyze the calculation done with his 
 * solution in detail. The calculation advances one step at a time.
 */
public class ExcerciseStepByStep extends AppState {
	
	private Readable input;
	private Blockable editor;
	private Hideable solutionView;
	private Blockable reductionOptions;
	private Hideable blockScreen; 
	private Blockable output;
	private Blockable runButton;
	private Blockable buttons;
	private Hideable exitButton;
	private ExchangableBehaviour solutionButton;

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
