package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.webui.components.VisualButton;

/**
 * In this state the application automatically calculates a given term.
 * 
 * The user cannot input any new term for calculation but is notified about
 * intermediate results of the calculation.
 */
public class AutoExecution extends AppState {
	
	private Deactivatable editor;
	private Deactivatable reductionOptions;
	private Deactivatable output;
	private Deactivatable runButton;
	private Deactivatable buttons;
	private Deactivatable footerButtons;

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
