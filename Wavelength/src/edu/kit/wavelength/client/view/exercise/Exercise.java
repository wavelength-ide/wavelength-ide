package edu.kit.wavelength.client.view.exercise;

/**
 * An exercise consists of a task specifying what the User is supposed to do and
 * a solution specifying what the result should look like. Additionally
 * exercises may provide a basis for a given task.
 */
public interface Exercise {

	/**
	 * Gets the name of the exercise.
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Returns the explanation of the exercise.
	 * 
	 * @return The task
	 */
	String getTask();

	/**
	 * Returns the sample solution. Note that this may not be the only possible
	 * solution.
	 * 
	 * @return the solution
	 */
	String getSolution();

	/**
	 * Returns whether this has predefined code or not.
	 * 
	 * @return {@code true} if this Exercise has predefined code
	 */
	boolean hasPredefinitions();

	/**
	 * Returns initial definitions that are supposed to be of help for the User.
	 * Note that this may be empty.
	 * 
	 * @return the predefined code
	 */
	String getPredefinitions();
}
