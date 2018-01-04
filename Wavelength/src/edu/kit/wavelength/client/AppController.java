package edu.kit.wavelength.client;

import java.util.List;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.state.AppState;
import edu.kit.wavelength.client.view.Output;

/**
 * This class handles the current state of the application and the currently
 * displayed output. The initial state is the Input state and the output is
 * empty when the application is started.
 */
public class AppController implements UIState {

	private AppState state;
	private List<LambdaTerm> currentOutput;
	private Output outputFormat;

	/**
	 * Constructs a new AppController object. The initial state is Input and the
	 * output is set empty.
	 */
	public AppController() {
		// state = new Input();
	}

	/**
	 * This method gets called when the reduction process starts. It delegates the
	 * handling to the current state.
	 */
	public void start() {
		// state.start();
	}

	/**
	 * This method gets called when the reduction process stops. It delegates the
	 * handling to the current state.
	 */
	public void stop() {
		// state.stop();
	}

	/**
	 * This method gets called when the reduction process is paused. It delegates
	 * the handling to the current state.
	 */
	public void pause() {
		// state.pause();
	}

	/**
	 * This method gets called when an exercise is selected. It delegates the
	 * handling to the current state.
	 */
	public void enterExercise() {
		// state.enterExercise();
	}

	/**
	 * This method gets called when the user exits the exercise mode. It delegates
	 * the handling to the current state.
	 */
	public void exitExercise() {
		// state.exitExercise();
	}
	
	public Output getOutputFormat() {
		return null;
	}
	
	public void setOutputFormat(Output format) {
		
	}
	
	

	/**
	 * Adds a lambda term to the current output.
	 */
	@Override
	public void addTerm(LambdaTerm term) {
		// add term to end of currentOutput
	}

	/**
	 * Removes the last lambda term from the current output.
	 */
	@Override
	public void popTerm() {
		// pop last term in currentOutput
	}

	/**
	 * Empties the current output.
	 */
	@Override
	public void clearOutput() {
		// currentOutput = new List<LambdaTerm>();
	}
}
