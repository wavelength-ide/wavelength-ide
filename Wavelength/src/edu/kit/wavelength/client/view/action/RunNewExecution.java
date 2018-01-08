package edu.kit.wavelength.client.view.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.update.UpdateOutput;
import edu.kit.wavelength.client.view.webui.components.Checkbox;
import edu.kit.wavelength.client.view.webui.components.TreeOutput;
import edu.kit.wavelength.client.view.webui.components.UnicodeOutput;

/**
 * This action causes the application to transition from {@link Input} (or
 * {@link ExerciseInput}) state to {@link AutoExecution} (or
 * {@link ExerciseAutoExecution}) state. It can only be triggered if the current
 * state is {@link} Input (or {@link ExerciseInput}).
 */
public class RunNewExecution implements Action {
	
	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}
	
	@Override
	public void run() {
		App a = App.get();
		// example code for starting the execution
		String code = a.editor().read();
		String orderName = a.reductionOrderBox().read();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));
		String sizeName = a.outputSizeBox().read();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));
		List<Library> libraries = a.libraryBoxes().stream()
				.filter(Checkbox::isSet)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.read().equals(l.getName())))
				.collect(Collectors.toList());
		List<Observer> observers = Arrays.asList(new UpdateOutput());
		// TODO: add engine wrapper
		engine = new ExecutionEngine(code, order, size, libraries, observers);
		// possibly error handling, reporting back to editor etc.
		String outputFormatName = a.outputFormatBox().read();
		switch (outputFormatName) {
		case App.UnicodeOutputName:
			// TODO: reset stuff
			unicodeOutput = new UnicodeOutput();
			// lots of formatting stuff
			break;
		case App.TreeOutputName:
			// TODO: reset stuff
			treeOutput = new TreeOutput();
			// lots of formatting stuff
			break;
		}
	}
}
