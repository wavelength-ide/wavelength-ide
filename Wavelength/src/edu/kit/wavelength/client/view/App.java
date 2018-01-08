package edu.kit.wavelength.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.view.action.RunNewExecution;
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
public class App {
	
	private static App instance = null;
	
	// protected so that App class can be mocket
	protected App() {}
	
	public static App get() {
		if (instance == null) {
			instance = new App();
			instance.initialize();
		}
		return instance;
	}
	
	// settable to mock the App with mockito
	public static void set(App c) {
		instance = c;
	}
	
	public static final String UnicodeOutputName = "Unicode";
	public static final String TreeOutputName = "Tree";
	public static final List<String> OutputNames = Arrays.asList(UnicodeOutputName, TreeOutputName);
	
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
	// etc.
	
	public VisualButton mainMenuButton() {
		return mainMenuButton;
	}
	
	public Editor editor() {
		return editor;
	}
	
	public OptionBox outputFormatBox() {
		return outputFormat;
	}
	
	public OptionBox reductionOrderBox() {
		return reductionOrder;
	}
	
	public OptionBox outputSizeBox() {
		return outputSize;
	}
	
	public VisualButton stepBackwardsButton() {
		return stepBackwards;
	}
	
	public VisualButton stepByStepModeButton() {
		return stepByStepMode;
	}
	
	public VisualButton stepForwardsButton() {
		return stepForwards;
	}
	
	public VisualButton terminateButton() {
		return terminate;
	}
	
	public VisualButton runPauseButton() {
		return runPause;
	}
	
	public TreeOutput treeOutput() {
		return treeOutput;
	}
	
	public UnicodeOutput unicodeOutput() {
		return unicodeOutput;
	}
	
	public VisualButton exportButton() {
		return export;
	}
	
	public VisualButton shareButton() {
		return share;
	}
	
	public List<Checkbox> libraryBoxes() {
		return libraries;
	}
	
	public List<LabeledButton> exerciseButtons() {
		return exercises;
	}
	
	public ExecutionEngine engine() {
		return engine;
	}
	
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
}
