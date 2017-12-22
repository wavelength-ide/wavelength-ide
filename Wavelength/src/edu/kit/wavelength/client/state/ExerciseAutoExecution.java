package edu.kit.wavelength.client.state;

import edu.kit.wavelength.client.view.Blockable;
import edu.kit.wavelength.client.view.Readable;

/**
 * In this state the solution form the user is automatically checked.
 * 
 * The user may not input any new solution but is notified about intermediate results
 * of the calculation form his solution.
 */
public class ExerciseAutoExecution extends AppState{
	
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
