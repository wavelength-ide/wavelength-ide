package edu.kit.wavelength.client.view.execution;

import java.util.List;

import com.google.gwt.core.client.Scheduler;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;

/**
 * Concurrently reduces lambda terms.
 */
public class Executor implements Serializable {

	private List<ExecutionObserver> observers;
	
	private boolean autoRunning = false;
	private ExecutionEngine engine;
	
	/**
	 * Creates a new Executor.
	 * @param observers Observers to update with reduced lambda terms
	 */
	public Executor(List<ExecutionObserver> observers) {
		this.observers = observers;
	}
	
	private void pushTerm(LambdaTerm t) {
		observers.forEach(o -> o.pushTerm(t));
	}
	
	private void pushTerms(Iterable<LambdaTerm> ts) {
		ts.forEach(this::pushTerm);
	}
	
	private void scheduleExecution() {
		autoRunning = true;
		Scheduler.get().scheduleIncremental(() -> {
			if (engine.isFinished()) {
				autoRunning = false;
				return autoRunning;
			}
			List<LambdaTerm> displayedTerms = engine.stepForward();
			pushTerms(displayedTerms);
			if (!autoRunning && !engine.isCurrentDisplayed()) {
				LambdaTerm current = engine.displayCurrent();
				pushTerm(current);
			}
			return autoRunning;
		});
	}
	
	/**
	 * Starts the automatic execution of the input, parsing the term and then reducing it.
	 * @param input code to parse and reduce
	 * @param order order with which to reduce
	 * @param size which terms to push to observers
	 * @param libraries libraries to consider when parsing
	 * @throws ParseException thrown when input cannot be parsed
	 */
	public void start(String input, ReductionOrder order, OutputSize size, List<Library> libraries) throws ParseException {
		engine = new ExecutionEngine(input, order, size, libraries);
		scheduleExecution();
	}
	
	/**
	 * Pauses the automatic execution, transitioning into the step by step mode.
	 */
	public void pause() {
		autoRunning = false;
	}

	/**
	 * Unpauses the automatic execution, transitioning from step by step mode into automatic execution.
	 */
	public void unpause() {
		scheduleExecution();
	}
	
	/**
	 * Terminates the step by step- and automatic execution.
	 */
	public void terminate() {
		pause();
	}

	/**
	 * Initiates the step by step execution, allowing the caller to choose the next step.
	 * @param input code to parse and execute
	 * @param order order with which to reduce
	 * @param size which terms to push to observers
	 * @param libraries libraries to consider when parsing
	 * @throws ParseException thrown when input cannot be parsed
	 */
	public void stepByStep(String input, ReductionOrder order, OutputSize size, List<Library> libraries) throws ParseException {
		engine = new ExecutionEngine(input, order, size, libraries);
	}
	
	/**
	 * Executes a single reduction of the current lambda term.
	 */
	public void stepForward() {
		List<LambdaTerm> displayedTerms = engine.stepForward();
		pushTerms(displayedTerms);
	}
	
	/**
	 * Executes a single reduction of the supplied redex.
	 * @param redex The redex to be evaluated. Must be a redex, otherwise
	 * an exception is thrown
	 */
	public void stepForward(Application redex) {
		List<LambdaTerm> displayedTerms = engine.stepForward(redex);
		pushTerms(displayedTerms);
	}
	
	/**
	 * Reverts to the previously output lambda term.
	 */
	public void stepBackward() {
		engine.stepBackward();
	}

	/**
	 * Changes the active reduction order to the entered one.
	 * @param reduction The new reduction order
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		engine.setReductionOrder(reduction);
	}
	
	/**
	 * Checks whether stepBackward is possible.
	 * @return whether stepBackward is possible
	 */
	public boolean canStepBackward() {
		return engine.canStepBackward();
	}
	
	/**
	 * Checks whether stepForward is possible.
	 * @return whether stepForward is possible
	 */
	public boolean canStepForward() {
		return !engine.isFinished();
	}
	
	/**
	 * Returns the currently displayed lambda terms.
	 * @return lt
	 */
	public List<LambdaTerm> getDisplayed() {
		return engine.getDisplayed();
	}
	
	/**
	 * Serializes the Executor by serializing its ExecutionEngine.
	 * @return The Executor serialized String representation
	 */
	public String serialize() {
		return null;
	}

}
