package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Hideable;

/**
 * In this state the application presents a task to the user and is waiting for an answer.
 * 
 * The user can formulate his solution until he is ready to check it.
 */
public class ExcerciseInput extends AppState {
	
	private Deactivatable editor;
	private Hideable solutionView;
	private Hideable taskView;
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
