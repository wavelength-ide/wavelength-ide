package edu.kit.wavelength.client.view.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.view.App;

/**
 * This class starts a new reduction process and sets the view accordingly.
 */
public class RunNewExecution implements Action {

	private static App app = App.get();

	// UI components that can no longer be interacted with
	private static List<ListBox> optionBoxesToLock = new ArrayList<ListBox>(Arrays.asList(
			app.outputFormatBox(), 
			app.reductionOrderBox(),
			app.outputSizeBox()	
			));
	
	private static List<Button> buttonsToLock = new ArrayList<Button>(Arrays.asList(
			app.backwardsButton(), 
			app.stepByStepButton(), 
			app.forwardButton()
			));


	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}

	/**
	 * Reads the users input and all required options from the option menus and
	 * delegates the reduction process to the Executor. Disables the editor and
	 * option menus and toggles the play button.
	 */
	@Override
	public void run() {
		// read the users input
		String code = app.editor.read();

		// determine the selected reduction order
		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));

		// determine the selected output size
		String sizeName = app.outputSizeBox().getSelectedItemText();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));

		// determine the selected libraries
		// TODO: find isSet in doc
		List<Library> libraries = app.libraryCheckBoxes().stream().filter(CheckBox::getValue)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getName().equals(l.getName())))
				.collect(Collectors.toList());
		
		// TODO: executor needs to throw exception
		// app.executor.start(code, order, size, libraries);

		try {
			// start the execution with the selected options
			app.executor().start(code, order, size, libraries);
		} catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int column = e.getColumn();
			app.outputArea().add(new Text(message + ":" + row + ":" + column));
			return;
		}

		
		// lock the view components
		optionBoxesToLock.forEach(b -> b.setEnabled(false));
		buttonsToLock.forEach(b -> b.setEnabled(false));
		app.exerciseButtons().forEach(b -> b.setEnabled(false));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(false));
		app.exportButtons().forEach(b -> b.setEnabled(false));
		app.cancelButton().setEnabled(true);


		// toggle run/pause button
		app.runButton().setVisible(false);
		app.pauseButton().setVisible(true);

		// TODO: determine the selected output format, display and lock it, hide the other
	}
}
