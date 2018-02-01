package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.html.Text;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.view.App;

/**
 * This class starts a new reduction process by requesting only the first
 * reduction step.
 */
public class StepByStep implements Action {

	private static App app = App.get();

	// UI components that can no longer be interacted with
	private static List<ListBox> componentsToLock = new ArrayList<ListBox>(Arrays.asList(
			app.outputSizeBox(),
			app.outputFormatBox()
			));


	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}

	/**
	 * Reads the users input and all required options from the option menus and
	 * delegates the reduction process to the Executor. Disables the editor and some
	 * option menus and toggles the buttons accordingly.
	 */
	@Override
	public void run() {
		String code = app.editor().read();

		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));

		String sizeName = app.outputSizeBox().getSelectedItemText();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));

		List<Library> libraries = app.libraryCheckBoxes().stream().filter(CheckBox::getValue)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getName().equals(l.getName())))
				.collect(Collectors.toList());

		try {
			app.executor().stepByStep(code, order, size, libraries);
		} catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int column = e.getColumn();
			app.outputArea().add(new Text(message + ":" + row + ":" + column));
			return;
		}

		app.outputFormatBox().setEnabled(false);
		app.outputSizeBox().setEnabled(false);
		
		
		if (app.executor().canStepBackward()) {
			app.backwardsButton().setEnabled(true);
		}
		app.stepByStepButton().setEnabled(false);
		if (app.executor().canStepForward()) {
			app.forwardButton().setEnabled(true);
		} else {
			app.reductionOrderBox().setEnabled(false);
		}
		app.cancelButton().setEnabled(true);
		
		app.exerciseButtons().forEach(b -> b.setEnabled(false));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(false));
		
		app.unpauseButton().setVisible(true);
		app.runButton().setVisible(false);

		// TODO: determine the selected output format, display and lock it, hide the other
		
	}
}
