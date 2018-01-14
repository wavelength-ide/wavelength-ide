package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.webui.component.Checkbox;

/**
 * This class starts a new reduction process by requesting only the first
 * reduction step.
 */
public class StepByStep implements Action {

	private static App app = App.get();

	private static List<Lockable> componentsToLock = Arrays.asList(app.editor(), app.outputSizeBox(),
			app.outputFormatBox(), app.stepByStepModeButton());

	static {
		componentsToLock.addAll(app.libraryBoxes());
		componentsToLock.addAll(app.exerciseButtons());
	}
	
	private static List<Lockable> componentsToUnlock = Arrays.asList(app.stepForwardButton());

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
		// read the users input
		String code = app.editor().read();

		// determine the selected reduction order
		String orderName = app.reductionOrderBox().read();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));

		// determine the selected output size
		String sizeName = app.outputSizeBox().read();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));

		// determine the selected libraries
		List<Library> libraries = app.libraryBoxes().stream().filter(Checkbox::isSet)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.read().equals(l.getName())))
				.collect(Collectors.toList());

		app.executor().stepByStep(code, order, size, libraries);

		// set the view
		componentsToLock.forEach(Lockable::lock);
		componentsToUnlock.forEach(Lockable::unlock);

		// determine the selected output format, then display and unlock it
		String outputFormatName = app.outputFormatBox().read();
		switch (outputFormatName) {
		case App.UnicodeOutputName:
			app.unicodeOutput().show();
			app.unicodeOutput().unlock();
			break;
		case App.TreeOutputName:
			app.treeOutput().show();
			app.treeOutput().unlock();
			break;
		}
	}
}
