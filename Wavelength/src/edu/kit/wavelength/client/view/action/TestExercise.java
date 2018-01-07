package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.exercise.Exercise;
import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * This Action tests a given {@link Exercise}. For each test case the solution
 * is calculated by the {@link ExecutionEngine} and matched against the user´s
 * solution. If the {@link Exercise} has predefined code then both the user´s
 * solution and the given solution are calculated by the {@link ExecutionEngine}
 * on given assignments of predefined variables. In order to directly get the
 * results of the {@link ExecutionEngine} this class implements its
 * {@link Observer} interface.
 * 
 */
public class TestExercise implements Action, Observer {

	private Exercise exercise;
	private ExecutionEngine userExecutionEngine;
	private ExecutionEngine testExecutionEngine;
	private LambdaTerm userSolution;
	private LambdaTerm testSolution;

	/**
	 * Creates a new Action for testing a specific {@link Exercise}.
	 * 
	 * @param exercise
	 *            the current Exercise
	 */
	public TestExercise(final Exercise exercise) {

	}

	@Override
	public void run() {

	}

	/**
	 * If the {@link Exercise} has predefined code reset userSolution and
	 * testSolution, else do nothing.
	 */
	public void executionStarted() {

	}

	/**
	 * Do not do anything here.
	 */
	public void executionStopped() {

	}

	/**
	 * Save the incoming result and wait for both results being available.
	 * Then compare the results: If they do not match tell the User otherwise continue with the next test case.
	 * If all test cases are passed tell the User about it.
	 */
	public void termToDisplay(LambdaTerm term) {

	}

	@Override
	public void popTerm() {
		
	}

}
