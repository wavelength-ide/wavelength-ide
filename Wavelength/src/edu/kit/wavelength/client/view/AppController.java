package edu.kit.wavelength.client.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.Observer;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.action.RunNewExecution;
import edu.kit.wavelength.client.view.api.Deactivatable;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.webui.components.Checkbox;
import edu.kit.wavelength.client.view.webui.components.Editor;
import edu.kit.wavelength.client.view.webui.components.LabeledButton;
import edu.kit.wavelength.client.view.webui.components.OptionBox;
import edu.kit.wavelength.client.view.webui.components.TreeOutput;
import edu.kit.wavelength.client.view.webui.components.UnicodeOutput;
import edu.kit.wavelength.client.view.webui.components.VisualButton;

/**
 * This class handles the current state of the application and the currently
 * displayed output. The initial state is the Input state and the output is
 * empty when the application is started.
 */
public class AppController implements Observer {
	
	private static AppController instance = null;
	
	protected AppController() {}
	
	public static AppController get() {
		if (instance == null) {
			instance = new AppController();
			instance.initialize();
		}
		return instance;
	}
	
	private static final String UnicodeOutputName = "Unicode";
	private static final String TreeOutputName = "Tree";
	private static final List<String> OutputNames = Arrays.asList(UnicodeOutputName, TreeOutputName);
	
	private VisualButton mainMenuButton;
	private Editor editor;
	private OptionBox outputFormat;
	private OptionBox reductionOrder;
	private OptionBox outputSize;
	private VisualButton stepBackwards;
	private VisualButton stepByStepMode;
	private VisualButton stepForwards;
	private VisualButton terminate;
	private VisualButton runPause;
	private TreeOutput treeOutput;
	private UnicodeOutput unicodeOutput;
	private VisualButton export;
	private VisualButton share;
	private List<Checkbox> libraries;
	private List<LabeledButton> exercises;
	private ExecutionEngine engine;
	
	private List<Deactivatable> deactivateOnStart;
	// etc.
	
	public void initialize() {
		mainMenuButton = new VisualButton(new Image(), new Image());
		editor = new Editor();
		outputFormat = new OptionBox(OutputNames);
		List<String> reductionOrders = ReductionOrders.all().stream().map(ReductionOrder::getName).collect(Collectors.toList());
		reductionOrder = new OptionBox(reductionOrders);
		List<String> outputSizes = OutputSizes.all().stream().map(OutputSize::getName).collect(Collectors.toList());
		outputSize = new OptionBox(outputSizes);
		stepBackwards = new VisualButton(new Image(), new Image());
		stepByStepMode = new VisualButton(new Image(), new Image());
		stepForwards = new VisualButton(new Image(), new Image());
		terminate = new VisualButton(new Image(), new Image());
		runPause = new VisualButton(new Image(), new Image());
		export = new VisualButton(new Image(), new Image());
		share = new VisualButton(new Image(), new Image());
		libraries = Libraries.all().stream().map(l -> new Checkbox(l.getName())).collect(Collectors.toList());
		exercises = Exercises.all().stream().map(e -> new LabeledButton(e.getName())).collect(Collectors.toList());
		// deactivation lists
		runPause.setAction(new RunNewExecution());
	}

	private static <T> T find(Collection<T> list, Predicate<? super T> pred) {
		return list.stream().filter(pred).findFirst().get();
	}
	
	/**
	 * This method gets called when the reduction process starts. It delegates the
	 * handling to the current state.
	 */
	public void start() {
		// example code for starting the execution
		String code = editor.read();
		String orderName = reductionOrder.read();
		ReductionOrder order = find(ReductionOrders.all(), o -> o.getName().equals(orderName));
		String sizeName = outputSize.read();
		OutputSize size = find(OutputSizes.all(), s -> s.getName().equals(sizeName));
		List<Library> libraries = this.libraries.stream()
				.filter(Checkbox::isSet)
				.map(libraryCheckbox -> find(Libraries.all(), l -> libraryCheckbox.read().equals(l.getName())))
				.collect(Collectors.toList());
		List<Observer> observers = Arrays.asList(this);
		engine = new ExecutionEngine(code, order, size, libraries, observers);
		// possibly error handling, reporting back to editor etc.
		String outputFormatName = outputFormat.read();
		switch (outputFormatName) {
		case UnicodeOutputName:
			unicodeOutput = new UnicodeOutput();
			// lots of formatting stuff
			break;
		case TreeOutputName:
			treeOutput = new TreeOutput();
			// lots of formatting stuff
			break;
		}
	}
	
	public void startStepByStep() {
		
	}

	/**
	 * This method gets called when the reduction process stops. It delegates the
	 * handling to the current state.
	 */
	public void stop() {

	}

	/**
	 * This method gets called when the reduction process is paused. It delegates
	 * the handling to the current state.
	 */
	public void pause() {

	}

	public void unpause() {
		
	}
	
	public void changeReductionOrder() {
		
	}
	
	/**
	 * This method gets called when an exercise is selected. It delegates the
	 * handling to the current state.
	 */
	public void enterExercise(Exercise selected) {

	}

	/**
	 * This method gets called when the user exits the exercise mode. It delegates
	 * the handling to the current state.
	 */
	public void exitExercise() {

	}
	
	/**
	 * Adds a lambda term to the current output.
	 */
	@Override
	public void addTerm(LambdaTerm term) {
		// add term to end of currentOutput
	}
	
	/**
	 * Removes the last lambda term from the current output.
	 */
	@Override
	public void popTerm() {
		// pop last term in currentOutput
	}

	@Override
	public void executionStarted() {

	}

	@Override
	public void executionStopped() {
		
	}
}
