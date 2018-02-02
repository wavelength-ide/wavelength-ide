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
	
	private boolean terminated = true;
	private boolean paused = true;
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
		paused = false;
		Scheduler.get().scheduleIncremental(() -> {
			if (engine.isFinished()) {
				paused = true;
				return !paused;
			}
			List<LambdaTerm> displayedTerms = engine.stepForward();
			pushTerms(displayedTerms);
			if (paused && !engine.isCurrentDisplayed()) {
				LambdaTerm current = engine.displayCurrent();
				pushTerm(current);
			}
			return !paused;
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
		if (!terminated) {
			throw new IllegalStateException("trying to start execution while execution is not terminated");
		}
		engine = new ExecutionEngine(input, order, size, libraries);
		scheduleExecution();
		terminated = false;
	}
	
	/**
	 * Pauses the automatic execution, transitioning into the step by step mode.
	 */
	public void pause() {
		if (terminated) {
			throw new IllegalStateException("trying to pause execution while execution is terminated");
		}
		paused = true;
	}

	/**
	 * Unpauses the automatic execution, transitioning from step by step mode into automatic execution.
	 */
	public void unpause() {
		if (terminated) {
			throw new IllegalStateException("trying to unpause execution while execution is terminated");
		}
		scheduleExecution();
	}
	
	/**
	 * Terminates the step by step- and automatic execution.
	 */
	public void terminate() {
		if (terminated) {
			throw new IllegalStateException("trying to terminate a terminated execution");
		}
		pause();
		terminated = true;
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
		if (!terminated) {
			throw new IllegalStateException("trying to start execution while execution is not terminated");
		}
		engine = new ExecutionEngine(input, order, size, libraries);
		terminated = false;
		if (!engine.getDisplayed().isEmpty()) {
			pushTerm(engine.getDisplayed().get(0));
		}
	}
	
	/**
	 * Executes a single reduction of the current lambda term.
	 */
	public void stepForward() {
		if (terminated) {
			throw new IllegalStateException("trying to step while execution is terminated");
		}
		List<LambdaTerm> displayedTerms = engine.stepForward();
		pushTerms(displayedTerms);
	}
	
	/**
	 * Executes a single reduction of the supplied redex.
	 * @param redex The redex to be evaluated. Must be a redex, otherwise
	 * an exception is thrown
	 */
	public void stepForward(Application redex) {
		if (terminated) {
			throw new IllegalStateException("trying to step while execution is terminated");
		}
		List<LambdaTerm> displayedTerms = engine.stepForward(redex);
		pushTerms(displayedTerms);
	}
	
	/**
	 * Reverts to the previously output lambda term.
	 */
	public void stepBackward() {
		if (terminated) {
			throw new IllegalStateException("trying to step while execution is terminated");
		}
		engine.stepBackward();
		observers.forEach(o -> o.removeLastTerm());
		
	}

	/**
	 * Changes the active reduction order to the entered one.
	 * @param reduction The new reduction order
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		if (terminated) {
			throw new IllegalStateException("trying to set option while execution is terminated");
		}
		engine.setReductionOrder(reduction);
	}
	
	/**
	 * Checks whether stepBackward is possible.
	 * @return whether stepBackward is possible
	 */
	public boolean canStepBackward() {
		return isPaused() && engine.canStepBackward();
	}
	
	/**
	 * Checks whether stepForward is possible.
	 * @return whether stepForward is possible
	 */
	public boolean canStepForward() {
		return isPaused() && !engine.isFinished();
	}
	
	/**
	 * Checks whether the engine is paused.
	 * @return whether the engine is paused
	 */
	public boolean isPaused() {
		return !terminated && paused;
	}
	
	/**
	 * Checks whether the engine is terminated.
	 * @return whether the engine is terminated
	 */
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Wipes the memory of the last execution.
	 */
	public void wipe() {
		if (!terminated) {
			throw new IllegalStateException("trying to wipe the executor while executor is not terminated");
		}
		engine = null;
	}
	
	/**
	 * Returns the currently displayed lambda terms.
	 * @return lt
	 */
	public List<LambdaTerm> getDisplayed() {
		if (engine == null) {
			throw new IllegalStateException("trying to read data of execution while there is nothing to read (no execution yet or executor was wiped beforehand)");
		}
		return engine.getDisplayed();
	}
	
	public List<Library> getLibraries() {
		if (engine == null) {
			throw new IllegalStateException("trying to read data of execution while there is nothing to read (no execution yet or executor was wiped beforehand)");
		}
		return engine.getLibraries();
	}
	
	/**
	 * Serializes the Executor by serializing its ExecutionEngine.
	 * @return The Executor serialized String representation
	 */
	public String serialize() {
		return null;
	}

}
