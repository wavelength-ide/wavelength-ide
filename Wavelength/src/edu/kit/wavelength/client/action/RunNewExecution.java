package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Readable;

/**
 * This action causes the application to transition from Input (or ExerciseInput) state to
 * AutoExecution (or ExerciseAutoExecution) state when the user presses the run button. It is only
 * triggered, if the current state is Input (or ExerciseInput) while pressing the button.
 */
public class RunNewExecution implements Action {

	private ExecutionEngine engine;
	private UIState state;
	private Readable input;

	public RunNewExecution(ExecutionEngine engine, UIState state, Readable input) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
<<<<<<< cb8a215ec605430a6e70d07c5c84bb0110f92390

=======
>>>>>>> Actions überholt.
	}
}
