package edu.kit.wavelength.client.view;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.execution.Executor;
import edu.kit.wavelength.client.view.exercise.Exercise;

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
	 * Gets the option box that allows the user to choose which reduction order
	 * to use.
	 * 
	 * @return The reduction order option box
	 */
	public ListBox reductionOrderBox() {
		return reductionOrder;
	}

	/**
	 * Gets the option box that allows the user to choose which output size to
	 * use.
	 * 
	 * @return The output size option box
	 */
	public ListBox outputSizeBox() {
		return outputSize;
	}

	/**
	 * Gets the button that can be used to play back to the previous displayed
	 * term.
	 * 
	 * @return The step backward button
	 */
	public Button stepBackwardButton() {
		return stepBackward;
	}

	/**
	 * Gets the button that can be used to initiate step by step reduction
	 * before execution.
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
	 * @return The Button used for showing the current {@link Exercise}'s
	 *         solution
	 */
	public Button showSolutionButton() {
		return this.showSolution;
	}

	/**
	 * Gets the Button that can be used for hiding the solution of the current
	 * {@link Exercise}.
	 * 
	 * @return The Button used for hiding the current {@link Exercise}'s
	 *         solution
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


	}

	@Override
	public String serialize() {
		return null;
	}
}
