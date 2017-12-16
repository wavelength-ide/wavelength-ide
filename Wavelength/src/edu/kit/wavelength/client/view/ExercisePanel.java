package edu.kit.wavelength.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;

/**
 * An ExercisePanel displays an {@link Exercise}.
 * 
 * ExercisePanels consist of a {@link Panel} taskPanel showing the current task and a {@link Button} solutionButton used to
 *  show or hide the current task´s solution.
 * The solution is shown in a {@link Panel} solutionPanel.
 * The solutionButton is labeled with either "show solution" or "hide solution" 
 *  depending on whether the solution is currently hidden or shown.
 * 
 * @author Muhammet Gümüs
 *
 */
public class ExercisePanel implements ExerciseView {
	
	private Button solutionButton;
	private Panel taskPanel;
	private Panel solutionPanel;
	
	@Override
	public void setTask(String task) {
		
	}

	@Override
	public void setSolution(String solution) {
		
	}

	/**
	 * Hides the solutionPanel.
	 * Changes the label of the solutionButton to "show solution".
	 */
	public void hideSolution() {
		
	}

	/**
	 * Shows the solutionPanel.
	 * Changes the label of the solutionButton to "hide solution".
	 */
	public void showSolution() {
		
	}
	
}
