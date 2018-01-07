package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * In this state the user can check his solution in detail.
 * 
 * The user may not input any new solution but analyze the calculation done with his 
 * solution in detail. The calculation advances one step at a time.
 */
public class ExcerciseStepByStep extends AppState {
	
	private Deactivatable editor;
	private Hideable solutionView;
	private Deactivatable reductionOptions;
	private Hideable blockScreen; 
	private Deactivatable output;
	private Deactivatable runButton;
	private Deactivatable buttons;
	private Hideable exitButton;

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
