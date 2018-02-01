package edu.kit.wavelength.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BetaReducer;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

/**
 * An execution engine manages the reduction of a {@link LambdaTerm}. It keeps
 * the history of terms and which of these terms were displayed and is able to
 * reduce the current term according to a {@link ReductionOrder} or reduce a
 * specific redex in the current term. It also keeps track of which terms should
 * be displayed and is able to revert to the previous displayed term.
 * 
 */
public class ExecutionEngine {

	private ReductionOrder order;
	private OutputSize size;

	private ArrayList<NumberedTerm> shown;
	private RingBuffer<LambdaTerm> current;
	private int currentNum, lastDisplayedNum;

	/**
	 * Creates a new execution engine.
	 * 
	 * @param input
	 *            The textual representation of a {@link LambdaTerm} to be handled
	 * @param order
	 *            The {@link ReductionOrder} to be used by default
	 * @param size
	 *            The {@link OutputSize} to be used
	 * @param libraries
	 *            The {@link Libraries} to be taken into consideration during
	 *            parsing
	 */
	public ExecutionEngine(String input, ReductionOrder order, OutputSize size, List<Library> libraries) throws ParseException {
		Parser p = new Parser(libraries);

		this.current = new RingBuffer<LambdaTerm>(size.numToPreserve());
		this.current.set(0, p.parse(input));
		this.size = size;
		this.order = order;
		this.shown = new ArrayList<>();

		this.currentNum = 0;
		this.lastDisplayedNum = 0;
	}

	private List<LambdaTerm> pushTerm(Application redex, boolean displayOverride) {

		LambdaTerm newTerm = current.get(currentNum).acceptVisitor(new BetaReducer(redex));
		++currentNum;

		current.set(currentNum, newTerm);

		List<NumberedTerm> result = new ArrayList<NumberedTerm>();

		if (displayOverride || size.displayLive(currentNum)) {
			result.add(new NumberedTerm(current.get(currentNum), currentNum));
		}

		if (isFinished()) {
			result.addAll(size.displayAtEnd(currentNum, lastDisplayedNum).stream()
					.map(i -> new NumberedTerm(current.get(i), i)).collect(Collectors.toList()));
		}

		shown.addAll(result);

		return result.stream().map(t -> t.getTerm()).collect(Collectors.toList());
	}

	/**
	 * Executes a single reduction of the current {@link LambdaTerm}.
	 * 
	 * @return The lambda terms that should be displayed as a result of this step
	 */
	public List<LambdaTerm> stepForward() {
		if (isFinished())
			throw new IllegalStateException("The reduction order does not provide any more terms.");

		Application nextTerm = order.next(current.get(currentNum));
		return pushTerm(nextTerm, false);
	}

	/**
	 * Executes a single reduction of the supplied {@link Application}.
	 * 
	 * @param redex
	 *            The {@link Application} to be evaluated. Must be a redex,
	 *            otherwise an exception is thrown
	 */
	public List<LambdaTerm> stepForward(Application redex) {
		return pushTerm(redex, true);
	}

	/**
	 * Determines whether the execution is finished according to the current
	 * {@link ReductionOrder}.
	 * 
	 * @return {@code true} if the current {@link ReductionOrder} does not provide
	 *         another redex, {@code false} otherwise
	 */
	public boolean isFinished() {
		return order.next(current.get(currentNum)) == null;
	}
	
	public boolean canStepBackward() {
		return shown.size() >= 2;
	}

	/**
	 * Reverts to the previously output {@link LambdaTerm}.
	 */
	public void stepBackward() {
		// This code is buggy. Can you spot it?
		
		if (!canStepBackward())
			throw new IllegalStateException("Not enough terms have been shown to step backwards");
		
		shown.remove(shown.size() - 1);
		NumberedTerm target = shown.get(shown.size() - 1);
		currentNum = target.getNumber();
		lastDisplayedNum = currentNum;
		current.set(currentNum, target.getTerm());
	}

	/**
	 * Returns a list of all {@link LambdaTerm}s that have been displayed.
	 * 
	 * @return A list of all {@link LambdaTerm}s that have been displayed
	 */
	public List<LambdaTerm> getDisplayed() {
		return Collections.unmodifiableList(shown.stream().map(NumberedTerm::getTerm).collect(Collectors.toList()));
	}

	/**
	 * Returns the last {@link LambdaTerm} that has been displayed.
	 * 
	 * @return The last {@link LambdaTerm} that has been displayed
	 */
	private LambdaTerm getLast() {
		return shown.get(shown.size() - 1).getTerm();
	}
	
	public boolean isCurrentDisplayed() {
		return lastDisplayedNum == currentNum;
	}

	/**
	 * Displays the currently reduced {@link LambdaTerm}, adding it to the list of
	 * displayed {@link LambdaTerm}.
	 * 
	 * @return the current {@link LambdaTerm}
	 */
	public LambdaTerm displayCurrent() {
		if (isCurrentDisplayed())
			throw new IllegalStateException("No term to show");

		shown.add(new NumberedTerm(current.get(currentNum), currentNum));
		lastDisplayedNum = currentNum;
		return getLast();
	}

	/**
	 * Changes the active {@link ReductionOrder} to the entered one.
	 * 
	 * @param reduction
	 *            The new {@link ReductionOrder}
	 */
	public void setReductionOrder(ReductionOrder reduction) {
		this.order = reduction;
	}

	/**
	 * Serializes the ExecutionEngine by serializing its current {@link OutputSize},
	 * {@link ReductionOrder} and the terms it holds.
	 * 
	 * @return The ExecutionEngine's serialized String representation
	 */
	public String serialize() {
		return null;
	}
}
