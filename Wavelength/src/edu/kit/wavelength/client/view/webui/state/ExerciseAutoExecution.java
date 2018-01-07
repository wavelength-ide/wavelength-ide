package edu.kit.wavelength.client.view.webui.state;

import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * In this state the solution form the user is automatically checked.
 * 
 * The user may not input any new solution but is notified about intermediate results
 * of the calculation form his solution.
 */
public class ExerciseAutoExecution extends AppState{
	
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
