package edu.kit.wavelength.client.view;

/**
 * An ExerciseView displays an {@link Exercise}.
 * 
 * Hence it provides methods for setting a task and its solution.
 * A solution can be shown or hidden.
 * 
 * @author Muhammet Gümüs
 * @version 1.0
 *
 */
public interface ExerciseView {

	/**
	 * Sets the task of the currently selected {@link Exercise}.
	 * @param task the task of an {@link Exercise}
	 */
	void setTask(final String task);
	
	/**
	 * Sets the solution of the currently selected {@link Exercise}.
	 * @param solution the solution of an {@link Exercise}
	 */
	void setSolution(final String solution);
	
	/**
	 * Hides the solution.
	 */
	void hideSolution();
	
	/**
	 * Shows the solution.
	 */
	void showSolution();
}
