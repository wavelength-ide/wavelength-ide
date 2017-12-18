package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.ReductionControl;
import edu.kit.wavelength.client.view.ReductionOptions;
import edu.kit.wavelength.client.view.Writable;

public class Pause implements Action {
	
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
