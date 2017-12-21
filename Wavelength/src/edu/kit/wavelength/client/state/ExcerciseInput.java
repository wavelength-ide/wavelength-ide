package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.ExchangableBehaviour;
import edu.kit.wavelength.client.view.Hideable;

/**
 * In this state the application presents a task to the user and is waiting for an answer.
 * 
 * The user can formulate his solution until he is ready to check it.
 */
public class ExcerciseInput extends AppState {
	
	private Blockable editor;
	private Hideable solutionView;
	private Hideable taskView;
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
