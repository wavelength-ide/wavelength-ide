package edu.kit.wavelength.client.view.action;

import edu.kit.wavelength.client.view.App;

/**
 * This class changes the view from exercise mode view to standard input view.
 */
public class EnterDefaultMode implements Action {

	private static App app = App.get();

	@Override
	public void run() {
		Control.wipe();
		
		app.editorExercisePanel().setWidgetHidden(app.exercisePanel(), true);
		app.solutionArea().setVisible(false);
		app.closeExercisePopup().hide();
		app.setCurrentExercise(null);
	}

}
