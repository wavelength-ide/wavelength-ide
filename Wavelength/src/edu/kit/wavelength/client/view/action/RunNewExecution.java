package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.webui.components.Checkbox;
import edu.kit.wavelength.client.view.webui.components.TreeOutput;
import edu.kit.wavelength.client.view.webui.components.UnicodeOutput;

/**
 * This class starts a new reduction process and sets the view accordingly.
 */
public class RunNewExecution implements Action {

	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}

	/**
	 * Reads the users input and all required options from the option menus and
	 * delegates the reduction process to a new ExectuionEngine instance.
	 * Disables the editor and option menus and toggles the play button.
	 */
	@Override
	public void run() {
		App a = App.get();
		// example code for starting the execution
		String code = a.editor().read();
		String orderName = a.reductionOrderBox().read();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));
		String sizeName = a.outputSizeBox().read();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));
		List<Library> libraries = a.libraryBoxes().stream().filter(Checkbox::isSet)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.read().equals(l.getName())))
				.collect(Collectors.toList());
		a.executor().start(code, order, size, libraries);
		// possibly error handling, reporting back to editor etc.
		String outputFormatName = a.outputFormatBox().read();
		switch (outputFormatName) {
		case App.UnicodeOutputName:
			a.unicodeOutput().show();
			break;
		case App.TreeOutputName:
			a.treeOutput().show();
			break;
		}
	}
}
