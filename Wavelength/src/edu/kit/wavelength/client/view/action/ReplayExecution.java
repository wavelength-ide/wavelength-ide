package edu.kit.wavelength.client.view.action;

import java.util.List;
import java.util.stream.Collectors;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.html.Text;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.view.App;

public class ReplayExecution implements Action {

	private static App app = App.get();
	
	@Override
	public void run() {
		app.executor().replay();

		app.outputFormatBox().setEnabled(false);
		app.outputSizeBox().setEnabled(false);
		
		Control.updateStepControls();
		app.stepByStepButton().setEnabled(false);
		
		app.exerciseButtons().forEach(b -> b.setEnabled(false));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(false));
		app.exportButtons().forEach(b -> b.setEnabled(true));
		
		app.replayButton().setVisible(false);
		app.cancelButton().setVisible(true);
		app.unpauseButton().setVisible(true);
		app.runButton().setVisible(false);
		
		app.outputBlocker().removeStyleName("notclickable");
	}

}
