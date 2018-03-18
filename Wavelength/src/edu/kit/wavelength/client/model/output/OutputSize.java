package edu.kit.wavelength.client.model.output;

import java.util.List;

import edu.kit.wavelength.client.model.serialization.Serializable;

/**
 * Policy to decide which {@link LambdaTerm}s should be displayed, both live and
 * at the end of the computation.
 *
 */
public interface OutputSize extends Serializable {
	/**
	 * Decides whether the step with the given number should be displayed live.
	 * 
	 * @param step
	 *            The step number to be considered
	 * @return Whether the given step should be displayed live
	 */
	boolean displayLive(int step);

	/**
	 * Decides which steps should be displayed after the computation has ended.
	 * 
	 * @param totalSteps
	 *            The total number of steps the computation took
	 * @param lastDisplayed
	 *            The last step that has been displayed, either according to a
	 *            policy or through manual step by step execution
	 * @return A list of step numbers, in the order in which they should be
	 *         displayed The step numbers may not be smaller than (totalSteps -
	 *         numToPreserve + 1.)
	 */
	List<Integer> displayAtEnd(int totalSteps, int lastDisplayed);

	/**
	 * Returns the number of terms that should be preserved even if displayLive()
	 * returns false.
	 * 
	 * @return
	 */
	int numToPreserve();

	/**
	 * Returns the name of the output size.
	 * 
	 * @return The name of the output size
	 */
	String getName();
}
