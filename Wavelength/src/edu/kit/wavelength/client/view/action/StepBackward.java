package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class removes the last shown reduction step from the output.
 */
public class StepBackward implements Action {

	private static App app = App.get();

	/**
	 * Removes the last shown step from the output.
	 */
	@Override
	public void run() {
		// TODO: remove step from output? -> Methode von component
		app.executor().stepBackward();

		// determine the selected output format and remove the last displayed term
		String outputFormatName = app.outputFormatBox().read();
		switch (outputFormatName) {
		case App.UnicodeOutputName:
			app.unicodeOutput().removeLastTerm();
			break;
		case App.TreeOutputName:
			app.treeOutput().removeLastTerm();
			break;
		}

		// lock stepping backwards if stepping back is not possible anymore
		if (app.executor().getDisplayed().isEmpty()) {
			app.stepBackwardButton().lock();
		}
	}

}
