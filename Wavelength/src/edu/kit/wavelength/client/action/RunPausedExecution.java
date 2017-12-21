package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.ReductionControl;
import edu.kit.wavelength.client.view.ReductionOptions;

/**
 * This action causes the application to transition from StepByStep or
 * (ExerciseStepByStep) state to AutoExecution (or ExerciseAutoExecution) state
 * when the user presses the run button. It is only triggered, if the current
 * state is StepByStep (or ExerciseStepByStep) while pressing the button.
 */
public class RunPausedExecution implements Action {

	private ExecutionEngine execution;
	private UIState state;
	private ReductionOptions options;
	private ReductionControl buttons;
	private Hideable playButton;
	private Hideable pauseButton;
	private Readable input;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
