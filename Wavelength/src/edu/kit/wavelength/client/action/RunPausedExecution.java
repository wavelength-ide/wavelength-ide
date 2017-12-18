package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.ReductionControl;
import edu.kit.wavelength.client.view.ReductionOptions;

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
