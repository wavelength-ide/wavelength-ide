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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
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
import edu.kit.wavelength.client.view.action.Control;
import edu.kit.wavelength.client.view.action.EnterDefaultMode;
import edu.kit.wavelength.client.view.action.LoadExercise;
import edu.kit.wavelength.client.view.action.Pause;
import edu.kit.wavelength.client.view.action.RunExecution;
import edu.kit.wavelength.client.view.action.SelectExercise;
import edu.kit.wavelength.client.view.action.SelectExportFormat;
import edu.kit.wavelength.client.view.action.SetReductionOrder;
import edu.kit.wavelength.client.view.action.StepBackward;
import edu.kit.wavelength.client.view.action.StepForward;
import edu.kit.wavelength.client.view.action.Terminate;
import edu.kit.wavelength.client.view.action.Unpause;
import edu.kit.wavelength.client.view.action.UseShare;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Export;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.gwt.MonacoEditor;
import edu.kit.wavelength.client.view.update.FinishExecution;
import edu.kit.wavelength.client.view.update.UpdateShareURL;
import edu.kit.wavelength.client.view.update.UpdateTreeOutput;
import edu.kit.wavelength.client.view.update.UpdateURL;
import edu.kit.wavelength.client.view.update.UpdateUnicodeOutput;

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
	private static final int NUMBER_OF_SERIALIZATIONS = 6;
	private static final char CHECKED_LIBRARY = 'c';
	private static final char UNCHECKED_LIBRARY = 'u';
	private static final int POLLING_DELAY_MS = 10000;

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

	/**
	 * Name of the unicode format.
	 */
	public static final String UnicodeOutputName = "Unicode";
	/**
	 * Name of the tree format.
	 */
	public static final String TreeOutputName = "Tree";

	// UI-Elements
	private DockLayoutPanel mainPanel;
	private DropDown mainMenu;
	private Button openMainMenuButton;
	private DropDownMenu mainMenuPanel;
	private DropDownHeader mainMenuLibraryTitle;
	private List<CheckBox> libraryCheckBoxes;
	private Divider mainMenuDivider;
	private DropDownHeader mainMenuExerciseTitle;
	private List<AnchorListItem> exerciseButtons;
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
	private FlowPanel footerPanel;
	private SplitLayoutPanel ioPanel;
	private DockLayoutPanel inputPanel;
	private FlowPanel outputArea;
	private FlowPanel inputControlPanel;
	private SplitLayoutPanel editorExercisePanel;
	private FlowPanel exercisePanel;
	private FlowPanel exerciseHeaderPanel;
	private FlowPanel exerciseControlPanel;
	private Label exerciseDescriptionLabel;
	private Button toggleSolutionButton;
	private Button closeExerciseButton;
	private TextArea solutionArea;
	private SimplePanel editorPanel;
	private FlowPanel optionBarPanel;
	private ListBox outputFormatBox;
	private ListBox reductionOrderBox;
	private ListBox outputSizeBox;
	private FlowPanel controlPanel;
	private FlowPanel stepByStepControlPanel;
	private Button backwardsButton;
	private Button pauseButton;
	private Button unpauseButton;
	private Button forwardButton;
	private FlowPanel runControlPanel;
	private Button terminateButton;
	private Button runButton;
	private ButtonGroup exportDropupGroup;
	private Button openExportMenuButton;
	private DropDownMenu exportMenu;
	private List<AnchorListItem> exportButtons;
	private Modal exportPopup;
	private ModalBody exportPopupBody;
	private TextArea exportArea;
	private ModalFooter exportPopupFooter;
	private Button exportPopupBodyOkButton;
	private ButtonGroup shareGroup;
	private TextBox sharePanel;
	private Button shareButton;
	private FlowPanel outputBlocker;

	// editor
	private MonacoEditor editor;

	// executor
	private Executor executor;

	// possible outputs
	private boolean unicodeIsSet;
	private boolean treeIsSet;

	private App() {

	}

	/**
	 * Initializes App.
	 */
	private void initialize() {
		// general layout
		mainPanel = new DockLayoutPanel(Unit.EM);
		mainPanel.addStyleName("mainPanel");
		// main menu
		mainMenu = new DropDown();
		mainMenu.addStyleName("mainMenu");
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		openMainMenuButton = new Button();
		openMainMenuButton.addStyleName("fa fa-bars");
		openMainMenuButton.setToggleCaret(false);
		openMainMenuButton.setDataToggle(Toggle.DROPDOWN);
		mainMenu.add(openMainMenuButton);

		mainMenuPanel = new DropDownMenu();
		mainMenuPanel.addStyleName("mainMenuPanel");
		// prevent dropdown from closing when clicking inside
		mainMenuPanel.addDomHandler(event -> event.stopPropagation(), ClickEvent.getType());
		mainMenu.add(mainMenuPanel);
		// add libraries to main menu
		mainMenuLibraryTitle = new DropDownHeader("Libraries");
		mainMenuPanel.add(mainMenuLibraryTitle);

		libraryCheckBoxes = new ArrayList<>();
		Libraries.all().forEach(lib -> {
			CheckBox libraryCheckBox = new CheckBox(lib.getName());
			libraryCheckBox.addStyleName("libraryCheckBox");
			libraryCheckBox.setName(lib.getName());
			mainMenuPanel.add(libraryCheckBox);
			libraryCheckBoxes.add(libraryCheckBox);
		});

		mainMenuDivider = new Divider();
		mainMenuPanel.add(mainMenuDivider);
		// add exercises to main menu
		mainMenuExerciseTitle = new DropDownHeader("Exercises");
		mainMenuPanel.add(mainMenuExerciseTitle);

		exerciseButtons = new ArrayList<>();
		Exercises.all().forEach(excs -> {
			AnchorListItem exerciseButton = new AnchorListItem();
			exerciseButton.setText(excs.getName());
			mainMenuPanel.add(exerciseButton);
			exerciseButtons.add(exerciseButton);
		});
		// create exercise mode popup
		loadExercisePopup = new Modal();
		loadExercisePopup.setClosable(false);
		loadExercisePopup.setDataBackdrop(ModalBackdrop.STATIC);

		loadExercisePopupBody = new ModalBody();
		loadExercisePopup.add(loadExercisePopupBody);

		loadExercisePopupText = new Label("Opening the exercise will empty your editor content. Continue?");
		loadExercisePopupBody.add(loadExercisePopupText);

		loadExercisePopupFooter = new ModalFooter();
		loadExercisePopup.add(loadExercisePopupFooter);

		loadExercisePopupCancelButton = new Button();
		loadExercisePopupCancelButton.addStyleName("fa fa-times");
		loadExercisePopupFooter.add(loadExercisePopupCancelButton);

		loadExercisePopupOkButton = new Button();
		loadExercisePopupOkButton.addStyleName("fa fa-check");
		loadExercisePopupFooter.add(loadExercisePopupOkButton);

		closeExercisePopup = new Modal();
		closeExercisePopup.setClosable(false);
		closeExercisePopup.setDataBackdrop(ModalBackdrop.STATIC);

		closeExercisePopupBody = new ModalBody();
		closeExercisePopup.add(closeExercisePopupBody);

		closeExercisePopupText = new Label("Closing the exercise will empty your editor content. Continue?");
		closeExercisePopupBody.add(closeExercisePopupText);

		closeExercisePopupFooter = new ModalFooter();
		closeExercisePopup.add(closeExercisePopupFooter);

		closeExercisePopupCancelButton = new Button();
		closeExercisePopupCancelButton.addStyleName("fa fa-times");
		closeExercisePopupFooter.add(closeExercisePopupCancelButton);

		closeExercisePopupOkButton = new Button();
		closeExercisePopupOkButton.addStyleName("fa fa-check");
		closeExercisePopupFooter.add(closeExercisePopupOkButton);
		// general layout
		footerPanel = new FlowPanel();
		footerPanel.addStyleName("footerPanel");
		mainPanel.addSouth(footerPanel, 2);
		// hack to display dropup on top of rest of ui
		footerPanel.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		ioPanel = new SplitLayoutPanel(3);
		mainPanel.add(ioPanel);

		inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 0.45 * Window.getClientHeight());

		outputBlocker = new FlowPanel("div");
		outputBlocker.addStyleName("output");
		outputBlocker.getElement().setId("scroll");

		outputArea = new FlowPanel("div");
		outputArea.getElement().setId("network");
		outputArea.setWidth("auto");
		outputArea.addStyleName("output");
		outputBlocker.add(outputArea);
		ioPanel.add(outputBlocker);

		inputControlPanel = new FlowPanel();
		inputControlPanel.addStyleName("inputControlPanel");
		inputPanel.addSouth(inputControlPanel, 1.85);

		editorExercisePanel = new SplitLayoutPanel(3);
		editorExercisePanel.addStyleName("editorExercisePanel");
		inputPanel.add(editorExercisePanel);

		exercisePanel = new FlowPanel();
		exercisePanel.addStyleName("exercisePanel");
		editorExercisePanel.addEast(exercisePanel, 0.3 * Window.getClientWidth());
		editorExercisePanel.setWidgetHidden(exercisePanel, true);

		exerciseHeaderPanel = new FlowPanel();
		exercisePanel.add(exerciseHeaderPanel);

		exerciseControlPanel = new FlowPanel();
		exerciseControlPanel.addStyleName("exerciseControlPanel");
		exerciseHeaderPanel.add(exerciseControlPanel);

		exerciseDescriptionLabel = new HTML("hello world<br>hello world<br>hello world<br>");
		exerciseDescriptionLabel.addStyleName("exerciseDescriptionLabel");
		exerciseHeaderPanel.add(exerciseDescriptionLabel);

		toggleSolutionButton = new Button();
		toggleSolutionButton.addStyleName("fa fa-lightbulb-o");
		exerciseControlPanel.add(toggleSolutionButton);

		closeExerciseButton = new Button();
		closeExerciseButton.addStyleName("fa fa-times-circle-o");
		exerciseControlPanel.add(closeExerciseButton);

		solutionArea = new TextArea();
		solutionArea.addStyleName("solutionArea");
		solutionArea.setVisible(false);
		solutionArea.setReadOnly(true);
		solutionArea.setText("hello\n\tworld\n\t\teveryone");
		exercisePanel.add(solutionArea);

		editorPanel = new SimplePanel();
		// id needed because MonacoEditor adds to panel div by id
		editorPanel.getElement().setId("editor");
		editorExercisePanel.add(editorPanel);
		// options
		optionBarPanel = new FlowPanel();
		optionBarPanel.addStyleName("optionBarPanel");
		inputControlPanel.add(optionBarPanel);
		// output formats
		outputFormatBox = new ListBox();
		outputFormatBox.addItem("Unicode Output");
		outputFormatBox.addItem("Tree Output");
		optionBarPanel.add(outputFormatBox);
		// reduction orders
		reductionOrderBox = new ListBox();
		reductionOrderBox.setName("Reduction Order");
		ReductionOrders.all().stream().map(ReductionOrder::getName).forEach(reductionOrderBox::addItem);
		optionBarPanel.add(reductionOrderBox);
		// output sizes
		outputSizeBox = new ListBox();
		outputSizeBox.setName("Output Size");
		OutputSizes.all().stream().map(OutputSize::getName).forEach(outputSizeBox::addItem);
		optionBarPanel.add(outputSizeBox);
		// engine control options
		controlPanel = new FlowPanel();
		controlPanel.addStyleName("controlPanel");
		inputControlPanel.add(controlPanel);

		stepByStepControlPanel = new FlowPanel();
		stepByStepControlPanel.addStyleName("stepByStepControlPanel");
		controlPanel.add(stepByStepControlPanel);

		backwardsButton = new Button();
		backwardsButton.addStyleName("fa fa-chevron-left");
		stepByStepControlPanel.add(backwardsButton);
		
		pauseButton = new Button();
		pauseButton.addStyleName("fa fa-pause");
		stepByStepControlPanel.add(pauseButton);
		
		unpauseButton = new Button();
		unpauseButton.addStyleName("fa fa-step-forward");
		stepByStepControlPanel.add(unpauseButton);
		
		forwardButton = new Button();
		forwardButton.addStyleName("fa fa-chevron-right");
		stepByStepControlPanel.add(forwardButton);
		
		runControlPanel = new FlowPanel();
		runControlPanel.addStyleName("runControlPanel");
		controlPanel.add(runControlPanel);
		
		terminateButton = new Button();
		terminateButton.addStyleName("fa fa-times");
		runControlPanel.add(terminateButton);
		
		runButton = new Button();
		runButton.addStyleName("fa fa-play");
		runControlPanel.add(runButton);
		
		// footer (export and share)
		exportDropupGroup = new ButtonGroup();
		exportDropupGroup.setDropUp(true);
		footerPanel.add(exportDropupGroup);

		openExportMenuButton = new Button();
		openExportMenuButton.setDataToggle(Toggle.DROPDOWN);
		openExportMenuButton.setToggleCaret(false);
		openExportMenuButton.addStyleName("fa fa-level-up");
		exportDropupGroup.add(openExportMenuButton);

		exportMenu = new DropDownMenu();
		exportButtons = new ArrayList<>();
		Exports.all().forEach(e -> {
			AnchorListItem exportButton = new AnchorListItem();
			exportButton.setText(e.getName());
			exportButton.setEnabled(false);
			exportMenu.add(exportButton);
			exportButtons.add(exportButton);
		});
		exportDropupGroup.add(exportMenu);

		exportPopup = new Modal();
		exportPopup.setDataKeyboard(true);
		exportPopup.setClosable(false);
		exportPopup.setDataBackdrop(ModalBackdrop.STATIC);

		exportPopupBody = new ModalBody();
		exportPopup.add(exportPopupBody);

		exportArea = new TextArea();
		exportArea.addStyleName("exportArea");
		exportArea.setReadOnly(true);
		exportPopupBody.add(exportArea);

		exportPopupFooter = new ModalFooter();
		exportPopup.add(exportPopupFooter);

		exportPopupBodyOkButton = new Button();
		exportPopupBodyOkButton.addStyleName("fa fa-check");
		exportPopupFooter.add(exportPopupBodyOkButton);

		// this only exists for style consistency with exportButton
		shareGroup = new ButtonGroup();
		footerPanel.add(shareGroup);

		sharePanel = new TextBox();
		sharePanel.addStyleName("sharePanel");
		sharePanel.setReadOnly(true);
		sharePanel.setVisible(false);
		footerPanel.add(sharePanel);

		shareButton = new Button();
		shareButton.addStyleName("fa fa-share-alt");
		shareGroup.add(shareButton);

		// set handlers
		LoadExercise loadExerciseAction = new LoadExercise();
		loadExercisePopupOkButton.addClickHandler(e -> loadExerciseAction.run());

		loadExercisePopupCancelButton.addClickHandler(e -> loadExercisePopup.hide());
		closeExercisePopupOkButton.addClickHandler(e -> new EnterDefaultMode().run());
		closeExercisePopupCancelButton.addClickHandler(e -> closeExercisePopup.hide());

		exportPopupBodyOkButton.addClickHandler(e -> exportPopup.hide());

		List<Exercise> exercises = Exercises.all();
		for (int i = 0; i < exercises.size(); i++) {
			SelectExercise action = new SelectExercise(loadExerciseAction, exercises.get(i));
			exerciseButtons.get(i).addClickHandler(e -> action.run());
		}

		toggleSolutionButton.addClickHandler(e -> solutionArea.setVisible(!solutionArea.isVisible()));
		closeExerciseButton.addClickHandler(e -> closeExercisePopup.show());

		reductionOrderBox.addChangeHandler(h -> new SetReductionOrder().run());

		backwardsButton.addClickHandler(e -> new StepBackward().run());
		forwardButton.addClickHandler(e -> new StepForward().run());
		terminateButton.addClickHandler(e -> new Terminate().run());
		runButton.addClickHandler(e -> new RunExecution().run());
		pauseButton.addClickHandler(e -> new Pause().run());
		unpauseButton.addClickHandler(e -> new Unpause().run());

		List<Export> exports = Exports.all();
		for (int i = 0; i < exports.size(); i++) {
			SelectExportFormat action = new SelectExportFormat(exports.get(i));
			exportButtons.get(i).addClickHandler(e -> action.run());
		}
		// set auto-serialization of URL
		UpdateURL updateURL = new UpdateURL();
		UpdateShareURL updateShareURL = new UpdateShareURL();
		URLSerializer urlSerializer = new URLSerializer(Arrays.asList(updateURL, updateShareURL), POLLING_DELAY_MS);
		urlSerializer.startPolling();
		shareButton.addClickHandler(e -> new UseShare(urlSerializer).run());
		// create editor and executor
		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		editor = MonacoEditor.load(editorPanel);
		executor = new Executor(Arrays.asList(new UpdateUnicodeOutput(), new UpdateTreeOutput()),
				Arrays.asList(new FinishExecution()));
		// standard output is unicode output
		unicodeIsSet = true;
		treeIsSet = false;
		
		Control.updateControls();

		// try deserialization if possible
		String state = History.getToken();
		if (state.length() > 0) {
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

		/*
		 * EXECUTOR_SERIALIZATION = 0; EDITOR_SERIALIZATION = 1;
		 * OUTPUTFORMAT_SERIALIZATION = 2; REDUCTIONORDER_SERIALIZATION = 3;
		 * OUTPUTSIZE_SERIALIZATION = 4; LIBRARY_SERIALIZATION = 5;
		 */
		return SerializationUtilities.enclose(executionEngineString, editorString, outputFormatBoxString,
				reductionOrderBoxString, outputSizeString, libraryCheckBoxesString);
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
				.size() == NUMBER_OF_SERIALIZATIONS) : "SerializationUtilities extracted a list of strings that doesn't contain 6 arguments";

		// deserializes the executor with the correct string
		executor.deserialize(val.get(EXECUTOR_SERIALIZATION));

		// writes Editor with given content
		editor.write(val.get(EDITOR_SERIALIZATION));

		// Selects the correct option of the optionBoxes
		// assert that the given strings are representations of decimal integers
		// and that the idex fits the optionBox
		outputFormatBox.setSelectedIndex(Integer.parseInt(val.get(OUTPUTFORMAT_SERIALIZATION)));
		reductionOrderBox.setSelectedIndex(Integer.parseInt(val.get(REDUCTIONORDER_SERIALIZATION)));
		outputSizeBox.setSelectedIndex(Integer.parseInt(val.get(OUTPUTSIZE_SERIALIZATION)));

		// checks and unchecks the Library Check Boxes
		assert (val.get(LIBRARY_SERIALIZATION).length() == libraryCheckBoxes.size());
		for (int i = 0; i < val.get(5).length(); i++) {
			assert (val.get(LIBRARY_SERIALIZATION).charAt(i) == CHECKED_LIBRARY
					|| val.get(LIBRARY_SERIALIZATION).charAt(i) == UNCHECKED_LIBRARY);
			if (val.get(LIBRARY_SERIALIZATION).charAt(i) == CHECKED_LIBRARY) {
				libraryCheckBoxes.get(i).setValue(true);
			} else {
				libraryCheckBoxes.get(i).setValue(false);
			}
		}

		Control.updateControls();
	}

	// getters
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

	public FlowPanel footerPanel() {
		return footerPanel;
	}

	public SplitLayoutPanel ioPanel() {
		return ioPanel;
	}

	public DockLayoutPanel inputPanel() {
		return inputPanel;
	}

	public FlowPanel outputArea() {
		return outputArea;
	}

	public FlowPanel inputControlPanel() {
		return inputControlPanel;
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

	public Label exerciseDescriptionLabel() {
		return exerciseDescriptionLabel;
	}

	public Button toggleSolutionButton() {
		return toggleSolutionButton;
	}

	public Button closeExerciseButton() {
		return closeExerciseButton;
	}

	public TextArea solutionArea() {
		return solutionArea;
	}

	public SimplePanel editorPanel() {
		return editorPanel;
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

	public Button backwardsButton() {
		return backwardsButton;
	}

	public Button unpauseButton() {
		return unpauseButton;
	}
	
	public Button pauseButton() {
		return pauseButton;
	}
	
	public Button forwardButton() {
		return forwardButton;
	}

	public FlowPanel runControlPanel() {
		return runControlPanel;
	}

	public Button terminateButton() {
		return terminateButton;
	}
	
	public Button runButton() {
		return runButton;
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

	public Button exportPopupBodyOkButton() {
		return exportPopupBodyOkButton;
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

	public MonacoEditor editor() {
		return editor;
	}

	public Executor executor() {
		return executor;
	}

	public boolean unicodeIsSet() {
		return this.unicodeIsSet;
	}

	public void setUnicode(boolean value) {
		this.unicodeIsSet = value;
	}

	public boolean treeIsSet() {
		return this.treeIsSet;
	}

	public void setTree(boolean value) {
		this.treeIsSet = value;
	}

	public FlowPanel outputBlocker() {
		return this.outputBlocker;
	}

	public static native void autoScroll() /*-{
		var elem = $doc.getElementById('scroll');
		elem.scrollTop = elem.scrollHeight;
		return;
	}-*/;
}
