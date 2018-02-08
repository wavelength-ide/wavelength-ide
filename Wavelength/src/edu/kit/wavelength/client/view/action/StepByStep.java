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
 * This class starts a new reduction process by requesting only the first
 * reduction step.
 */
public class StepByStep implements Action {

	private static App app = App.get();

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
		

		app.outputArea().clear();
				
		String format = app.outputFormatBox().getSelectedItemText();
		switch(format) {
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

		app.outputFormatBox().setEnabled(false);
		app.outputSizeBox().setEnabled(false);
		
		Control.updateStepControls();
		app.stepByStepButton().setEnabled(false);
		app.cancelButton().setEnabled(true);
		
		app.exerciseButtons().forEach(b -> b.setEnabled(false));
		app.libraryCheckBoxes().forEach(b -> b.setEnabled(false));
		app.exportButtons().forEach(b -> b.setEnabled(true));
		
		app.unpauseButton().setVisible(true);
		app.runButton().setVisible(false);
		
		app.outputBlocker().removeStyleName("notclickable");
		
	}
}
