package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.execution.ControlObserver;

public class FinishExecution implements ControlObserver {

	private static App app = App.get();
	
	@Override
	public void finish() {
		Control.updateStepControls();
		
		app.backwardsButton().setEnabled(false);
		app.stepByStepButton().setEnabled(true);
		app.forwardButton().setEnabled(false);

		app.outputFormatBox().setEnabled(true);
		app.outputSizeBox().setEnabled(true);
		app.reductionOrderBox().setEnabled(true);

		app.editor().unlock();

		app.libraryCheckBoxes().forEach(b -> b.setEnabled(true));
		app.exerciseButtons().forEach(b -> b.setEnabled(true));
		app.exportButtons().forEach(b -> b.setEnabled(true));
		app.shareButton().setEnabled(true);

		app.replayButton().setVisible(true);
		app.replayButton().setEnabled(true);
		app.cancelButton().setVisible(false);
		app.pauseButton().setVisible(false);
		app.pauseButton().setEnabled(true);
		app.unpauseButton().setVisible(false);
		app.unpauseButton().setEnabled(true);
		app.runButton().setVisible(true);
		app.runButton().setEnabled(true);
		
		app.outputBlocker().addStyleName("notclickable");
	}

}
