package edu.kit.wavelength.client.model;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;

public class ExecutionEngine {

	/**
	 * Creates a new execution engine.
	 * @param input The textual representation of a lambda term to be handled
	 * @param order The reduction order to be used by default
	 * @param size The output size to be used
	 * @param libraries The libraries to be taken into consideration during parsing
	 * @param observers The observers to be notified during state changes
	 */
	public ExecutionEngine(String input, ReductionOrder order, OutputSize size, List<Library> libraries) {
		
	}

	/**
	 * Executes a single reduction of the current lambda term.
	 * @return whether this step is displayed
	 */
	public boolean stepForward() {
		return false;
	}
	
	/**
	 * Executes a single reduction of the supplied redex.
	 * @param term The redex to be evaluated. Must be a redex, otherwise
	 * an exception is thrown
	 */
	public void stepForward(Application redex) {

	}
	
	/**
	 * Reverts to the previously output lambda term.
	 */
	public void stepBackward() {
	
	}
	
	/**
	 * Returns a list of all lambda terms that have been displayed.
	 * @return A list of all lambda terms that have been displayed
	 */
	public List<LambdaTerm> getDisplayed() {
		return null;
	}
	
	/**
	 * Returns the last lambda term that has been displayed.
	 * @return The last lambda term that has been displayed
	 */
	public LambdaTerm getLast() {
		return null;
	}
	
	/**
	 * Displays the currently reduced term, adding it to the list of displayed terms.
	 * @return the current term
	 */
	public LambdaTerm displayCurrent() {
		return null;
	}
	
	/**
	 * Changes the active reduction order to the entered one.
	 * @param type The new reduction order.
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		
	}
	
	/**
	 * Serializes the ExecutionEngine by serializing its current OutputSize, ReductionOrder and the terms it holds.
	 * @return The ExecutionEngine's serialized String representation
	 */
	public String serialize() {
		return null;
	}
}
