package edu.kit.wavelength.client.view.exercise;

import edu.kit.wavelength.client.model.serialization.Serializable;

/**
 * An exercise consists of a task specifying what the User is supposed to do and
 * a solution specifying what the result should look like. Additionally
 * exercises may provide a basis for a given task.
 */
public interface Exercise extends Serializable {

	/**
	 * Gets the name of the exercise.
	 * 
	 * @return The name of the exercise
	 */
	String getName();

	/**
	 * Returns the explanation of the exercise.
	 * 
	 * @return The description of the task
	 */
	String getTask();

	/**
	 * Returns the sample solution. Note that this may not be the only possible
	 * solution.
	 * 
	 * @return The solution of the exercise
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
	 * @return The predefined code
	 */
	String getPredefinitions();
}
