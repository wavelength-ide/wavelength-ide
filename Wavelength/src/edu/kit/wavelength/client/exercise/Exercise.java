package edu.kit.wavelength.client.exercise;

/**
 * An exercise consists of a task specifying what the User is supposed to do and
 * a solution specifying what the result should look like. Additionally
 * exercises may provide a basis for a given task. In order to evaluate a
 * solution there are some test cases provided as Strings, meaning that they
 * need to be evaluated by the {@link ExecutionEngine} and matched against a
 * possible solution.
 */
public interface Exercise {

	/**
	 * Returns the task.
	 * 
	 * @return the task
	 */
	String getTask();

	/**
	 * Returns the solution
	 * 
	 * @return the solution
	 */
	String getSolution();

	/**
	 * Returns whether this has predefined code or not.
	 * 
	 * @return {@code true} if this Exercise has predefined code and {@code false}
	 *         if not.
	 */
	boolean hasPredefinitions();

	/**
	 * Returns initial definitions that are supposed to be of help for the User.
	 * Note that this may be empty.
	 * 
	 * @return predefined code
	 */
	String getPredefinitions();

	/**
	 * Returns an iterable collection of test cases.
	 * 
	 * @return test cases
	 */
	Iterable<String> getTestCases();
}
