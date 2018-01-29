package edu.kit.wavelength.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercises;
import edu.kit.wavelength.client.view.export.Exports;
import edu.kit.wavelength.client.view.gwtbinding.MonacoEditor;

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

	public final DockLayoutPanel mainPanel;
	public final DisclosurePanel mainMenu;
	public final FlowPanel mainMenuPanel;
	public final Label libraryTitle;
	public final List<CheckBox> libraryCheckBoxes;
	public final Label exerciseTitle;
	public final Modal infoPopup;
	public final ModalBody infoPopupBody;
	public final Label infoPopupText;
	public final ModalFooter infoPopupFooter;
	public final Button infoPopupOkButton;
	public final Button infoPopupCancelButton;
	public final List<Button> exerciseButtons;
	public final FlowPanel footerPanel;
	public final SplitLayoutPanel ioPanel;
	public final DockLayoutPanel inputPanel;
	public final TextArea outputArea;
	public final DockLayoutPanel inputControlPanel;
	public final SplitLayoutPanel editorTaskPanel;
	public final FlowPanel taskPanel;
	public final FlowPanel taskHeaderPanel;
	public final FlowPanel taskControlPanel;
	public final Label taskDescriptionLabel;
	public final Button toggleSolutionButton;
	public final Button closeTaskButton;
	public final TextArea solutionArea;
	public final SimplePanel editorPanel;
	public final FlowPanel optionBarPanel;
	public final ListBox outputFormatBox;
	public final ListBox reductionOrderBox;
	public final ListBox outputSizeBox;
	public final FlowPanel controlPanel;
	public final FlowPanel stepByStepControlPanel;
	public final Button backwardsButton;
	public final Button stepByStepButton;
	public final Button forwardButton;
	public final FlowPanel runControlPanel;
	public final Button cancelButton;
	public final Button runButton;
	public final Button pauseButton;
	public final ButtonGroup exportDropupGroup;
	public final Button openExportMenuButton;
	public final DropDownMenu exportMenu;
	public final List<AnchorListItem> exportButtons;
	public final ButtonGroup shareGroup;
	public final TextBox sharePanel;
	public final Button shareButton;
	
	public final MonacoEditor editor;
	
	public final Executor executor = null;
	
	/**
	 * Initializes App.
	 */
	private App() {

		String state = Window.Location.getPath();
		// deserialize

		mainPanel = new DockLayoutPanel(Unit.EM);
		mainPanel.addStyleName("mainPanel");

		mainMenu = new DisclosurePanel("Options");
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

		mainMenuPanel = new FlowPanel();
		mainMenuPanel.addStyleName("mainMenuPanel");

		libraryTitle = new Label("Libraries");
		libraryTitle.addStyleName("menuTitle");
		mainMenuPanel.add(libraryTitle);
		libraryCheckBoxes = new ArrayList<>();
		Libraries.all().forEach(lib -> {
			CheckBox libraryBox = new CheckBox(lib.getName());
			libraryBox.addStyleName("libraryCheckBox");
			mainMenuPanel.add(libraryBox);
			libraryCheckBoxes.add(libraryBox);
		});

		exerciseTitle = new Label("Exercises");
		exerciseTitle.addStyleName("menuTitle");
		mainMenuPanel.add(exerciseTitle);

		infoPopup = new Modal();
		infoPopup.setClosable(false);
		infoPopup.setFade(true);
		infoPopup.setDataBackdrop(ModalBackdrop.STATIC);
		
		infoPopupBody = new ModalBody();
		infoPopup.add(infoPopupBody);
		
		infoPopupText = new Label("hello world");
		infoPopupBody.add(infoPopupText);
		
		infoPopupFooter = new ModalFooter();
		infoPopup.add(infoPopupFooter);
		
		infoPopupOkButton = new Button();
		infoPopupOkButton.addStyleName("fa fa-times");
		infoPopupFooter.add(infoPopupOkButton);
		
		infoPopupCancelButton = new Button();
		infoPopupCancelButton.addStyleName("fa fa-check");
		infoPopupFooter.add(infoPopupCancelButton);
		
		// infoPopup.show();
		
		exerciseButtons = new ArrayList<>();
		Exercises.all().forEach(excs -> {
			Button exerciseButton = new Button(excs.getName());
			// exerciseButton.addClickHandler(event -> new SelectExercise(excs).run());
			exerciseButton.addStyleName("exerciseButton");
			exerciseButton.setTitle(excs.getTask());
			mainMenuPanel.add(exerciseButton);
			exerciseButtons.add(exerciseButton);
		});
		
		mainMenu.setContent(mainMenuPanel);
		mainPanel.addNorth(mainMenu, 2.1);
		// hack to display menu on top of rest of ui
		mainMenu.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		footerPanel = new FlowPanel();
		footerPanel.addStyleName("footerPanel");
		mainPanel.addSouth(footerPanel, 2);
		// hack to display dropup on top of rest of ui
		footerPanel.getElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);

		ioPanel = new SplitLayoutPanel(3);
		mainPanel.add(ioPanel);

		inputPanel = new DockLayoutPanel(Unit.EM);
		ioPanel.addNorth(inputPanel, 400);

		outputArea = new TextArea();
		outputArea.setWidth("100%");
		ioPanel.add(outputArea);

		inputControlPanel = new DockLayoutPanel(Unit.PCT);
		inputPanel.addSouth(inputControlPanel, 1.85);

		editorTaskPanel = new SplitLayoutPanel(3);
		editorTaskPanel.addStyleName("editorTaskPanel");
		inputPanel.add(editorTaskPanel);
		
		taskPanel = new FlowPanel();
		taskPanel.addStyleName("taskPanel");
		editorTaskPanel.addEast(taskPanel, 400);
		
		taskHeaderPanel = new FlowPanel();
		taskPanel.add(taskHeaderPanel);
		
		taskControlPanel = new FlowPanel();
		taskControlPanel.addStyleName("taskControlPanel");
		taskHeaderPanel.add(taskControlPanel);
		
		taskDescriptionLabel = new HTML("hello world<br>hello world<br>hello world<br>");
		taskDescriptionLabel.addStyleName("taskDescriptionLabel");
		taskHeaderPanel.add(taskDescriptionLabel);
		
		toggleSolutionButton = new Button();
		toggleSolutionButton.addStyleName("fa fa-lightbulb-o");
		taskControlPanel.add(toggleSolutionButton);
		
		closeTaskButton = new Button();
		closeTaskButton.addStyleName("fa fa-times-circle-o");
		taskControlPanel.add(closeTaskButton);
		
		solutionArea = new TextArea();
		solutionArea.addStyleName("solutionArea");
		solutionArea.addStyleName("form-control");
		solutionArea.setVisible(false);
		solutionArea.setReadOnly(true);
		solutionArea.setText("hello\n\tworld\n\t\teveryone");
		toggleSolutionButton.addClickHandler(e -> solutionArea.setVisible(!solutionArea.isVisible()));
		taskPanel.add(solutionArea);
		
		editorPanel = new SimplePanel();
		// id needed because MonacoEditor adds to panel div by id
		editorPanel.getElement().setId("editor");
		editorTaskPanel.add(editorPanel);

		optionBarPanel = new FlowPanel();
		optionBarPanel.addStyleName("optionBarPanel");
		inputControlPanel.addWest(optionBarPanel, 50);
		
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
		inputControlPanel.addEast(controlPanel, 50);

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
		runControlPanel.add(pauseButton);

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
			AnchorListItem exportButton = new AnchorListItem(e.getName());
			exportMenu.add(exportButton);
			exportButtons.add(exportButton);
		});
		exportDropupGroup.add(exportMenu);
		
		// this only exists for style consistency with exportButton
		shareGroup = new ButtonGroup();
		footerPanel.add(shareGroup);
		
		sharePanel = new TextBox();
		sharePanel.setText("hello world");
		sharePanel.setReadOnly(true);
		sharePanel.setVisible(false);
		footerPanel.add(sharePanel);
		
		shareButton = new Button();
		shareButton.addClickHandler(event -> sharePanel.setVisible(!sharePanel.isVisible()));
		shareButton.addStyleName("fa fa-share-alt");
		shareGroup.add(shareButton);
		
		// ui needs to be created BEFORE loading the editor for the ids to exist
		RootLayoutPanel.get().add(mainPanel);
		editor = MonacoEditor.load(editorPanel);
		
		// set actions
		// ...
		
		// executor = new Executor(Arrays.asList(new UpdateUnicodeOutput(), new UpdateTreeOutput()));
	}

	@Override
	public String serialize() {
		return null;
	}
}
