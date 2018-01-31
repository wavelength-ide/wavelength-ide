package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListBox;

import edu.kit.wavelength.client.view.App;

/**
 * This action stops the currently running reduction process and re-enables all
 * input related components.
 */
public class Stop implements Action {

	private static App app = App.get();

	// UI components that can no longer be interacted with
	private static List<Button> componentsToLock = new ArrayList<Button>(Arrays.asList(
			app.backwardsButton(), 
			app.forwardButton(),
			app.cancelButton()
			));

	// UI components that can now be interacted with
	private static List<ListBox> componentsToUnlock = new ArrayList<ListBox>(Arrays.asList(
			app.outputFormatBox(),
			app.outputSizeBox(), 
			app.reductionOrderBox()
			));


	/**
	 * Re-enables the editor and all option menus and blocks all buttons except the
	 * run button. Does not clear the output view.
	 */
	@Override
	public void run() {
		// terminate running execution
		app.executor.terminate();

		// set view components
		componentsToLock.forEach(b -> b.setEnabled(false));
		// TODO: lock outputs
		app.editor.unlock();
		app.stepByStepButton().setEnabled(true);
		componentsToUnlock.forEach(b -> b.setEnabled(true));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(true));
		app.exerciseButtons().forEach(b -> b.setEnabled(true));
		app.exportButtons().forEach(b -> b.setEnabled(true));

		// toggle run/pause button
		app.pauseButton().setVisible(false);
		app.unpauseButton().setVisible(false);
		app.runButton().setVisible(true);
	}
}
