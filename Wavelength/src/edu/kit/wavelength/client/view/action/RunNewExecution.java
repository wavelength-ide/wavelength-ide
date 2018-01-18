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
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.webui.component.Checkbox;

/**
 * This class starts a new reduction process and sets the view accordingly.
 */
public class RunNewExecution implements Action {

	// for brevity
	private static App app = App.get();

	// list of UI components to lock
	private static List<Lockable> lockOnRun = Arrays.asList(
			app.outputFormatBox(), 
			app.reductionOrderBox(),
			app.outputSizeBox(), 
			app.stepBackwardButton(), 
			app.stepByStepModeButton(), 
			app.stepForwardButton()
			);
	
	static {
		lockOnRun.addAll(app.exerciseButtons());
		lockOnRun.addAll(app.libraryBoxes());
		lockOnRun.addAll(app.exportFormatButtons());
	}

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
		
		// TODO: possibly error handling, reporting back to editor etc.
		Parser testParser = new Parser(libraries);
		
		try {
			testParser.parse(code);
		} 
		catch (ParseException e) {
			String message = e.getMessage();
			int row = e.getRow();
			int column = e.getColumn();
			// TODO: set text in output
			return;
		}
		
		
		// start the execution with the selected options
		app.executor().start(code, order, size, libraries);


		// lock the view components
		lockOnRun.forEach(Lockable::lock);

		// runButton -> pauseButton
		app.runButton().hide();
		app.pauseButton().show();

		// determine the selected output format, display and lock it, hide the other
		String outputFormatName = app.outputFormatBox().read();
		switch (outputFormatName) {
		case App.UnicodeOutputName:
			app.treeOutput().hide();
			app.unicodeOutput().show();
			app.unicodeOutput().lock();
			break;
		case App.TreeOutputName:
			app.unicodeOutput().hide();
			app.treeOutput().show();
			app.treeOutput().lock();
			break;
		}
	}
}
