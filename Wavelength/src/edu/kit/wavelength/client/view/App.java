package edu.kit.wavelength.client.view;

import java.util.ArrayList;
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

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.Serializable;

import edu.kit.wavelength.client.view.action.EnterDefaultMode;
import edu.kit.wavelength.client.view.action.LoadExercise;
import edu.kit.wavelength.client.view.action.RunNewExecution;
import edu.kit.wavelength.client.view.action.SelectExercise;
import edu.kit.wavelength.client.view.action.SelectExportFormat;
import edu.kit.wavelength.client.view.action.SetReductionOrder;
import edu.kit.wavelength.client.view.action.StepBackward;
import edu.kit.wavelength.client.view.action.StepByStep;
import edu.kit.wavelength.client.view.action.StepForward;
import edu.kit.wavelength.client.view.action.Stop;
import edu.kit.wavelength.client.view.action.UnpauseExecution;
import edu.kit.wavelength.client.view.action.UseShare;

import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Export;
import edu.kit.wavelength.client.view.export.Exports;

import edu.kit.wavelength.client.view.gwt.MonacoEditor;


/**
 * App is a singleton that initializes and holds the view.
 */
public class App implements Serializable {

	private static App instance = null;

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
	private Button stepByStepButton;
	private Button forwardButton;
	private FlowPanel runControlPanel;
	private Button cancelButton;
	private Button runButton;
	private Button pauseButton;
	private Button unpauseButton;
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

	public MonacoEditor editor;

	public Executor executor;

	private App() {
		
	}

	
	/**
	 * Initializes App.
	 */
	private void initialize() {
		String state = Window.Location.getPath();
		// deserialize

		mainPanel = new DockLayoutPanel(Unit.EM);
		mainPanel.addStyleName("mainPanel");

		mainMenu = new DropDown();
		mainMenu.addStyleName("mainMenu");
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
		
		openMainMenuButton = new Button();
		openMainMenuButton.addStyleName("fa fa-cog");
		openMainMenuButton.setToggleCaret(false);
		openMainMenuButton.setDataToggle(Toggle.DROPDOWN);
		mainMenu.add(openMainMenuButton);
		
		mainMenuPanel = new DropDownMenu();
		// prevent dropdown from closing when clicking inside
		mainMenuPanel.addDomHandler(event -> event.stopPropagation(), ClickEvent.getType());
		mainMenu.add(mainMenuPanel);
		
		mainMenuLibraryTitle = new DropDownHeader("Libraries");
		mainMenuPanel.add(mainMenuLibraryTitle);
		
		libraryCheckBoxes = new ArrayList<>();
		Libraries.all().forEach(lib -> {
			CheckBox libraryCheckBox = new CheckBox(lib.getName());
			libraryCheckBox.addStyleName("libraryCheckBox");
			mainMenuPanel.add(libraryCheckBox);
			libraryCheckBoxes.add(libraryCheckBox);
		});
		
		mainMenuDivider = new Divider();
		mainMenuPanel.add(mainMenuDivider);
		
		mainMenuExerciseTitle = new DropDownHeader("Exercises");
		mainMenuPanel.add(mainMenuExerciseTitle);
		
		exerciseButtons = new ArrayList<>();
		Exercises.all().forEach(excs -> {
			AnchorListItem exerciseButton = new AnchorListItem();
			exerciseButton.setText(excs.getName());
			mainMenuPanel.add(exerciseButton);
			exerciseButtons.add(exerciseButton);
		});

		loadExercisePopup = new Modal();
		loadExercisePopup.setClosable(false);
		loadExercisePopup.setDataBackdrop(ModalBackdrop.STATIC);
		
		loadExercisePopupBody = new ModalBody();
		loadExercisePopup.add(loadExercisePopupBody);
		
		loadExercisePopupText = new Label("hello world");
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
		
		closeExercisePopupText = new Label("hello world2");
		closeExercisePopupBody.add(closeExercisePopupText);
		
		closeExercisePopupFooter = new ModalFooter();
		closeExercisePopup.add(closeExercisePopupFooter);
		
		closeExercisePopupCancelButton = new Button();
		closeExercisePopupCancelButton.addStyleName("fa fa-times");
		closeExercisePopupFooter.add(closeExercisePopupCancelButton);
		
		closeExercisePopupOkButton = new Button();
		closeExercisePopupOkButton.addStyleName("fa fa-check");
		closeExercisePopupFooter.add(closeExercisePopupOkButton);
		
		footerPanel = new FlowPanel();
		footerPanel.addStyleName("footerPanel");
		mainPanel.addSouth(footerPanel, 2);
		// hack to display dropup on top of rest of ui
		footerPanel.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		ioPanel = new SplitLayoutPanel(3);
		mainPanel.add(ioPanel);

		inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 0.45 * Window.getClientHeight());

		outputArea = new FlowPanel("div");
		outputArea.setWidth("100%");
		outputArea.addStyleName("output");
		ioPanel.add(outputArea);

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

		optionBarPanel = new FlowPanel();
		optionBarPanel.addStyleName("optionBarPanel");
		inputControlPanel.add(optionBarPanel);
		
		outputFormatBox = new ListBox();
		outputFormatBox.addItem("Unicode Output");
		outputFormatBox.addItem("Tree Output");
		optionBarPanel.add(outputFormatBox);

		reductionOrderBox = new ListBox();
		reductionOrderBox.setName("Reduction Order");
		ReductionOrders.all().stream().map(ReductionOrder::getName).forEach(reductionOrderBox::addItem);
		optionBarPanel.add(reductionOrderBox);

		outputSizeBox = new ListBox();
		outputSizeBox.setName("Output Size");
		OutputSizes.all().stream().map(OutputSize::getName).forEach(outputSizeBox::addItem);
		optionBarPanel.add(outputSizeBox);

		controlPanel = new FlowPanel();
		controlPanel.addStyleName("controlPanel");
		inputControlPanel.add(controlPanel);

		stepByStepControlPanel = new FlowPanel();
		stepByStepControlPanel.addStyleName("stepByStepControlPanel");
		controlPanel.add(stepByStepControlPanel);
		
		backwardsButton = new Button();
		backwardsButton.addStyleName("fa fa-chevron-left");
		stepByStepControlPanel.add(backwardsButton);

		stepByStepButton = new Button();
		stepByStepButton.addStyleName("fa fa-exchange");
		stepByStepControlPanel.add(stepByStepButton);

		forwardButton = new Button();
		forwardButton.addStyleName("fa fa-chevron-right");
		stepByStepControlPanel.add(forwardButton);

		runControlPanel = new FlowPanel();
		runControlPanel.addStyleName("runControlPanel");
		controlPanel.add(runControlPanel);
		
		cancelButton = new Button();
		cancelButton.addStyleName("fa fa-stop");
		runControlPanel.add(cancelButton);
		
		runButton = new Button();
		runButton.addStyleName("fa fa-play");
		runControlPanel.add(runButton);
		
		pauseButton = new Button();
		pauseButton.addStyleName("fa fa-pause");
		pauseButton.setVisible(false);
		runControlPanel.add(pauseButton);
		
		unpauseButton = new Button();
		unpauseButton.addStyleName("fa fa-play");
		unpauseButton.setVisible(false);
		runControlPanel.add(unpauseButton);

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
		exportArea.setVisibleLines(10);
		exportArea.setReadOnly(true);
		exportArea.setText("hello\n\tworld\n\t\teveryone");
		exportPopupBody.add(exportArea);
		
		exportPopupFooter = new ModalFooter();
		exportPopup.add(exportPopupFooter);
		
		exportPopupBodyOkButton = new Button();
		exportPopupBodyOkButton.addStyleName("fa fa-check");
		exportPopupFooter.add(exportPopupBodyOkButton);
		
		// exportPopup.show();
		
		// this only exists for style consistency with exportButton
		shareGroup = new ButtonGroup();
		footerPanel.add(shareGroup);
		
		sharePanel = new TextBox();
		sharePanel.addStyleName("sharePanel");
		sharePanel.setText("hello world");
		sharePanel.setReadOnly(true);
		sharePanel.setVisible(false);
		footerPanel.add(sharePanel);
		
		shareButton = new Button();
		shareButton.addStyleName("fa fa-share-alt");
		shareGroup.add(shareButton);
		
		
		LoadExercise loadExerciseAction = new LoadExercise();
		loadExercisePopupOkButton.addClickHandler(e -> loadExerciseAction.run());
		
		loadExercisePopupCancelButton.addClickHandler(e -> loadExercisePopup.hide());
		
		closeExercisePopupOkButton.addClickHandler(e -> new EnterDefaultMode());
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
		stepByStepButton.addClickHandler(e -> new StepByStep().run());
		forwardButton.addClickHandler(e -> new StepForward().run());
		cancelButton.addClickHandler(e -> new Stop().run());
		runButton.addClickHandler(e -> new RunNewExecution().run());
		unpauseButton.addClickHandler(e -> new UnpauseExecution().run());
		
		List<Export> exports = Exports.all();
		for (int i = 0; i < exports.size(); i++) {
			SelectExportFormat action = new SelectExportFormat(exports.get(i));
			exportButtons.get(i).addClickHandler(e -> action.run());
		}
		
		shareButton.addClickHandler(e -> new UseShare(null).run());
		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		editor = MonacoEditor.load(editorPanel);
		// executor = new Executor(Arrays.asList(new UpdateUnicodeOutput(), new UpdateTreeOutput()));
	}

	@Override
	public String serialize() {
		return null;
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

	public Button stepByStepButton() {
		return stepByStepButton;
	}

	public Button forwardButton() {
		return forwardButton;
	}

	public FlowPanel runControlPanel() {
		return runControlPanel;
	}

	public Button cancelButton() {
		return cancelButton;
	}

	public Button runButton() {
		return runButton;
	}

	public Button pauseButton() {
		return pauseButton;
	}

	public Button unpauseButton() {
		return unpauseButton;
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

}
