package edu.kit.wavelength.client.view.execution;

import java.util.List;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.ExecutionState;
import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;

public class ConcurrentExecuter implements Executer {

	private ExecutionEngine engine;
	
	@Override
	public void start(String input, ReductionOrder order, OutputSize size, List<Library> libraries,
			List<Observer> observers) {
		// todo morgen
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepForward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepBackward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReductionOrder(ReductionOrder reduction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExecutionState getExecutionState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unpause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

}
