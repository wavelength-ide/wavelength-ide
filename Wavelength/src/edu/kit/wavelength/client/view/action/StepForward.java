package edu.kit.wavelength.client.view.action;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
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

/**
 * This action requests and displays the next reduction step of the current
 * execution.
 */
public class StepForward implements Action {

	private static App app = App.get();

	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}
	
	/**
	 * Requests and displays the next reduction step.
	 */
	@Override
	public void run() {
		if (app.executor().isRunning()) {
			app.executor().pause();
			app.executor().stepForward();
			Control.updateControls();
			return;
		}
		if (app.executor().isPaused()) {
			app.executor().stepForward();
			Control.updateControls();
			return;
		}
		
		String code = app.editor().read();

		String orderName = app.reductionOrderBox().getSelectedItemText();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));

		String sizeName = app.outputSizeBox().getSelectedItemText();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));

		List<Library> libraries = app.libraryCheckBoxes().stream().filter(CheckBox::getValue)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.getName().equals(l.getName())))
				.collect(Collectors.toList());
				
		String format = app.outputFormatBox().getSelectedItemText();
		switch (format) {
		case "Unicode Output":
			app.setUnicode(true);
			app.setTree(false);
			break;
		case "Tree Output":
			app.setUnicode(false);
			app.setTree(true);
			break;
		default: break;
		}

		app.editor().unerror();
		try {
			app.executor().stepByStep(code, order, size, libraries);
		} catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int columnStart = e.getColumnStart();
			int columnEnd = e.getColumnEnd();
			app.outputArea().add(new Text(message + "(row " + row + ", colums " + columnStart + "-" + columnEnd + ")"));
			app.editor().error(message, row, row, columnStart, columnEnd);
			return;
		}
		
		Control.updateControls();
	}
}
