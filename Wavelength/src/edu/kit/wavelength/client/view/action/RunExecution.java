package edu.kit.wavelength.client.view.action;

import java.util.Collection;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

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
public class RunExecution implements Action {

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
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getText().equals(l.getName())))
				.collect(Collectors.toList());
		
		if (!app.executor().isTerminated()) {
			app.executor().terminate();
		}
		
		app.outputArea().clear();
		
		app.editor().unerror();
		try {
			app.executor().start(code, order, size, libraries);
		} catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int columnStart = e.getColumnStart();
			int columnEnd = e.getColumnEnd();
			FlowPanel error = new FlowPanel("span");
			error.add(new Text(message + " (row " + row + ", colums " + columnStart + "-" + (columnEnd - 1) + ")"));
			error.addStyleName("errorText");
			FlowPanel wrapper = new FlowPanel();
			wrapper.add(error);
			app.outputArea().add(wrapper);
			app.editor().error(message, row, row, columnStart, columnEnd);
			return;
		}
		
		Control.updateControls();
	}
}
