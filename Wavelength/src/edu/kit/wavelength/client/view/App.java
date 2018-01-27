package edu.kit.wavelength.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.action.RunNewExecution;
import edu.kit.wavelength.client.view.action.SelectExercise;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.update.UpdateTreeOutput;
import edu.kit.wavelength.client.view.update.UpdateUnicodeOutput;
import edu.kit.wavelength.client.view.webui.component.Checkbox;
import edu.kit.wavelength.client.view.webui.component.DisclosureButton;
import edu.kit.wavelength.client.view.webui.component.Editor;
import edu.kit.wavelength.client.view.webui.component.ImageButton;
import edu.kit.wavelength.client.view.webui.component.OptionBox;
import edu.kit.wavelength.client.view.webui.component.PopUpWindow;
import edu.kit.wavelength.client.view.webui.component.TextButton;
import edu.kit.wavelength.client.view.webui.component.TextField;
import edu.kit.wavelength.client.view.webui.component.TreeOutput;
import edu.kit.wavelength.client.view.webui.component.UnicodeOutput;
import edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor;

/**
 * App is a singleton that initializes and holds the view.
 */
public class App implements Serializable {

	private static App instance = null;

	private App() {
	}

	/**
	 * Gets a singleton instance of App.
	 * 
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
	 * Name of the unicode format.
	 */
	public static final String UnicodeOutputName = "Unicode";
	/**
	 * Name of the tree format.
	 */
	public static final String TreeOutputName = "Tree";

	private DisclosureButton mainMenuButton;
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
	private List<Checkbox> libraries = new ArrayList<Checkbox>();
	private List<TextButton> exercises = new ArrayList<TextButton>();
	private List<TextButton> exportFormats = new ArrayList<TextButton>();
	private Executor executor;

	private TextButton showSolution;
	private TextButton hideSolution;
	private TextButton exitExerciseModeButton;
	private PopUpWindow enterExerciseMode;
	private PopUpWindow leaveExerciseMode;
	private TextField solutionPanel;
	private TextField taskPanel;

	// etc.

	/**
	 * Gets the panel that contains the URL to play back the state of the
	 * application.
	 * 
	 * @return The panel containing the share-URL
	 */
	public TextField sharePanel() {
		return this.sharePanel;
	}

	/**
	 * Gets the window that shows exported output.
	 * 
	 * @return The export window
	 */
	public PopUpWindow exportWindow() {
		return this.exportWindow;
	}

	/**
	 * Gets the button that is used to open the main menu.
	 * 
	 * @return The main menu button
	 */
	public DisclosureButton mainMenuButton() {
		return mainMenuButton;
	}

	/**
	 * Gets the editor.
	 * 
	 * @return The editor
	 */
	public Editor editor() {
		return editor;
	}

	/**
	 * Gets the option box that allows the user to choose which output format to
	 * use.
	 * 
	 * @return The output format box
	 */
	public OptionBox outputFormatBox() {
		return outputFormat;
	}

	/**
	 * Gets the option box that allows the user to choose which reduction order to
	 * use.
	 * 
	 * @return The reduction order option box
	 */
	public OptionBox reductionOrderBox() {
		return reductionOrder;
	}

	/**
	 * Gets the option box that allows the user to choose which output size to use.
	 * 
	 * @return The output size option box
	 */
	public OptionBox outputSizeBox() {
		return outputSize;
	}

	/**
	 * Gets the button that can be used to play back to the previous displayed term.
	 * 
	 * @return The step backward button
	 */
	public ImageButton stepBackwardButton() {
		return stepBackward;
	}

	/**
	 * Gets the button that can be used to initiate step by step reduction before
	 * execution.
	 * 
	 * @return The step-by-step button
	 */
	public ImageButton stepByStepModeButton() {
		return stepByStepMode;
	}

	/**
	 * Gets the button that can be used to initiate the next reduction by the
	 * currently selected reduction order.
	 * 
	 * @return The step forward button
	 */
	public ImageButton stepForwardButton() {
		return stepForward;
	}

	/**
	 * Gets the button that can be used to terminate the reduction.
	 * 
	 * @return The terminate button
	 */
	public ImageButton terminateButton() {
		return terminate;
	}

	/**
	 * Gets the button that can be used to initiate the execution, automatically
	 * reducing the input with the given options
	 * 
	 * @return The run button
	 */
	public ImageButton runButton() {
		return run;
	}

	/**
	 * Gets the button that can be used to transition from the automatic execution
	 * to the step by step mode.
	 * 
	 * @return The pause button
	 */
	public ImageButton pauseButton() {
		return pause;
	}

	/**
	 * Gets the output that displays terms as trees.
	 * 
	 * @return The output used to display a term in tree representation
	 */
	public TreeOutput treeOutput() {
		return treeOutput;
	}

	/**
	 * Gets the output that displays terms with unicode text.
	 * 
	 * @return The output used to display a term in unicode
	 */
	public UnicodeOutput unicodeOutput() {
		return unicodeOutput;
	}

	/**
	 * Gets the button that can be used to open the menu that allows the user to
	 * choose an export format.
	 * 
	 * @return The export button
	 */
	public ImageButton exportButton() {
		return export;
	}

	/**
	 * Gets the button that can be used to toggle the panel that displays the
	 * serialized URL.
	 * 
	 * @return The share button
	 */
	public ImageButton shareButton() {
		return share;
	}

	/**
	 * Gets all checkboxes that can be used to enable libraries.
	 * 
	 * @return The checkboxes used to toggle libraries
	 */
	public List<Checkbox> libraryBoxes() {
		return libraries;
	}

	/**
	 * Gets all buttons that can be used to load an exercise.
	 * 
	 * @return The buttons used to load exercises
	 */
	public List<TextButton> exerciseButtons() {
		return exercises;
	}

	/**
	 * Gets all buttons that can be used to load the output into the export window
	 * with the given export format specified by the button.
	 * 
	 * @return The buttons used to select an output format
	 */
	public List<TextButton> exportFormatButtons() {
		return exportFormats;
	}

	/**
	 * Gets the wrapper that controls the reduction of lambda terms.
	 * 
	 * @return The Executor instance controlling the execution
	 */
	public Executor executor() {
		return executor;
	}

	/**
	 * Gets the Button that can be used for showing the solution of the current
	 * {@link Exercise}.
	 * 
	 * @return The Button used for showing the current {@link Exercise}'s solution
	 */
	public TextButton showSolutionButton() {
		return this.showSolution;
	}

	/**
	 * Gets the Button that can be used for hiding the solution of the current
	 * {@link Exercise}.
	 * 
	 * @return The Button used for hiding the current {@link Exercise}'s solution
	 */
	public TextButton hideSolutionButton() {
		return this.hideSolution;
	}

	/**
	 * Gets the Button that can be used for exiting the exercise mode.
	 * 
	 * @return The Button used for exiting the exercise mode
	 */
	public TextButton exitExerciseModeButton() {
		return this.exitExerciseModeButton;
	}

	/**
	 * Gets the Dialog that is shown when entering exercise mode.
	 * 
	 * @return The Dialog shown when entering exercise mode
	 */
	public PopUpWindow enterExerciseMode() {
		return this.enterExerciseMode;
	}

	/**
	 * Gets the Dialog that is shown when leaving exercise mode.
	 * 
	 * @return The Dialog shown when leaving exercise mode
	 */
	public PopUpWindow leaveExerciseMode() {
		return this.leaveExerciseMode;
	}

	/**
	 * Gets the TextArea that can be used to show the current {@link Exercise}'s
	 * solution.
	 * 
	 * @return The TextArea showing the current {@link Exercise}'s solution
	 */
	public TextField solutionPanel() {
		return this.solutionPanel;
	}

	/**
	 * Gets the TextArea that can be used to show the current {@link Exercise}'s
	 * task.
	 * 
	 * @return The TextArea showing the current {@link Exercise}'s task
	 */
	public TextField taskPanel() {
		return this.taskPanel;
	}

	// etc.

	/**
	 * Initializes App.
	 */
	private void initialize() {

		String state = Window.Location.getPath();
		// deserialize

		DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.EM);

		DisclosurePanel mainMenu = new DisclosurePanel("Options");

		mainMenu.setAnimationEnabled(true);
		mainMenu.addStyleName("mainMenu");

		mainMenu.setHeader(new Label()); // label is just used to hide the arrow of the DisclosurePanel
		mainMenu.getElement().getStyle().setZIndex(Integer.MAX_VALUE);
		mainMenu.getHeader().addStyleName("fa fa-cog fa-2x");
		mainMenu.getHeader().addStyleName("mainMenuButton");

		FlowPanel mainMenuPanel = new FlowPanel();
		mainMenuPanel.addStyleName("mainMenuPanel");

		Label libraryTitle = new Label("Libraries");
		libraryTitle.addStyleName("menuTitle");
		mainMenuPanel.add(libraryTitle);
		Libraries.all().forEach(lib -> {
			String n = lib.getName();
			CheckBox libraryBox = new CheckBox(n);
			libraryBox.addStyleName("libraryCheckBox");
			mainMenuPanel.add(libraryBox);
			libraries.add(new Checkbox(libraryBox, n));
		});

		Label exerciseTitle = new Label("Exercises");
		exerciseTitle.addStyleName("menuTitle");
		mainMenuPanel.add(exerciseTitle);

		Exercises.all().forEach(excs -> {
			String n = excs.getName();
			Button exerciseButton = new Button(n);
			exerciseButton.addClickHandler(event -> new SelectExercise(excs).run());
			exerciseButton.setTitle(excs.getTask());
			mainMenuPanel.add(exerciseButton);
			this.exercises.add(new TextButton(exerciseButton, n));
		});

		mainMenu.setContent(mainMenuPanel);
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		FlowPanel footerPanel = new FlowPanel();
		mainPanel.addSouth(footerPanel, 2);

		SplitLayoutPanel ioPanel = new SplitLayoutPanel(3);
		mainPanel.add(ioPanel);

		DockLayoutPanel inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 400);

		TextArea outputArea = new TextArea();
		outputArea.setWidth("100%");
		ioPanel.add(outputArea);

		DockLayoutPanel inputControlPanel = new DockLayoutPanel(Unit.PCT);
		inputPanel.addSouth(inputControlPanel, 1.85);

		SimplePanel editorPanel = new SimplePanel();
		// id needed because MonacoEditor adds to panel div by id
		editorPanel.getElement().setId("editor");
		inputPanel.add(editorPanel);

		FlowPanel optionBarPanel = new FlowPanel();
		optionBarPanel.addStyleName("optionBarPanel");
		inputControlPanel.addWest(optionBarPanel, 50);

		ListBox outputFormatBox = new ListBox();
		outputFormatBox.addItem("Unicode Output");
		outputFormatBox.addItem("Tree Output");
		optionBarPanel.add(outputFormatBox);

		ListBox reductionBox = new ListBox();
		reductionBox.setName("Reduction Order");
		ReductionOrders.all().stream().map(ReductionOrder::getName).forEach(reductionBox::addItem);
		optionBarPanel.add(reductionBox);

		ListBox outputSizeBox = new ListBox();
		outputSizeBox.setName("Output Size");
		OutputSizes.all().stream().map(OutputSize::getName).forEach(outputSizeBox::addItem);
		optionBarPanel.add(outputSizeBox);

		FlowPanel controlPanel = new FlowPanel();
		controlPanel.addStyleName("controlPanel");
		inputControlPanel.addEast(controlPanel, 50);

		Button backwardsButton = new Button();
		backwardsButton.addStyleName("fa fa-chevron-left");
		controlPanel.add(backwardsButton);

		Button stepByStepButton = new Button();
		stepByStepButton.addStyleName("fa fa-exchange");
		controlPanel.add(stepByStepButton);

		Button forwardButton = new Button();
		forwardButton.addStyleName("fa fa-chevron-right");
		controlPanel.add(forwardButton);

		Button cancelButton = new Button();
		cancelButton.addStyleName("fa fa-stop");
		controlPanel.add(cancelButton);

		Button runButton = new Button();
		runButton.addStyleName("fa fa-play");
		controlPanel.add(runButton);

		Button exportButton = new Button();
		exportButton.addStyleName("fa fa-level-up");
		footerPanel.add(exportButton);

		Button shareButton = new Button();
		shareButton.addStyleName("fa fa-share-alt");
		footerPanel.add(shareButton);

		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		MonacoEditor me = MonacoEditor.load(editorPanel);

		stepBackward = new ImageButton(new PushButton(), new Image(), new Image());
		stepByStepMode = new ImageButton(new PushButton(), new Image(), new Image());
		stepForward = new ImageButton(new PushButton(), new Image(), new Image());
		terminate = new ImageButton(new PushButton(), new Image(), new Image());
		run = new ImageButton(new PushButton(), new Image(), new Image());
		pause = new ImageButton(new PushButton(), new Image(), new Image());
		export = new ImageButton(new PushButton(), new Image(), new Image());
		share = new ImageButton(new PushButton(), new Image(), new Image());
		exportFormats = Exports.all().stream().map(e -> new TextButton(new Button(), e.getName()))
				.collect(Collectors.toList());
		executor = new Executor(Arrays.asList(new UpdateUnicodeOutput(), new UpdateTreeOutput()));
		run.setAction(new RunNewExecution());
	}

	@Override
	public String serialize() {
		return null;
	}
}
