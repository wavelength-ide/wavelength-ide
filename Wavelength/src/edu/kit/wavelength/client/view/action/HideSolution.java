package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.api.Hideable;

/**
 * This action hides the shown sample solution of an Exercise.
 */
public class HideSolution implements Action {

	private Hideable solutionView;

	/**
	 * Constructs a new HidesSolution Action
	 * 
	 * @param solutionView
	 *            The UI element to hide, the solution is currently displayed in
	 *            this element
	 */
	public HideSolution(Hideable solutionView) {

	}

	@Override
	public void run() {

	}

}
