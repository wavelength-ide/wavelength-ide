package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Input;
import edu.kit.wavelength.client.view.ReductionControl;
import edu.kit.wavelength.client.view.ReductionOptions;
import edu.kit.wavelength.client.view.Showable;

public class RunPausedExecution implements Action {
	
	private ExecutionEngine execution;
	private UIState state;
	private ReductionOptions options;
	private ReductionControl buttons;
	private Showable playButton;
	private Showable pauseButton;
	private Input input;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
