package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.webui.component.TextButton;
import edu.kit.wavelength.client.view.webui.component.TextField;

/**
 * This action toggles the solution button and panel.
 */
public class ToggleSolution implements Action {

	private static App app = App.get();

	/**
	 * If the solution panel is currently shown, hide it and display the show
	 * solution button. Otherwise show the solution panel and the hide solution
	 * button. Always hide the unused button.
	 */
	@Override
	public void run() {
		TextField solutionPanel = app.solutionPanel();
		TextButton showSolution = app.showSolutionButton();
		TextButton hideSolution = app.hideSolutionButton();
		if (solutionPanel.isShown()) {
			solutionPanel.hide();
			hideSolution.hide();
			showSolution.show();
		} else {
			solutionPanel.show();
			showSolution.hide();
			hideSolution.show();
		}

	}

}
