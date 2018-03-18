package edu.kit.wavelength.client.view.execution;

import java.util.Date;
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
	
	private enum S { Running, Paused, Terminated }
	
	private static class State { 
		S val;
		
		State(S v) { 
			this.val = v; 
		}
		
		State(State s) { 
			this(s.val); 
		}
	}
	
	private static int allowedReductionTimeMS = 100;

	private List<ExecutionObserver> executionObservers;
	private List<ControlObserver> controlObservers;
	
	private State state = new State(S.Terminated);
	
	private void setState(S v) { 
		state.val = v; 
		state = new State(state); 
	}
	
	private ExecutionEngine engine;

	/**
	 * Creates a new Executor.
	 * 
	 * @param executionObservers
	 *            Observers to update with reduced lambda terms
	 * @param controlObservers
	 *            Observers to notify when executor reaches certain states
	 */
	public Executor(List<ExecutionObserver> executionObservers, List<ControlObserver> controlObservers) {
		this.executionObservers = executionObservers;
		this.controlObservers = controlObservers;
	}

	private void pushTerm(LambdaTerm t) {
		executionObservers.forEach(o -> o.pushTerm(t));
	}

	private void pushTerms(Iterable<LambdaTerm> ts) {
		ts.forEach(this::pushTerm);
	}

	private void scheduleExecution() {
		final State state = this.state;
		Scheduler.get().scheduleIncremental(() -> {
			Date start = new Date();
			switch (state.val) {
			case Terminated:
				return false;
			case Paused:
				return false;
			default:
				while (true) {
					if (engine.isFinished()) {
						setState(S.Paused);
						controlObservers.forEach(ControlObserver::finish);
						return false;
					}
					List<LambdaTerm> displayedTerms = engine.stepForward();
					pushTerms(displayedTerms);
					Date end = new Date();
					if (end.getTime() - start.getTime() > allowedReductionTimeMS) {
						return true;
					}
				}
			}
		});
	}

	/**
	 * Starts the automatic execution of the input, parsing the term and then
	 * reducing it.
	 * 
	 * @param input
	 *            code to parse and reduce
	 * @param order
	 *            order with which to reduce
	 * @param size
	 *            which terms to push to observers
	 * @param libraries
	 *            libraries to consider when parsing
	 * @throws ParseException
	 *             thrown when input cannot be parsed
	 */
	public void start(String input, ReductionOrder order, OutputSize size, List<Library> libraries)
			throws ParseException {
		if (!isTerminated()) {
			throw new IllegalStateException("trying to start execution while execution is not terminated");
		}
		executionObservers.forEach(o -> o.clear());
		engine = new ExecutionEngine(input, order, size, libraries);
		setState(S.Running);
		if (!engine.getDisplayed().isEmpty()) {
			pushTerm(engine.getDisplayed().get(0));
		}
		scheduleExecution();
	}

	/**
	 * Initiates the step by step execution, allowing the caller to choose the next
	 * step.
	 * 
	 * @param input
	 *            code to parse and execute
	 * @param order
	 *            order with which to reduce
	 * @param size
	 *            which terms to push to observers
	 * @param libraries
	 *            libraries to consider when parsing
	 * @throws ParseException
	 *             thrown when input cannot be parsed
	 */
	public void stepByStep(String input, ReductionOrder order, OutputSize size, List<Library> libraries)
			throws ParseException {
		if (!isTerminated()) {
			throw new IllegalStateException("trying to start execution while execution is not terminated");
		}
		executionObservers.forEach(o -> o.clear());
		engine = new ExecutionEngine(input, order, size, libraries);
		setState(S.Paused);
		if (!engine.getDisplayed().isEmpty()) {
			pushTerm(engine.getDisplayed().get(0));
		}
	}
	
	/**
	 * Pauses the automatic execution, transitioning into the step by step mode.
	 */
	public void pause() {
		if (!isRunning()) {
			throw new IllegalStateException("trying to pause execution that isn't running");
		}
		setState(S.Paused);
		if (!engine.isCurrentDisplayed()) {
			LambdaTerm current = engine.displayCurrent();
			pushTerm(current);
		}
	}

	/**
	 * Unpauses the automatic execution, transitioning from step by step mode into
	 * automatic execution.
	 */
	public void unpause() {
		if (!isPaused()) {
			throw new IllegalStateException("trying to unpause execution that isn't paused");
		}
		setState(S.Running);
		scheduleExecution();
	}

	/**
	 * Terminates the step by step- and automatic execution.
	 */
	public void terminate() {
		if (isTerminated()) {
			throw new IllegalStateException("trying to terminate a terminated execution");
		}
		setState(S.Terminated);
		engine = null;
	}

	/**
	 * Executes a single reduction of the current lambda term.
	 */
	public void stepForward() {
		if (!isPaused()) {
			throw new IllegalStateException("trying to step while execution isn't paused");
		}
		List<LambdaTerm> displayedTerms = engine.stepForward();
		pushTerms(displayedTerms);
		if (!engine.isCurrentDisplayed()) {
			LambdaTerm current = engine.displayCurrent();
			pushTerm(current);
		}
	}

	/**
	 * Executes a single reduction of the supplied redex.
	 * 
	 * @param redex
	 *            The redex to be evaluated. Must be a redex, otherwise an exception
	 *            is thrown
	 */
	public void stepForward(Application redex) {
		if (!isPaused()) {
			throw new IllegalStateException("trying to step while execution isn't paused");
		}
		List<LambdaTerm> displayedTerms = engine.stepForward(redex);
		pushTerms(displayedTerms);
	}

	/**
	 * Reverts to the previously output lambda term.
	 */
	public void stepBackward() {
		if (!isPaused()) {
			throw new IllegalStateException("trying to step while execution isn't paused");
		}
		engine.stepBackward();
		executionObservers.forEach(o -> o.removeLastTerm());

	}

	/**
	 * Checks whether stepBackward is possible.
	 * 
	 * @return whether stepBackward is possible
	 */
	public boolean canStepBackward() {
		return isPaused() && engine.canStepBackward();
	}

	/**
	 * Checks whether stepForward is possible.
	 * 
	 * @return whether stepForward is possible
	 */
	public boolean canStepForward() {
		return isPaused() && !engine.isFinished();
	}

	/**
	 * Checks whether the engine is paused.
	 * 
	 * @return whether the engine is paused
	 */
	public boolean isPaused() {
		return state.val == S.Paused;
	}

	/**
	 * Checks whether the engine is terminated.
	 * 
	 * @return whether the engine is terminated
	 */
	public boolean isTerminated() {
		return state.val == S.Terminated;
	}

	/**
	 * Checks whether the engine is running.
	 * 
	 * @return whether the engine is running
	 */
	public boolean isRunning() {
		return state.val == S.Running;
	}

	/**
	 * Returns the currently displayed lambda terms.
	 * 
	 * @return lt
	 */
	public List<LambdaTerm> getDisplayed() {
		if (isTerminated()) {
			throw new IllegalStateException("trying to read data of execution while executor is terminated");
		}
		return engine.getDisplayed();
	}

	/**
	 * Returns the libraries in use by the engine.
	 * 
	 * @return libraries
	 */
	public List<Library> getLibraries() {
		if (isTerminated()) {
			throw new IllegalStateException("trying to read data of execution while executor is terminated");
		}
		return engine.getLibraries();
	}

	/**
	 * Serializes the Executor by serializing its ExecutionEngine.
	 * 
	 * @return The Executor serialized String representation
	 */
	public StringBuilder serialize() {
		if (engine == null) {
			return new StringBuilder("");
		} else {
			return engine.serialize();
		}
	}

	/**
	 * Deserializes the Executor by deserializing its ExecutionEngine. Also loads
	 * the correct content into OutputArea.
	 * 
	 * @param serialization
	 *            serialized Executor
	 */
	public void deserialize(String serialization) {
		if (serialization == "") {
			// engine was null
			return;
		} else {
			setState(S.Paused);
			this.engine = new ExecutionEngine(serialization);
			this.pushTerms(engine.getDisplayed());
		}
	}

	/**
	 * Changes the active reduction order to the entered one.
	 * 
	 * @param reduction
	 *            The new reduction order
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		if (isTerminated()) {
			throw new IllegalStateException("trying to set option while execution is terminated");
		}
		engine.setReductionOrder(reduction);
		executionObservers.forEach(o -> o.reloadTerm());
	}
	
	public void updatedOutputFormat() {
		if (isTerminated()) {
			throw new IllegalStateException("trying to set option while execution is terminated");
		}
		executionObservers.forEach(o -> o.reloadTerm());
	}
	
	public void setOutputSize(OutputSize s) {
		if (isTerminated()) {
			throw new IllegalStateException("trying to set option while execution is terminated");
		}
		engine.setOutputSize(s);
	}
}
