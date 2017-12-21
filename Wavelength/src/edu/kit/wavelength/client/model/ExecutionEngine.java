package edu.kit.wavelength.client.model;

import edu.kit.wavelength.client.model.reductions.ReductionOrder;

public class ExecutionEngine {

	public ExecutionEngine(String input, ReductionType type, OutputSize size) {
		
	}
	
	/**
	 * Starts the reduction process.
	 */
	public void start() {

	}

	/**
	 * Pauses the reduction process after completing the current reduction step.
	 * A paused execution can be resumed by either invoking startRunning, or by using the stepForward method.
	 */
	public void pause() {
	}
	
	/**
	 * Cancels the reduction process. A cancelled instruction can not be restarted.
	 */
	public void cancel() {
	}

	/**
	 * Executes a single reduction of the current lambda-term
	 */
	public void stepForward() {

	}
	
	/**
	 * Reverts to the previously output lamba-term.
	 */
	public void stepBackward() {
	
	}
	
	/**
	 * Changes the active reduction order to the entered one.
	 * @param type The new reduction order.
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		
	}
	
	/**
	 * Changes the active output size to the entered one.
	 * @param size The new output size.
	 */
	public void setOutputSize(OutputSize size) {
		
	}
	
	/**
	 * Creates an ExecutionState object containing the current state of this ExecutionEngine.
	 * @return An ExecutionState object with the current state of the ExecutionEngine
	 */
	public ExecutionState getExecutionState() {
		return null;
	}
}
