package edu.kit.wavelength.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.Divider;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.DropDownHeader;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

import edu.kit.wavelength.client.database.DatabaseService;
import edu.kit.wavelength.client.database.DatabaseServiceAsync;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.view.action.Clear;
import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.action.EnterDefaultMode;
import edu.kit.wavelength.client.view.action.LoadExercise;
import edu.kit.wavelength.client.view.action.Pause;
import edu.kit.wavelength.client.view.action.RunExecution;
import edu.kit.wavelength.client.view.action.SelectExercise;
import edu.kit.wavelength.client.view.action.SelectExportFormat;
import edu.kit.wavelength.client.view.action.SetOutputFormat;
import edu.kit.wavelength.client.view.action.SetOutputSize;
import edu.kit.wavelength.client.view.action.SetReductionOrder;
import edu.kit.wavelength.client.view.action.StepBackward;
import edu.kit.wavelength.client.view.action.StepForward;
import edu.kit.wavelength.client.view.action.Unpause;
import edu.kit.wavelength.client.view.action.UseShare;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Export;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.gwt.MonacoEditor;
import edu.kit.wavelength.client.view.update.FinishExecution;
import edu.kit.wavelength.client.view.update.UpdateOutput;
import edu.kit.wavelength.client.view.update.UpdateShareURL;

/**
 * App is a singleton that initializes and holds the view.
 */
public class App implements Serializable {

	private static App instance = null;
	
	private static final int EXECUTOR_SERIALIZATION = 0;
	private static final int EDITOR_SERIALIZATION = 1;
	private static final int OUTPUTFORMAT_SERIALIZATION = 2;
	private static final int REDUCTIONORDER_SERIALIZATION = 3;
	private static final int OUTPUTSIZE_SERIALIZATION = 4;
	private static final int LIBRARY_SERIALIZATION = 5;
	private static final int EXERCISE_SERIALIZATION = 6;
	private static final int NUMBER_OF_SERIALIZATIONS = 7;
	private static final char CHECKED_LIBRARY = 'c';
	private static final char UNCHECKED_LIBRARY = 'u';
	private static final int POLLING_DELAY_MS = 10000;
	
	public static final String KEY = "s";

	/**
	 * Creates a new instance of App if there is none. Returns a singleton instance
	 * of App.
	 *
	 * @return singleton instance of App
	 */
	public static App get() {
		if (instance == null) {
			instance = new App();
			instance.initialize();
		}
		return instance;
	}

	private DockLayoutPanel mainPanel;
	private DropDown mainMenu;
	private Button openMainMenuButton;
	private DropDownMenu mainMenuPanel;
	private DropDownHeader mainMenuLibraryTitle;
	private List<CheckBox> libraryCheckBoxes;
	private Divider mainMenuDivider;
	private DropDownHeader mainMenuExerciseTitle;
	private List<AnchorListItem> exerciseButtons;
	private FlowPanel footerPanel;
	private ButtonGroup exportDropupGroup;
	private Button openExportMenuButton;
	private DropDownMenu exportMenu;
	private List<AnchorListItem> exportButtons;
	private ButtonGroup shareGroup;
	private Button shareButton;
	private TextBox sharePanel;
	private SplitLayoutPanel ioPanel;
	private DockLayoutPanel inputPanel;
	private FlowPanel inputControlPanel;
	private FlowPanel optionBarPanel;
	private ListBox outputFormatBox;
	private ListBox reductionOrderBox;
	private ListBox outputSizeBox;
	private FlowPanel controlPanel;
	private FlowPanel stepByStepControlPanel;
	private Button backwardButton;
	private Button pauseButton;
	private Button unpauseButton;
	private Button forwardButton;
	private Label spinner;
	private FlowPanel runControlPanel;
	private Button clearButton;
	private Button runButton;
	private SplitLayoutPanel editorExercisePanel;
	private FlowPanel exercisePanel;
	private FlowPanel exerciseHeaderPanel;
	private FlowPanel exerciseControlPanel;
	private Label exerciseDescriptionLabel;
	private Button toggleSolutionButton;
	private Button closeExerciseButton;
	private TextArea solutionArea;
	private SimplePanel editorPanel;
	private FlowPanel outputBlocker;
	private FlowPanel outputArea;
	private Modal loadExercisePopup;
	private ModalBody loadExercisePopupBody;
	private Label loadExercisePopupText;
	private ModalFooter loadExercisePopupFooter;
	private Button loadExercisePopupOkButton;
	private Button loadExercisePopupCancelButton;
	private Modal closeExercisePopup;
	private ModalBody closeExercisePopupBody;
	private Label closeExercisePopupText;
	private ModalFooter closeExercisePopupFooter;
	private Button closeExercisePopupOkButton;
	private Button closeExercisePopupCancelButton;
	private Modal exportPopup;
	private ModalBody exportPopupBody;
	private TextArea exportArea;
	private ModalFooter exportPopupFooter;
	private Button exportPopupOkButton;
	
	private MonacoEditor editor;
	
	private Executor executor;
	
	private Exercise currentExercise;

	private App() {

	}

	/**
	 * Initializes App.
	 */
	private void initialize() {
		/**
		 * Structure:
		 * mainPanel
		 * 		mainMenu
		 * 			openMainMenuButton
		 * 			mainMenuPanel
		 * 				mainMenuLibraryTitle
		 * 				libraryCheckBoxes
		 * 				mainMenuDivider
		 * 				mainMenuExerciseTitle
		 * 				exerciseButtons
		 * 		footerPanel
		 *			exportDropupGroup
		 *				openExportMenuButton
		 *				exportMenu
		 *					exportButtons
		 *			shareGroup
		 *				shareButton
 		 *			sharePanel	
		 *		ioPanel
		 *			inputPanel
		 *				inputControlPanel
		 *					optionBarPanel
		 *						outputFormatBox
		 *						reductionOrderBox
		 *						outputSizeBox
		 *					controlPanel
		 *						stepByStepControlPanel
		 *							backwardButton
		 *							pauseButton
		 *							unpauseButton
		 *							forwardButton
		 *						spinner
		 *						runControlPanel
		 *							terminateButton
		 *							runButton
		 *				editorExercisePanel
		 *					exercisePanel
		 *						exerciseHeaderPanel
		 *							exerciseControlPanel
		 *								toggleSolutionButton
		 *								closeExerciseButton
		 *							exerciseDescriptionLabel
		 *						solutionArea
		 *					editorPanel
		 *			outputBlocker
		 *				outputArea
		 * loadExercisePopup
		 *		loadExercisePopupBody
		 *			loadExercisePopupText
		 *		loadExercisePopupFooter
		 *			loadExercisePopupCancelButton
		 *			loadExercisePopupOkButton
		 * closeExercisePopup
		 *		closeExercisePopupBody
		 *			closeExercisePopupText
		 *		closeExercisePopupFooter
		 *			closeExercisePopupCancelButton
		 *			closeExercisePopupOkButton
		 * exportPopup
		 *		exportPopupBody
		 *			exportArea
		 *		exportPopupFooter
		 *			exportPopupBodyOkButton
		 */
		
		mainPanel = new DockLayoutPanel(Unit.EM);
		mainPanel.getElement().setId("mainPanel");
		mainPanel.addStyleName("mainPanel");

		mainMenu = new DropDown();
		mainMenu.setId("mainMenu");
		mainMenu.addStyleName("mainMenu");
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		openMainMenuButton = new Button();
		openMainMenuButton.setTitle("Open/Close the main menu.");
		openMainMenuButton.setId("openMainMenuButton");
		openMainMenuButton.addStyleName("fa fa-bars");
		openMainMenuButton.setToggleCaret(false);
		openMainMenuButton.setDataToggle(Toggle.DROPDOWN);
		mainMenu.add(openMainMenuButton);

		mainMenuPanel = new DropDownMenu();
		mainMenuPanel.setId("mainMenuPanel");
		mainMenuPanel.addStyleName("mainMenuPanel");
		// prevent dropdown from closing when clicking inside
		mainMenuPanel.addDomHandler(event -> event.stopPropagation(), ClickEvent.getType());
		mainMenu.add(mainMenuPanel);

		mainMenuLibraryTitle = new DropDownHeader("Libraries");
		mainMenuLibraryTitle.getElement().setId("mainMenuLibraryTitle");
		mainMenuPanel.add(mainMenuLibraryTitle);

		libraryCheckBoxes = new ArrayList<>();
		Libraries.all().forEach(lib -> {
			CheckBox libraryCheckBox = new CheckBox(lib.getName());
			libraryCheckBox.addStyleName("libraryCheckBox");
			mainMenuPanel.add(libraryCheckBox);
			libraryCheckBoxes.add(libraryCheckBox);
		});

		mainMenuDivider = new Divider();
		mainMenuDivider.getElement().setId("mainMenuDivider");
		mainMenuPanel.add(mainMenuDivider);

		mainMenuExerciseTitle = new DropDownHeader("Exercises");
		mainMenuExerciseTitle.getElement().setId("mainMenuExerciseTitle");
		mainMenuPanel.add(mainMenuExerciseTitle);

		exerciseButtons = new ArrayList<>();
		Exercises.all().forEach(excs -> {
			AnchorListItem exerciseButton = new AnchorListItem();
			exerciseButton.setText(excs.getName());
			mainMenuPanel.add(exerciseButton);
			exerciseButtons.add(exerciseButton);
		});

		footerPanel = new FlowPanel();
		footerPanel.getElement().setId("footerPanel");
		footerPanel.addStyleName("footerPanel");
		// addSouth needs to be called before add, otherwise gwt will stop working => footerPanel before ioPanel
		mainPanel.addSouth(footerPanel, 2);
		// hack to display dropup on top of rest of ui
		footerPanel.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		exportDropupGroup = new ButtonGroup();
		exportDropupGroup.setId("exportDropupGroup");
		exportDropupGroup.setDropUp(true);
		footerPanel.add(exportDropupGroup);

		openExportMenuButton = new Button();
		openExportMenuButton.setTitle("Export the current autput.");
		openExportMenuButton.setId("openExportMenuButton");
		openExportMenuButton.setDataToggle(Toggle.DROPDOWN);
		openExportMenuButton.setToggleCaret(false);
		openExportMenuButton.addStyleName("fa fa-level-up");
		exportDropupGroup.add(openExportMenuButton);

		exportMenu = new DropDownMenu();
		exportMenu.setId("exportMenu");
		exportDropupGroup.add(exportMenu);
		
		exportButtons = new ArrayList<>();
		Exports.all().forEach(e -> {
			AnchorListItem exportButton = new AnchorListItem();
			exportButton.setText(e.getName());
			exportButton.setEnabled(false);
			exportMenu.add(exportButton);
			exportButtons.add(exportButton);
		});

		// this only exists for style consistency with exportButton
		shareGroup = new ButtonGroup();
		shareGroup.setId("shareGroup");
		footerPanel.add(shareGroup);

		shareButton = new Button();
		shareButton.setTitle("Create a permalink.");
		shareButton.setId("shareButton");
		shareButton.addStyleName("fa fa-share-alt");
		shareGroup.add(shareButton);

		sharePanel = new TextBox();
		sharePanel.setId("sharePanel");
		sharePanel.addStyleName("sharePanel");
		sharePanel.setReadOnly(true);
		sharePanel.setVisible(false);
		footerPanel.add(sharePanel);
		
		ioPanel = new SplitLayoutPanel(3);
		ioPanel.getElement().setId("ioPanel");
		mainPanel.add(ioPanel);

		inputPanel = new DockLayoutPanel(Unit.EM);
		inputPanel.getElement().setId("inputPanel");
		ioPanel.addNorth(inputPanel, 0.45 * Window.getClientHeight());

		inputControlPanel = new FlowPanel();
		inputControlPanel.getElement().setId("inputControlPanel");
		inputControlPanel.addStyleName("inputControlPanel");
		// addSouth needs to be called before add, otherwise gwt will stop working => inputControlPanel before editorExercisePanel
		inputPanel.addSouth(inputControlPanel, 1.85);

		optionBarPanel = new FlowPanel();
		optionBarPanel.getElement().setId("optionBarPanel");
		optionBarPanel.addStyleName("optionBarPanel");
		inputControlPanel.add(optionBarPanel);

		outputFormatBox = new ListBox();
		outputFormatBox.setId("outputFormatBox");
		outputFormatBox.addItem("Unicode Output");
		outputFormatBox.addItem("Tree Output");
		optionBarPanel.add(outputFormatBox);

		reductionOrderBox = new ListBox();
		reductionOrderBox.setId("reductionOrderBox");
		ReductionOrders.all().stream().map(ReductionOrder::getName).forEach(reductionOrderBox::addItem);
		optionBarPanel.add(reductionOrderBox);

		outputSizeBox = new ListBox();
		outputSizeBox.setId("outputSizeBox");
		OutputSizes.all().stream().map(OutputSize::getName).forEach(outputSizeBox::addItem);
		optionBarPanel.add(outputSizeBox);

		controlPanel = new FlowPanel();
		controlPanel.getElement().setId("controlPanel");
		controlPanel.addStyleName("controlPanel");
		inputControlPanel.add(controlPanel);

		stepByStepControlPanel = new FlowPanel();
		stepByStepControlPanel.getElement().setId("stepByStepControlPanel");
		controlPanel.add(stepByStepControlPanel);

		backwardButton = new Button();
		backwardButton.setTitle("Revert the last reduction step.");
		backwardButton.setId("backwardButton");
		backwardButton.addStyleName("fa fa-chevron-left");
		stepByStepControlPanel.add(backwardButton);
		
		pauseButton = new Button();
		pauseButton.setTitle("Pause the currently running reduction.");
		pauseButton.setId("pauseButton");
		pauseButton.addStyleName("fa fa-pause");
		stepByStepControlPanel.add(pauseButton);
		
		unpauseButton = new Button();
		unpauseButton.setTitle("Resume current reduction.");
		unpauseButton.setId("unpauseButton");
		unpauseButton.addStyleName("fa fa-play");
		stepByStepControlPanel.add(unpauseButton);
		
		forwardButton = new Button();
		forwardButton.setTitle("Do a single reduction step.");
		forwardButton.setId("forwardButton");
		forwardButton.addStyleName("fa fa-chevron-right");
		stepByStepControlPanel.add(forwardButton);
		
		spinner = new Label();
		spinner.getElement().setId("spinner");
		spinner.addStyleName("spinner");
		spinner.addStyleName("fa fa-spinner fa-spin");
		controlPanel.add(spinner);
		
		runControlPanel = new FlowPanel();
		runControlPanel.getElement().setId("runControlPanel");
		controlPanel.add(runControlPanel);
		
		clearButton = new Button();
		clearButton.setTitle("Clear the output.");
		clearButton.setId("clearButton");
		clearButton.addStyleName("fa fa-times");
		runControlPanel.add(clearButton);
		
		runButton = new Button();
		runButton.setTitle("Run a new reduction.");
		runButton.setId("runButton");
		runButton.addStyleName("fa fa-fast-forward");
		runControlPanel.add(runButton);
		
		editorExercisePanel = new SplitLayoutPanel(3);
		editorExercisePanel.getElement().setId("editorExercisePanel");
		editorExercisePanel.addStyleName("editorExercisePanel");
		inputPanel.add(editorExercisePanel);

		exercisePanel = new FlowPanel();
		exercisePanel.getElement().setId("exercisePanel");
		exercisePanel.addStyleName("exercisePanel");
		// addEast needs to be called before add, otherwise gwt will stop working => exercisePanel before editorPanel
		editorExercisePanel.addEast(exercisePanel, 0.3 * Window.getClientWidth());
		editorExercisePanel.setWidgetHidden(exercisePanel, true);

		exerciseHeaderPanel = new FlowPanel();
		exerciseHeaderPanel.getElement().setId("exerciseHeaderPanel");
		exercisePanel.add(exerciseHeaderPanel);

		exerciseControlPanel = new FlowPanel();
		exerciseControlPanel.getElement().setId("exerciseControlPanel");
		exerciseControlPanel.addStyleName("exerciseControlPanel");
		exerciseHeaderPanel.add(exerciseControlPanel);

		toggleSolutionButton = new Button();
		toggleSolutionButton.setTitle("Show/Hide the current exercise's solution.");
		toggleSolutionButton.setId("toggleSolutionButton");
		toggleSolutionButton.addStyleName("fa fa-lightbulb-o");
		exerciseControlPanel.add(toggleSolutionButton);

		closeExerciseButton = new Button();
		closeExerciseButton.setTitle("Leave the exercise mode.");
		closeExerciseButton.setId("closeExerciseButton");
		closeExerciseButton.addStyleName("fa fa-times-circle-o");
		exerciseControlPanel.add(closeExerciseButton);

		exerciseDescriptionLabel = new HTML();
		exerciseDescriptionLabel.getElement().setId("exerciseDescriptionLabel");
		exerciseDescriptionLabel.addStyleName("exerciseDescriptionLabel");
		exerciseHeaderPanel.add(exerciseDescriptionLabel);
		
		solutionArea = new TextArea();
		solutionArea.setId("solutionArea");
		solutionArea.addStyleName("solutionArea");
		solutionArea.setVisible(false);
		solutionArea.setReadOnly(true);
		exercisePanel.add(solutionArea);

		editorPanel = new SimplePanel();
		editorPanel.getElement().setId("editor");
		editorExercisePanel.add(editorPanel);
		
		outputBlocker = new FlowPanel("div");
		outputBlocker.getElement().setId("outputBlocker");
		outputBlocker.addStyleName("output");
		outputBlocker.getElement().setId("scroll");
		ioPanel.add(outputBlocker);
		
		outputArea = new FlowPanel("div");
		outputArea.getElement().setId("network");
		outputArea.addStyleName("output");
		outputBlocker.add(outputArea);
		
		loadExercisePopup = new Modal();
		loadExercisePopup.setId("loadExercisePopup");
		loadExercisePopup.setClosable(false);
		loadExercisePopup.setDataBackdrop(ModalBackdrop.STATIC);
		loadExercisePopup.setDataKeyboard(true);

		loadExercisePopupBody = new ModalBody();
		loadExercisePopupBody.setId("loadExercisePopupBody");
		loadExercisePopup.add(loadExercisePopupBody);

		loadExercisePopupText = new Label("Opening the exercise will empty your editor content. Continue?");
		loadExercisePopupText.getElement().setId("loadExercisePopupText");
		loadExercisePopupBody.add(loadExercisePopupText);

		loadExercisePopupFooter = new ModalFooter();
		loadExercisePopupFooter.setId("loadExercisePopupFooter");
		loadExercisePopup.add(loadExercisePopupFooter);

		loadExercisePopupCancelButton = new Button();
		loadExercisePopupCancelButton.setId("loadExercisePopupCancelButton");
		loadExercisePopupCancelButton.addStyleName("fa fa-times");
		loadExercisePopupFooter.add(loadExercisePopupCancelButton);

		loadExercisePopupOkButton = new Button();
		loadExercisePopupOkButton.setId("loadExercisePopupOkButton");
		loadExercisePopupOkButton.addStyleName("fa fa-check");
		loadExercisePopupFooter.add(loadExercisePopupOkButton);

		closeExercisePopup = new Modal();
		closeExercisePopup.setId("closeExercisePopup");
		closeExercisePopup.setClosable(false);
		closeExercisePopup.setDataBackdrop(ModalBackdrop.STATIC);
		closeExercisePopup.setDataKeyboard(true);

		closeExercisePopupBody = new ModalBody();
		closeExercisePopupBody.setId("closeExercisePopupBody");
		closeExercisePopup.add(closeExercisePopupBody);

		closeExercisePopupText = new Label("Closing the exercise will empty your editor content. Continue?");
		closeExercisePopupText.getElement().setId("closeExercisePopupText");
		closeExercisePopupBody.add(closeExercisePopupText);

		closeExercisePopupFooter = new ModalFooter();
		closeExercisePopupFooter.setId("closeExercisePopupFooter");
		closeExercisePopup.add(closeExercisePopupFooter);

		closeExercisePopupCancelButton = new Button();
		closeExercisePopupCancelButton.setId("closeExercisePopupCancelButton");
		closeExercisePopupCancelButton.addStyleName("fa fa-times");
		closeExercisePopupFooter.add(closeExercisePopupCancelButton);

		closeExercisePopupOkButton = new Button();
		closeExercisePopupOkButton.setId("closeExercisePopupOkButton");
		closeExercisePopupOkButton.addStyleName("fa fa-check");
		closeExercisePopupFooter.add(closeExercisePopupOkButton);
		
		exportPopup = new Modal();
		exportPopup.setId("exportPopup");
		exportPopup.setDataKeyboard(true);
		exportPopup.setClosable(false);
		exportPopup.setDataBackdrop(ModalBackdrop.STATIC);

		exportPopupBody = new ModalBody();
		exportPopupBody.setId("exportPopupBody");
		exportPopup.add(exportPopupBody);

		exportArea = new TextArea();
		exportArea.setId("exportArea");
		exportArea.addStyleName("exportArea");
		exportArea.setReadOnly(true);
		exportPopupBody.add(exportArea);

		exportPopupFooter = new ModalFooter();
		exportPopupFooter.setId("exportPopupFooter");
		exportPopup.add(exportPopupFooter);

		exportPopupOkButton = new Button();
		exportPopupOkButton.setId("exportPopupOkButton");
		exportPopupOkButton.addStyleName("fa fa-check");
		exportPopupFooter.add(exportPopupOkButton);
		
		
		
		LoadExercise loadExerciseAction = new LoadExercise();
		loadExercisePopupOkButton.addClickHandler(e -> loadExerciseAction.run());
		loadExercisePopupCancelButton.addClickHandler(e -> loadExercisePopup.hide());
		closeExercisePopupOkButton.addClickHandler(e -> new EnterDefaultMode().run());
		closeExercisePopupCancelButton.addClickHandler(e -> closeExercisePopup.hide());
		exportPopupOkButton.addClickHandler(e -> exportPopup.hide());
		List<Exercise> exercises = Exercises.all();
		for (int i = 0; i < exercises.size(); i++) {
			SelectExercise action = new SelectExercise(loadExerciseAction, exercises.get(i));
			exerciseButtons.get(i).addClickHandler(e -> action.run());
		}
		toggleSolutionButton.addClickHandler(e -> solutionArea.setVisible(!solutionArea.isVisible()));
		closeExerciseButton.addClickHandler(e -> closeExercisePopup.show());
		outputFormatBox.addChangeHandler(h -> new SetOutputFormat().run());
		reductionOrderBox.addChangeHandler(h -> new SetReductionOrder().run());
		outputSizeBox.addChangeHandler(h -> new SetOutputSize().run());

		backwardButton.addClickHandler(e -> new StepBackward().run());

		forwardButton.addClickHandler(e -> new StepForward().run());
		clearButton.addClickHandler(e -> new Clear().run());
		runButton.addClickHandler(e -> new RunExecution().run());
		pauseButton.addClickHandler(e -> new Pause().run());
		unpauseButton.addClickHandler(e -> new Unpause().run());
		List<Export> exports = Exports.all();
		for (int i = 0; i < exports.size(); i++) {
			SelectExportFormat action = new SelectExportFormat(exports.get(i));
			exportButtons.get(i).addClickHandler(e -> action.run());
		}
		shareButton.addClickHandler(e -> new UseShare(Arrays.asList(new UpdateShareURL())).run());

		//keybindings
		RootPanel.get().addDomHandler(event -> {
			if (event.isControlKeyDown() && event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
				if (clearButton.isEnabled()) {
					clearButton.click();
				}
			}
		}, KeyDownEvent.getType());
		
		RootPanel.get().addDomHandler(event -> {
			if (event.isControlKeyDown() && event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if (runButton.isEnabled()) {
					runButton.click();
				}
			}
		}, KeyDownEvent.getType());
		
		loadExercisePopup.addDomHandler(event -> {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				loadExercisePopupOkButton.click();
			}
		}, KeyDownEvent.getType());
		
		closeExercisePopup.addDomHandler(event -> {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				closeExercisePopupOkButton.click();
			}
		}, KeyDownEvent.getType());
		
		
		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		editor = MonacoEditor.load(editorPanel);

		executor = new Executor(Arrays.asList(new UpdateOutput()),
				Arrays.asList(new FinishExecution()));	
		
		Control.updateControls();

		
		// try deserialization if possible
		String state = Window.Location.getParameter(KEY);
		if (state != null) {
			// deserialize
			DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
			AsyncCallback<String> callback = new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {
					App.get().outputArea().add(new Text(caught.getMessage()));
				}

				@Override
				public void onSuccess(String result) {
					if (result != null) {
						deserialize(result);
					}

				}
			};
			databaseService.getSerialization(state, callback);
		}
	}

	/**
	 * Serializes the Application by returning a String from which the state of the
	 * application can be recreated.
	 *
	 * The String holds information about the {@link Executor}, the {@link Editor},
	 * the {@link OptionBox}es and the selected {@link Library}s in this order.
	 *
	 * @return the string representation of the application
	 */
	@Override
	public StringBuilder serialize() {
		StringBuilder executionEngineString = executor.serialize();

		StringBuilder editorString = new StringBuilder(editor.read());

		StringBuilder outputFormatBoxString = new StringBuilder(Integer.toString(outputFormatBox.getSelectedIndex()));
		StringBuilder reductionOrderBoxString = new StringBuilder(
				Integer.toString(reductionOrderBox.getSelectedIndex()));
		StringBuilder outputSizeString = new StringBuilder(Integer.toString(outputSizeBox.getSelectedIndex()));

		// has one character for each library. A 'c' for a selected library and
		// a 'u' for an unselected library
		StringBuilder libraryCheckBoxesString = new StringBuilder();
		for (int i = 0; i < libraryCheckBoxes.size(); i++) {
			if (libraryCheckBoxes.get(i).getValue()) {
				libraryCheckBoxesString.append(CHECKED_LIBRARY);
			} else {
				libraryCheckBoxesString.append(UNCHECKED_LIBRARY);
			}
		}
		StringBuilder exerciseString = new StringBuilder("");
		if (currentExercise != null) {
			exerciseString = currentExercise.serialize();
		}

		/*
		 * EXECUTOR_SERIALIZATION = 0; EDITOR_SERIALIZATION = 1;
		 * OUTPUTFORMAT_SERIALIZATION = 2; REDUCTIONORDER_SERIALIZATION = 3;
		 * OUTPUTSIZE_SERIALIZATION = 4; LIBRARY_SERIALIZATION = 5;
		 */
		return SerializationUtilities.enclose(executionEngineString, editorString, outputFormatBoxString,
				reductionOrderBoxString, outputSizeString, libraryCheckBoxesString, exerciseString);
	}

	/**
	 * Deserializes the Application with the given String.
	 *
	 * This includes {@link Executor}, the {@link Editor}, the {@link OptionBox}es
	 * and the selected {@link Library}s. It sets the application into step by step
	 * mode if the Executor holds terms and leaves the application in its initial
	 * state else.
	 *
	 * @param content
	 *            the string representing the state of the application
	 */
	public void deserialize(String content) {
		List<String> val = SerializationUtilities.extract(content);
		assert (val
				.size() == NUMBER_OF_SERIALIZATIONS) : 
					"SerializationUtilities extracted a list of strings that doesn't contain 6 arguments";

		// Selects the correct option of the optionBoxes
		// assert that the given strings are representations of decimal integers
		// and that the index fits the optionBox
		outputFormatBox.setSelectedIndex(Integer.parseInt(val.get(OUTPUTFORMAT_SERIALIZATION)));
		reductionOrderBox.setSelectedIndex(Integer.parseInt(val.get(REDUCTIONORDER_SERIALIZATION)));
		outputSizeBox.setSelectedIndex(Integer.parseInt(val.get(OUTPUTSIZE_SERIALIZATION)));
		
		// deserializes the executor with the correct string
		executor.deserialize(val.get(EXECUTOR_SERIALIZATION));

		// checks and unchecks the Library Check Boxes
		assert (val.get(LIBRARY_SERIALIZATION).length() == libraryCheckBoxes.size());
		for (int i = 0; i < val.get(LIBRARY_SERIALIZATION).length(); i++) {
			assert (val.get(LIBRARY_SERIALIZATION).charAt(i) == CHECKED_LIBRARY
					|| val.get(LIBRARY_SERIALIZATION).charAt(i) == UNCHECKED_LIBRARY);
			if (val.get(LIBRARY_SERIALIZATION).charAt(i) == CHECKED_LIBRARY) {
				libraryCheckBoxes.get(i).setValue(true);
			} else {
				libraryCheckBoxes.get(i).setValue(false);
			}
		}
		
		if (!val.get(EXERCISE_SERIALIZATION).isEmpty()) {
			// deserialize exercise mode
			Exercise exercise = Exercises.deserialize(val.get(EXERCISE_SERIALIZATION).toString());
			setCurrentExercise(exercise);
			
			exportButtons().forEach(b -> b.setEnabled(false));
			
			exerciseDescriptionLabel().setText(exercise.getTask());
			solutionArea().setText(exercise.getSolution());
			
			editorExercisePanel().setWidgetHidden(exercisePanel(), false);
			
			// writes Editor with given content
			if (val.get(EDITOR_SERIALIZATION) != exercise.getPredefinitions()) {
				editor.write(val.get(EDITOR_SERIALIZATION));
			}
			//editor.write(val.get(EDITOR_SERIALIZATION));

			Control.updateControls();
		} else {
			// writes Editor with given content
			editor.write(val.get(EDITOR_SERIALIZATION));

			Control.updateControls();
		}
		
	}

	public DockLayoutPanel mainPanel() {
		return mainPanel;
	}

	public DropDown mainMenu() {
		return mainMenu;
	}

	public Button openMainMenuButton() {
		return openMainMenuButton;
	}

	public DropDownMenu mainMenuPanel() {
		return mainMenuPanel;
	}

	public DropDownHeader mainMenuLibraryTitle() {
		return mainMenuLibraryTitle;
	}

	public List<CheckBox> libraryCheckBoxes() {
		return libraryCheckBoxes;
	}

	public Divider mainMenuDivider() {
		return mainMenuDivider;
	}

	public DropDownHeader mainMenuExerciseTitle() {
		return mainMenuExerciseTitle;
	}

	public List<AnchorListItem> exerciseButtons() {
		return exerciseButtons;
	}

	public FlowPanel footerPanel() {
		return footerPanel;
	}

	public ButtonGroup exportDropupGroup() {
		return exportDropupGroup;
	}

	public Button openExportMenuButton() {
		return openExportMenuButton;
	}

	public DropDownMenu exportMenu() {
		return exportMenu;
	}

	public List<AnchorListItem> exportButtons() {
		return exportButtons;
	}
	
	public ButtonGroup shareGroup() {
		return shareGroup;
	}

	public TextBox sharePanel() {
		return sharePanel;
	}

	public Button shareButton() {
		return shareButton;
	}
	
	public SplitLayoutPanel ioPanel() {
		return ioPanel;
	}

	public DockLayoutPanel inputPanel() {
		return inputPanel;
	}

	public FlowPanel inputControlPanel() {
		return inputControlPanel;
	}
	
	public FlowPanel optionBarPanel() {
		return optionBarPanel;
	}

	public ListBox outputFormatBox() {
		return outputFormatBox;
	}

	public ListBox reductionOrderBox() {
		return reductionOrderBox;
	}

	public ListBox outputSizeBox() {
		return outputSizeBox;
	}

	public FlowPanel controlPanel() {
		return controlPanel;
	}

	public FlowPanel stepByStepControlPanel() {
		return stepByStepControlPanel;
	}

	public Button backwardButton() {
		return backwardButton;
	}

	public Button pauseButton() {
		return pauseButton;
	}
	
	public Button unpauseButton() {
		return unpauseButton;
	}
	
	public Button forwardButton() {
		return forwardButton;
	}

	public Label spinner() {
		return spinner;
	}
	
	public FlowPanel runControlPanel() {
		return runControlPanel;
	}

	public Button clearButton() {
		return clearButton;
	}
	
	public Button runButton() {
		return runButton;
	}

	public SplitLayoutPanel editorExercisePanel() {
		return editorExercisePanel;
	}

	public FlowPanel exercisePanel() {
		return exercisePanel;
	}

	public FlowPanel exerciseHeaderPanel() {
		return exerciseHeaderPanel;
	}

	public FlowPanel exerciseControlPanel() {
		return exerciseControlPanel;
	}

	public Button toggleSolutionButton() {
		return toggleSolutionButton;
	}

	public Button closeExerciseButton() {
		return closeExerciseButton;
	}
	
	public Label exerciseDescriptionLabel() {
		return exerciseDescriptionLabel;
	}

	public TextArea solutionArea() {
		return solutionArea;
	}

	public SimplePanel editorPanel() {
		return editorPanel;
	}

	public FlowPanel outputBlocker() {
		return this.outputBlocker;
	}
	
	public FlowPanel outputArea() {
		return outputArea;
	}

	public Modal loadExercisePopup() {
		return loadExercisePopup;
	}

	public ModalBody loadExercisePopupBody() {
		return loadExercisePopupBody;
	}

	public Label loadExercisePopupText() {
		return loadExercisePopupText;
	}

	public ModalFooter loadExercisePopupFooter() {
		return loadExercisePopupFooter;
	}

	public Button loadExercisePopupOkButton() {
		return loadExercisePopupOkButton;
	}

	public Button loadExercisePopupCancelButton() {
		return loadExercisePopupCancelButton;
	}

	public Modal closeExercisePopup() {
		return closeExercisePopup;
	}

	public ModalBody closeExercisePopupBody() {
		return closeExercisePopupBody;
	}

	public Label closeExercisePopupText() {
		return closeExercisePopupText;
	}

	public ModalFooter closeExercisePopupFooter() {
		return closeExercisePopupFooter;
	}

	public Button closeExercisePopupOkButton() {
		return closeExercisePopupOkButton;
	}

	public Button closeExercisePopupCancelButton() {
		return closeExercisePopupCancelButton;
	}
	
	public Modal exportPopup() {
		return exportPopup;
	}

	public ModalBody exportPopupBody() {
		return exportPopupBody;
	}

	public TextArea exportArea() {
		return exportArea;
	}

	public ModalFooter exportPopupFooter() {
		return exportPopupFooter;
	}

	public Button exportPopupOkButton() {
		return exportPopupOkButton;
	}
	
	public MonacoEditor editor() {
		return editor;
	}
	
	public Executor executor() {
		return executor;
	}
	
	public Exercise currentExercise() {
		return currentExercise;
	}
	
	public static native void autoScrollOutput() /*-{
		var elem = $doc.getElementById('scroll');
		elem.scrollTop = elem.scrollHeight;
		return;
	}-*/;
	
	public void setCurrentExercise(Exercise e) {
		this.currentExercise = e;
	}
}
