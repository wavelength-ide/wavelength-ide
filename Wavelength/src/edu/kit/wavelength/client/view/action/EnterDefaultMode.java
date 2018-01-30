package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Button;


import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Action;

/**
 * This class changes the view from exercise mode view to standard input view.
 */
public class EnterDefaultMode implements Action {

	private static App app = App.get();

	// UI components to hide from the user
	private static List<Widget> componentsToHide = new ArrayList<Widget>(Arrays.asList(
			app.closeTaskButton,
			app.toggleSolutionButton,
			app.solutionArea, 
			app.editorTaskPanel
			));
	
	// UI components that can now be interacted with
	private static List<ListBox> componentsToUnlock = new ArrayList<ListBox>(Arrays.asList(
			app.outputFormatBox,
			app.outputSizeBox, 
			app.reductionOrderBox
			));

	// UI components that can no longer be interacted with
	private static List<Button> componentsToLock = new ArrayList<Button>(Arrays.asList(
			app.backwardsButton, 
			app.forwardButton,
			app.cancelButton 
			));

	/**
	 * Resizes the editor window to full width, hides the solution and task windows
	 * and hides the buttons for exiting and showing the solution.
	 */
	@Override
	public void run() {
		// terminate the running execution
		app.executor.terminate();

		// set the view components
		componentsToHide.forEach(w -> w.setVisible(false));
		app.editor.unlock();
		componentsToUnlock.forEach(w -> w.setEnabled(true));
		app.libraryCheckBoxes.forEach(c -> c.setEnabled(true));
		app.exerciseButtons.forEach(b -> b.setEnabled(true));
		app.exportButtons.forEach(b -> b.setEnabled(true));
			
		componentsToLock.forEach(w -> w.setEnabled(false));
		// TODO: lock output
		
		
		// toggle run/pause button
		app.pauseButton.setVisible(false);
		app.runButton.setVisible(true);

		// clear exercise panels
		app.solutionArea.clear();
		app.taskDescriptionLabel.setText("");
		// TODO: clear input and output -> leerer String
		app.editor.write("");
		
		// TODO: clear outputs
		// app.unicodeOutput().write("");
		// app.treeOutput().write("");
		
		// TODO: set unicode output as default
		// app.unicodeOutput().show();
	}

}
