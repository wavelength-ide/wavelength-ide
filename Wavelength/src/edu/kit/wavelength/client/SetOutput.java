package edu.kit.wavelength.client;

import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.Output;

/**
 * TODO: change name and/or package?
 */
public class SetOutput implements Observer {
	
	private UIState appController;
	private Output output;

	@Override
	public void executionStarted() {
		// clear view -> create new Output object?
		// appController.clearOutput()
	}

	@Override
	public void executionStopped() {		
		// appController.stop() -> state transition
	}

	@Override
	public void termToDisplay(LambdaTerm term) {	
		// appController.addTerm()
		// output.addTerm()
	}

	@Override
	public void popTerm() {	
		// output.popTerm()
	}

}
