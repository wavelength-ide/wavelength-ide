package edu.kit.wavelength.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BetaReducer;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.TermNotAcceptableException;
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
	private RingBuffer current;

	private int currentNum;
	private int lastDisplayedNum;
	private ArrayList<Library> libraries;

	private static final int ORDER_POSITION = 0;
	private static final int SIZE_POSITION = 1;
	private static final int SHOWN_POSITION = 2;
	private static final int CURRENT_POSITION = 3;
	private static final int CURRENT_NUM_POSITION = 4;
	private static final int LAST_DISPLAYED_NUM_POSITION = 5;
	private static final int LIBRARIES_POSITION = 6;
	private static final int TOTAL_SERIALIZATION_SIZE = 7;

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
	public ExecutionEngine(String input, ReductionOrder order, OutputSize size, List<Library> libraries)
			throws ParseException {
		Parser p = new Parser(libraries);

		this.libraries = new ArrayList<>(libraries);
		this.libraries.add(p.getInputLibary());

		this.current = new RingBuffer(size.numToPreserve());
		this.current.set(0, p.parse(input));
		this.size = size;
		this.order = order;
		this.shown = new ArrayList<>();
		shown.add(new NumberedTerm(current.get(0), 0));

		this.currentNum = 0;
		this.lastDisplayedNum = 0;
	}

	/**
	 * Instantiates a new ExecutionEngine from its serialization.
	 * 
	 * @param serialized
	 *            A serialized ExecutionEngine
	 */
	public ExecutionEngine(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() == TOTAL_SERIALIZATION_SIZE;

		this.order = ReductionOrders.deserialize(extracted.get(ORDER_POSITION));
		this.size = OutputSizes.deserialize(extracted.get(SIZE_POSITION));
		this.shown = new ArrayList<>(
				SerializationUtilities.deserializeList(extracted.get(SHOWN_POSITION), NumberedTerm::new));
		this.current = new RingBuffer(extracted.get(CURRENT_POSITION));
		this.currentNum = Integer.valueOf(extracted.get(CURRENT_NUM_POSITION));
		this.lastDisplayedNum = Integer.valueOf(extracted.get(LAST_DISPLAYED_NUM_POSITION));
		this.libraries = new ArrayList<>(
				SerializationUtilities.deserializeList(extracted.get(LIBRARIES_POSITION), Libraries::deserialize));
	}

	/**
	 * Returns the libraries that have been used by the execution engine.
	 * 
	 * @return The libraries that have been used by the execution engine
	 */
	public List<Library> getLibraries() {
		// Make sure the user cannot edit our libraries
		return Collections.unmodifiableList(libraries);
	}

	// Reduces the given redex in the current term, adds the result to the correct
	// internal
	// structures and returns which terms should be displayed
	private List<LambdaTerm> pushTerm(Application redex, boolean displayOverride) throws ExecutionException {

		// Reduce
		LambdaTerm newTerm;
		try {
			newTerm = current.get(currentNum).acceptVisitor(new BetaReducer(redex));
		} catch (TermNotAcceptableException ex) {
			++currentNum;
			current.set(currentNum, null);
			throw new ExecutionException(ex.getMessage());
		}

		++currentNum;
		current.set(currentNum, newTerm);

		List<NumberedTerm> result = new ArrayList<NumberedTerm>();

		// Show the term live
		if (displayOverride || size.displayLive(currentNum)) {
			result.add(new NumberedTerm(current.get(currentNum), currentNum));
			lastDisplayedNum = currentNum;
		}

		if (isFinished()) {
			// We need the last few terms that have been reduced, which we get from current
			result.addAll(size.displayAtEnd(currentNum, lastDisplayedNum).stream()
					.map(i -> new NumberedTerm(current.get(i), i)).collect(Collectors.toList()));
		}

		shown.addAll(result);
		lastDisplayedNum = shown.get(shown.size() - 1).getNumber();

		return result.stream().map(t -> t.getTerm()).collect(Collectors.toList());
	}

	/**
	 * Executes a single reduction of the current {@link LambdaTerm}.
	 * 
	 * @return The lambda terms that should be displayed as a result of this step
	 */
	public List<LambdaTerm> stepForward() throws ExecutionException {
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
	public List<LambdaTerm> stepForward(Application redex) throws ExecutionException {
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
		return current.get(currentNum) == null || order.next(current.get(currentNum)) == null;
	}

	public boolean canStepBackward() {
		return shown.size() >= 2;
	}

	/**
	 * Reverts to the previously output {@link LambdaTerm}.
	 */
	public void stepBackward() {
		if (!canStepBackward())
			throw new IllegalStateException("Not enough terms have been shown to step backwards");

		if (current.get(currentNum) != null) {
			shown.remove(shown.size() - 1);
		}
		
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
	 * displayed {@link LambdaTerm}s.
	 * 
	 * @return the current {@link LambdaTerm}
	 */
	public LambdaTerm displayCurrent() {
		if (current.get(currentNum) == null || isCurrentDisplayed())
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
	 * Changes the active {@link OutputSize} to the entered one.
	 * 
	 * In general, this will not be possible seamlessly. Instead, it will just apply
	 * to the future.
	 * 
	 * @param size
	 *            The new output size
	 */
	public void setOutputSize(OutputSize size) {
		this.size = size;
		RingBuffer newBuffer = new RingBuffer(size.numToPreserve());
		newBuffer.set(this.currentNum, this.current.get(this.currentNum));

		// The effect of this line is that only terms which have been produced after
		// changing the output size are considered.
		this.lastDisplayedNum = Math.max(this.lastDisplayedNum, this.currentNum - 1);
		this.current = newBuffer;
	}

	/**
	 * Returns the number of the current step in the computation.
	 * 
	 * @return The number of the current step in the computation
	 */
	public int getStepNumber() {
		return this.currentNum;
	}

	/**
	 * Serializes the ExecutionEngine by serializing its current {@link OutputSize},
	 * {@link ReductionOrder}, {@link Libraries} and the terms it holds.
	 * 
	 * @return The ExecutionEngine's serialized String representation
	 */
	public StringBuilder serialize() {
		return SerializationUtilities.enclose(order.serialize(), size.serialize(),
				SerializationUtilities.serializeList(shown, NumberedTerm::serialize), current.serialize(),
				new StringBuilder(String.valueOf(currentNum)), new StringBuilder(String.valueOf(lastDisplayedNum)),
				SerializationUtilities.serializeList(libraries, Library::serialize));
	}
}