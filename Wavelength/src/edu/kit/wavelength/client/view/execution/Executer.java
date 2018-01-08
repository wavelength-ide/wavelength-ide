package edu.kit.wavelength.client.view.execution;

import java.util.List;

import edu.kit.wavelength.client.model.ExecutionState;
import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;

public interface Executer {

	void start(String input, ReductionOrder order, OutputSize size, List<Library> libraries, List<Observer> observers);

	void pause();
	
	void unpause();
	
	void cancel();

	void stepForward();
	
	void stepBackward();

	void setReductionOrder(ReductionOrder reduction);
	
	ExecutionState getExecutionState();
	
	void terminate();
	
}
