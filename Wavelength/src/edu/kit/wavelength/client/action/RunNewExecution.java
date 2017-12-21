package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.ReductionControl;
import edu.kit.wavelength.client.view.ReductionOptions;
import edu.kit.wavelength.client.view.Writable;

/**
 * This action causes the application to transition from Input (or ExerciseInput) state to
 * AutoExecution (or ExerciseAutoExecution) state when the user presses the run button. It is only
 * triggered, if the current state is Input (or ExerciseInput) while pressing the button.
 */
public class RunNewExecution implements Action {

	private ExecutionEngine execution;
	private UIState state;
	private ReductionOptions options;
	private ReductionControl buttons;
	private Hideable playButton;
	private Hideable pauseButton;
	private Readable input;
	private Writable output;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
