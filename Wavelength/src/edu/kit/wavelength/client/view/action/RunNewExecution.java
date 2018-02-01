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
		String code = app.editor().read();

		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));

		String sizeName = app.outputSizeBox().getSelectedItemText();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));

		List<Library> libraries = app.libraryCheckBoxes().stream().filter(CheckBox::getValue)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getName().equals(l.getName())))
				.collect(Collectors.toList());

		try {
			app.executor().start(code, order, size, libraries);
		} catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int column = e.getColumn();
			app.outputArea().add(new Text(message + ":" + row + ":" + column));
			return;
		}

		app.outputFormatBox().setEnabled(false);
		app.reductionOrderBox().setEnabled(false);
		app.outputSizeBox().setEnabled(false);
		
		app.backwardsButton().setEnabled(false);
		app.stepByStepButton().setEnabled(false);
		app.forwardButton().setEnabled(false);
		app.cancelButton().setEnabled(true);
		
		app.editor().lock();
		
		app.exerciseButtons().forEach(b -> b.setEnabled(false));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(false));
		
		app.runButton().setVisible(false);
		app.pauseButton().setVisible(true);

		// TODO: determine the selected output format, display and lock it, hide the other
	}
}
