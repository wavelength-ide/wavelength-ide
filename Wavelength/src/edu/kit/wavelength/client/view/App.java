package edu.kit.wavelength.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.action.SelectExercise;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.gwtbinding.MonacoEditor;

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

	private Button mainMenuButton;
	private Panel editor;
	
	private ListBox outputFormat;
	private ListBox reductionOrder;
	private ListBox outputSize;
	private Button stepBackward;
	private Button stepByStepMode;
	private Button stepForward;
	private Button terminate;
	private Button run;
	private Button pause;
	private Panel treeOutput;
	private Panel unicodeOutput;
	private Button export;
	private Panel exportWindow;
	private Button share;
	private TextArea sharePanel;
	private List<CheckBox> libraries;
	private List<Button> exercises;
	private List<Button> exportFormats;
	private Executor executor;

	private Button showSolution;
	private Button hideSolution;
	private Button exitExerciseModeButton;
	private Panel enterExerciseMode;
	private Panel leaveExerciseMode;
	private TextArea solutionPanel;
	private Label taskPanel;

	// etc.

	/**
	 * Gets the panel that contains the URL to play back the state of the
	 * application.
	 * 
	 * @return The panel containing the share-URL
	 */
	public TextArea sharePanel() {
		return this.sharePanel;
	}

	/**
	 * Gets the window that shows exported output.
	 * 
	 * @return The export window
	 */
	public Panel exportWindow() {
		return this.exportWindow;
	}

	/**
	 * Gets the button that is used to open the main menu.
	 * 
	 * @return The main menu button
	 */
	public Button mainMenuButton() {
		return mainMenuButton;
	}

	/**
	 * Gets the editor.
	 * 
	 * @return The editor
	 */
	public Panel editor() {
		return editor;
	}

	/**
	 * Gets the option box that allows the user to choose which output format to
	 * use.
	 * 
	 * @return The output format box
	 */
	public ListBox outputFormatBox() {
		return outputFormat;
	}

	/**
	 * Gets the option box that allows the user to choose which reduction order to
	 * use.
	 * 
	 * @return The reduction order option box
	 */
	public ListBox reductionOrderBox() {
		return reductionOrder;
	}

	/**
	 * Gets the option box that allows the user to choose which output size to use.
	 * 
	 * @return The output size option box
	 */
	public ListBox outputSizeBox() {
		return outputSize;
	}

	/**
	 * Gets the button that can be used to play back to the previous displayed term.
	 * 
	 * @return The step backward button
	 */
	public Button stepBackwardButton() {
		return stepBackward;
	}

	/**
	 * Gets the button that can be used to initiate step by step reduction before
	 * execution.
	 * 
	 * @return The step-by-step button
	 */
	public Button stepByStepModeButton() {
		return stepByStepMode;
	}

	/**
	 * Gets the button that can be used to initiate the next reduction by the
	 * currently selected reduction order.
	 * 
	 * @return The step forward button
	 */
	public Button stepForwardButton() {
		return stepForward;
	}

	/**
	 * Gets the button that can be used to terminate the reduction.
	 * 
	 * @return The terminate button
	 */
	public Button terminateButton() {
		return terminate;
	}

	/**
	 * Gets the button that can be used to initiate the execution, automatically
	 * reducing the input with the given options.
	 * 
	 * @return The run button
	 */
	public Button runButton() {
		return run;
	}

	/**
	 * Gets the button that can be used to transition from the automatic
	 * execution to the step by step mode.
	 * 
	 * @return The pause button
	 */
	public Button pauseButton() {
		return pause;
	}

	/**
	 * Gets the output that displays terms as trees.
	 * 
	 * @return The output used to display a term in tree representation
	 */
	public Panel treeOutput() {
		return treeOutput;
	}

	/**
	 * Gets the output that displays terms with unicode text.
	 * 
	 * @return The output used to display a term in unicode
	 */
	public Panel unicodeOutput() {
		return unicodeOutput;
	}

	/**
	 * Gets the button that can be used to open the menu that allows the user to
	 * choose an export format.
	 * 
	 * @return The export button
	 */
	public Button exportButton() {
		return export;
	}

	/**
	 * Gets the button that can be used to toggle the panel that displays the
	 * serialized URL.
	 * 
	 * @return The share button
	 */
	public Button shareButton() {
		return share;
	}

	/**
	 * Gets all checkboxes that can be used to enable libraries.
	 * 
	 * @return The checkboxes used to toggle libraries
	 */
	public List<CheckBox> libraryBoxes() {
		return libraries;
	}

	/**
	 * Gets all buttons that can be used to load an exercise.
	 * 
	 * @return The buttons used to load exercises
	 */
	public List<Button> exerciseButtons() {
		return exercises;
	}

	/**
	 * Gets all buttons that can be used to load the output into the export
	 * window with the given export format specified by the button.
	 * 
	 * @return The buttons used to select an output format
	 */
	public List<Button> exportFormatButtons() {
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
	public Button showSolutionButton() {
		return this.showSolution;
	}

	/**
	 * Gets the Button that can be used for hiding the solution of the current
	 * {@link Exercise}.
	 * 
	 * @return The Button used for hiding the current {@link Exercise}'s solution
	 */
	public Button hideSolutionButton() {
		return this.hideSolution;
	}

	/**
	 * Gets the Button that can be used for exiting the exercise mode.
	 * 
	 * @return The Button used for exiting the exercise mode
	 */
	public Button exitExerciseModeButton() {
		return this.exitExerciseModeButton;
	}

	/**
	 * Gets the Dialog that is shown when entering exercise mode.
	 * 
	 * @return The Dialog shown when entering exercise mode
	 */
	public Panel enterExerciseMode() {
		return this.enterExerciseMode;
	}

	/**
	 * Gets the Dialog that is shown when leaving exercise mode.
	 * 
	 * @return The Dialog shown when leaving exercise mode
	 */
	public Panel leaveExerciseMode() {
		return this.leaveExerciseMode;
	}

	/**
	 * Gets the TextArea that can be used to show the current {@link Exercise}'s
	 * solution.
	 * 
	 * @return The TextArea showing the current {@link Exercise}'s solution
	 */
	public TextArea solutionPanel() {
		return this.solutionPanel;
	}

	/**
	 * Gets the Label that can be used to show the current {@link Exercise}'s
	 * task.
	 * 
	 * @return The Label showing the current {@link Exercise}'s task
	 */
	public Label taskPanel() {
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
		mainPanel.addStyleName("mainPanel");

		DisclosurePanel mainMenu = new DisclosurePanel("Options");
		mainMenu.setAnimationEnabled(true);
		mainPanel.addDomHandler(event -> {
			if (mainMenu.isOpen()) {
				mainMenu.setOpen(false);
			}
		}, MouseDownEvent.getType());
		mainMenu.addDomHandler(event -> event.stopPropagation(), MouseDownEvent.getType());
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
			//libraries.add(new Checkbox(libraryBox, n));
		});

		Label exerciseTitle = new Label("Exercises");
		exerciseTitle.addStyleName("menuTitle");
		mainMenuPanel.add(exerciseTitle);

		Modal infoPopup = new Modal();
		infoPopup.setClosable(false);
		infoPopup.setFade(true);
		infoPopup.setDataBackdrop(ModalBackdrop.STATIC);
		
		ModalBody infoPopupBody = new ModalBody();
		infoPopup.add(infoPopupBody);
		
		Label infoPopupText = new Label("hello world");
		infoPopupBody.add(infoPopupText);
		
		ModalFooter infoPopupFooter = new ModalFooter();
		infoPopup.add(infoPopupFooter);
		
		Button infoPopupOkButton = new Button();
		infoPopupOkButton.addStyleName("btn btn-default");
		infoPopupOkButton.addStyleName("fa fa-times");
		infoPopupFooter.add(infoPopupOkButton);
		
		Button infoPopupCancelButton = new Button();
		infoPopupCancelButton.addStyleName("btn btn-default");
		infoPopupCancelButton.addStyleName("fa fa-check");
		infoPopupFooter.add(infoPopupCancelButton);
		
		// infoPopup.show();
		
		Exercises.all().forEach(excs -> {
			String n = excs.getName();
			Button exerciseButton = new Button(n);
			exerciseButton.addClickHandler(event -> new SelectExercise(excs).run());
			exerciseButton.addStyleName("exerciseButton");
			exerciseButton.addStyleName("btn btn-default");
			exerciseButton.setTitle(excs.getTask());
			mainMenuPanel.add(exerciseButton);
			//this.exercises.add(new TextButton(exerciseButton, n));
		});
		
		mainMenu.setContent(mainMenuPanel);
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		FlowPanel footerPanel = new FlowPanel();
		footerPanel.addStyleName("footerPanel");
		mainPanel.addSouth(footerPanel, 2);
		// hack to display dropup on top of rest of ui
		footerPanel.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		SplitLayoutPanel ioPanel = new SplitLayoutPanel(3);
		mainPanel.add(ioPanel);

		DockLayoutPanel inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 400);

		TextArea outputArea = new TextArea();
		outputArea.setWidth("100%");
		ioPanel.add(outputArea);

		DockLayoutPanel inputControlPanel = new DockLayoutPanel(Unit.PCT);
		inputPanel.addSouth(inputControlPanel, 1.85);

		SplitLayoutPanel editorTaskPanel = new SplitLayoutPanel(3);
		editorTaskPanel.addStyleName("editorTaskPanel");
		inputPanel.add(editorTaskPanel);
		
		FlowPanel taskPanel = new FlowPanel();
		taskPanel.addStyleName("taskPanel");
		editorTaskPanel.addEast(taskPanel, 400);
		
		FlowPanel taskHeaderPanel = new FlowPanel();
		taskPanel.add(taskHeaderPanel);
		
		FlowPanel taskControlPanel = new FlowPanel();
		taskControlPanel.addStyleName("taskControlPanel");
		taskHeaderPanel.add(taskControlPanel);
		
		Label taskDescriptionLabel = new HTML("hello world<br>hello world<br>hello world<br>");
		taskDescriptionLabel.addStyleName("taskDescriptionLabel");
		taskHeaderPanel.add(taskDescriptionLabel);
		
		Button toggleSolutionButton = new Button();
		toggleSolutionButton.addStyleName("fa fa-lightbulb-o");
		toggleSolutionButton.addStyleName("btn btn-default");
		taskControlPanel.add(toggleSolutionButton);
		
		Button closeTaskButton = new Button();
		closeTaskButton.addStyleName("fa fa-times-circle-o");
		closeTaskButton.addStyleName("btn btn-default");
		taskControlPanel.add(closeTaskButton);
		
		TextArea solutionArea = new TextArea();
		solutionArea.addStyleName("solutionArea");
		solutionArea.addStyleName("form-control");
		solutionArea.setVisible(false);
		solutionArea.setReadOnly(true);
		solutionArea.setText("hello\n\tworld\n\t\teveryone");
		toggleSolutionButton.addClickHandler(e -> solutionArea.setVisible(!solutionArea.isVisible()));
		taskPanel.add(solutionArea);
		
		//editorTaskPanel.setWidgetHidden(taskPanel, true);
		
		SimplePanel editorPanel = new SimplePanel();
		// id needed because MonacoEditor adds to panel div by id
		editorPanel.getElement().setId("editor");
		editorTaskPanel.add(editorPanel);

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

		FlowPanel stepByStepControlPanel = new FlowPanel();
		stepByStepControlPanel.addStyleName("stepByStepControlPanel");
		controlPanel.add(stepByStepControlPanel);
		
		Button backwardsButton = new Button();
		backwardsButton.addStyleName("fa fa-chevron-left");
		backwardsButton.addStyleName("btn btn-default");
		stepByStepControlPanel.add(backwardsButton);

		Button stepByStepButton = new Button();
		stepByStepButton.addStyleName("fa fa-exchange");
		stepByStepButton.addStyleName("btn btn-default");
		stepByStepControlPanel.add(stepByStepButton);

		Button forwardButton = new Button();
		forwardButton.addStyleName("fa fa-chevron-right");
		forwardButton.addStyleName("btn btn-default");
		stepByStepControlPanel.add(forwardButton);

		FlowPanel runControlPanel = new FlowPanel();
		runControlPanel.addStyleName("runControlPanel");
		controlPanel.add(runControlPanel);
		
		Button cancelButton = new Button();
		cancelButton.addStyleName("fa fa-stop");
		cancelButton.addStyleName("btn btn-default");
		runControlPanel.add(cancelButton);
		
		Button runButton = new Button();
		runButton.addStyleName("fa fa-play");
		runButton.addStyleName("btn btn-default");
		runControlPanel.add(runButton);
		
		Button pauseButton = new Button();
		pauseButton.addStyleName("fa fa-pause");
		pauseButton.addStyleName("btn btn-default");
		runControlPanel.add(pauseButton);

		ButtonGroup exportDropupGroup = new ButtonGroup();
		exportDropupGroup.setDropUp(true);
		footerPanel.add(exportDropupGroup);
		
		org.gwtbootstrap3.client.ui.Button exportButton = new org.gwtbootstrap3.client.ui.Button();
		exportButton.setDataToggle(Toggle.DROPDOWN);
		exportButton.setToggleCaret(false);
		exportButton.addStyleName("fa fa-level-up");
		exportDropupGroup.add(exportButton);
		
		DropDownMenu exportMenu = new DropDownMenu();
		Exports.all().forEach(e -> {
			exportMenu.add(new AnchorListItem(e.getName()));
		});
		exportDropupGroup.add(exportMenu);
		
		// this only exists for style consistency with exportButton
		ButtonGroup shareGroup = new ButtonGroup();
		footerPanel.add(shareGroup);
		
		TextBox sharePanel = new TextBox();
		sharePanel.setText("hello world");
		sharePanel.setReadOnly(true);
		sharePanel.setVisible(false);
		footerPanel.add(sharePanel);
		
		Button shareButton = new Button();
		shareButton.addClickHandler(event -> sharePanel.setVisible(!sharePanel.isVisible()));
		shareButton.addStyleName("fa fa-share-alt");
		shareButton.addStyleName("btn btn-default");
		shareGroup.add(shareButton);
		
		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		MonacoEditor editor = MonacoEditor.load(editorPanel);
	}

	@Override
	public String serialize() {
		return null;
	}
}
