package edu.kit.wavelength.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.action.RunNewExecution;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.update.UpdateTreeOutput;
import edu.kit.wavelength.client.view.update.UpdateUnicodeOutput;
import edu.kit.wavelength.client.view.webui.component.Checkbox;
import edu.kit.wavelength.client.view.webui.component.Editor;
import edu.kit.wavelength.client.view.webui.component.ImageButton;
import edu.kit.wavelength.client.view.webui.component.OptionBox;
import edu.kit.wavelength.client.view.webui.component.PopUpWindow;
import edu.kit.wavelength.client.view.webui.component.TextButton;
import edu.kit.wavelength.client.view.webui.component.TextField;
import edu.kit.wavelength.client.view.webui.component.TreeOutput;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;

/**
 * This class handles the current state of the application and the currently
 * displayed output. The initial state is the Input state and the output is
 * empty when the application is started.
 */
public final class App implements Serializable {
	
	private static App instance = null;
	
	protected App() {}
	
	/**
	 * Gets a singleton instance of App.
	 * @return instance
	 */
	public static App get() {
		if (instance == null) {
			instance = new App();
			instance.initialize();
		}
		return instance;
	}
	
	/**
	 * Sets the singleton instance of App for testing.
	 * @param a instance to set
	 */
	public static void set(App a) {
		instance = a;
	}
	
	/**
	 * Name of the unicode format.
	 */
	public static final String UnicodeOutputName = "Unicode";
	/**
	 * Name of the tree format.
	 */
	public static final String TreeOutputName = "Tree";
	
	private ImageButton mainMenuButton;
	private Editor editor;
	private OptionBox outputFormat;
	private OptionBox reductionOrder;
	private OptionBox outputSize;
	private ImageButton stepBackward;
	private ImageButton stepByStepMode;
	private ImageButton stepForward;
	private ImageButton terminate;
	private ImageButton run;
	private ImageButton pause;
	private TreeOutput treeOutput;
	private UnicodeOutput unicodeOutput;
	private ImageButton export;
	private PopUpWindow exportWindow;
	private ImageButton share;
	private TextField sharePanel;
	private List<Checkbox> libraries;
	private List<TextButton> exercises;
	private List<TextButton> exportFormats;
	private Executor executor;
	// etc.
	
	/**
	 * Gets the panel that contains the URL to play back the state of the application.
	 * @return panel
	 */
	public TextField sharePanel() {
		return this.sharePanel;
	}
	
	/**
	 * Gets the window that shows exported output.
	 * @return window
	 */
	public PopUpWindow exportWindow() {
		return this.exportWindow;
	}
	
	/**
	 * Gets the button that is used to open the main menu.
	 * @return button
	 */
	public ImageButton mainMenuButton() {
		return mainMenuButton;
	}
	
	/**
	 * Gets the editor.
	 * @return editor
	 */
	public Editor editor() {
		return editor;
	}
	
	/**
	 * Gets the option box that allows the user to choose which output format to use.
	 * @return box
	 */
	public OptionBox outputFormatBox() {
		return outputFormat;
	}
	
	/**
	 * Gets the option box that allows the user to choose which reduction order to use.
	 * @return box
	 */
	public OptionBox reductionOrderBox() {
		return reductionOrder;
	}
	
	/**
	 * Gets the option box that allows the user to choose which output size to use.
	 * @return box
	 */
	public OptionBox outputSizeBox() {
		return outputSize;
	}
	
	/**
	 * Gets the button that can be used to play back to the previous displayed term.
	 * @return button
	 */
	public ImageButton stepBackwardButton() {
		return stepBackward;
	}
	
	/**
	 * Gets the button that can be used to initiate step by step reduction before execution.
	 * @return button
	 */
	public ImageButton stepByStepModeButton() {
		return stepByStepMode;
	}
	
	/**
	 * Gets the button that can be used to initiate the next reduction by the currently selected reduction order.
	 * @return button
	 */
	public ImageButton stepForwardButton() {
		return stepForward;
	}
	
	/**
	 * Gets the button that can be used to terminate the reduction.
	 * @return button
	 */
	public ImageButton terminateButton() {
		return terminate;
	}
	
	/**
	 * Gets the button that can be used to initiate the execution, automatically reducing the input with the given options
	 * @return button
	 */
	public ImageButton runButton() {
		return run;
	}
	
	/**
	 * Gets the button that can be used to transition from the automatic execution to the step by step mode.
	 * @return button
	 */
	public ImageButton pauseButton() {
		return pause;
	}
	
	/**
	 * Gets the output that displays terms as trees.
	 * @return output
	 */
	public TreeOutput treeOutput() {
		return treeOutput;
	}
	
	/**
	 * Gets the output that displays terms with unicode text.
	 * @return output
	 */
	public UnicodeOutput unicodeOutput() {
		return unicodeOutput;
	}
	
	/**
	 * Gets the button that can be used to open the menu that allows the user to choose an export format.
	 * @return button
	 */
	public ImageButton exportButton() {
		return export;
	}
	
	/**
	 * Gets the button that can be used to toggle the panel that displays the serialized URL.
	 * @return button
	 */
	public ImageButton shareButton() {
		return share;
	}
	
	/**
	 * Gets all checkboxes that can be used to enable libraries.
	 * @return library checkboxes
	 */
	public List<Checkbox> libraryBoxes() {
		return libraries;
	}
	
	/**
	 * Gets all buttons that can be used to load an exercise.
	 * @return exercise buttons
	 */
	public List<TextButton> exerciseButtons() {
		return exercises;
	}
	
	/**
	 * Gets all buttons that can be used to load the output into the export window with the given export format specified by the button.
	 * @return export format buttons
	 */
	public List<TextButton> exportFormatButtons() {
		return exportFormats;
	}
	
	/**
	 * Gets the wrapper that controls the reduction of lambda terms.
	 * @return Executor
	 */
	public Executor executor() {
		return executor;
	}
	
	// etc.
	
	/**
	 * Initializes App.
	 */
	private void initialize() {
		String state = Window.Location.getPath();
		// deserialize
		
		mainMenuButton = new ImageButton(new Button(), new Image(), new Image());
		editor = new Editor();
		//create ListBox for outputFormats
		outputFormat = new OptionBox(new ListBox());
		List<String> reductionOrders = ReductionOrders.all().stream().map(ReductionOrder::getName).collect(Collectors.toList());
		//create ListBox for reductionOrders
		reductionOrder = new OptionBox(new ListBox());
		List<String> outputSizes = OutputSizes.all().stream().map(OutputSize::getName).collect(Collectors.toList());
		//create ListBox for outputSizes
		outputSize = new OptionBox(new ListBox());
		stepBackward = new ImageButton(new Button(), new Image(), new Image());
		stepByStepMode = new ImageButton(new Button(), new Image(), new Image());
		stepForward = new ImageButton(new Button(), new Image(), new Image());
		terminate = new ImageButton(new Button(), new Image(), new Image());
		run = new ImageButton(new Button(), new Image(), new Image());
		pause = new ImageButton(new Button(), new Image(), new Image());
		export = new ImageButton(new Button(), new Image(), new Image());
		share = new ImageButton(new Button(), new Image(), new Image());
		libraries = Libraries.all().stream().map(l -> new Checkbox(new CheckBox(), l.getName())).collect(Collectors.toList());
		exercises = Exercises.all().stream().map(e -> new TextButton(new Button(), e.getName())).collect(Collectors.toList());
		exportFormats = Exports.all().stream().map(e -> new TextButton(new Button(), e.getName())).collect(Collectors.toList());
		executor = new Executor(Arrays.asList(new UpdateUnicodeOutput(), new UpdateTreeOutput()));
		run.setAction(new RunNewExecution());
	}

	@Override
	public String serialize() {
		return null;
	}
}
