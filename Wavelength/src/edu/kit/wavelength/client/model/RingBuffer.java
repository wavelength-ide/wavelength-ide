package edu.kit.wavelength.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.model.serialization.SerializationUtilities;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * A simple ring buffer of lambda terms.
 *
 */
final class RingBuffer implements Serializable {

	private ArrayList<LambdaTerm> elements;
	private int size;
	
	private static final char EXISTS_INDICATOR = 'e';
	private static final char NULL_INDICATOR = 'n';

	/**
	 * Creates a new ring buffer of a given size.
	 * @param size The size of the ring buffer
	 */
	public RingBuffer(int size) {
		if (size <= 0)
			throw new IllegalArgumentException("The ring buffer needs to have at least one element.");

		this.elements = new ArrayList<>(Collections.nCopies(size, null));
		this.size = size;
	}
	
	/**
	 * Restores a serialized ring buffer.
	 * @param serialized The serialized ring buffer
	 */
	public RingBuffer(String serialized) {
		List<String> extracted = SerializationUtilities.extract(serialized);
		assert extracted.size() > 0;
		size = extracted.size();
		elements = new ArrayList<>(Collections.nCopies(size, null));
		
		for (int i = 0; i < size; ++i) {
			String extr = extracted.get(i);
			assert extr.length() > 0;
			
			if (extr.charAt(0) == EXISTS_INDICATOR) {
				elements.set(i, LambdaTerm.deserialize(extr.substring(1)));
			}
		}
	}

	/**
	 * Gets the lambda term at the given position in the ring buffer.
	 * @param index The position to access
	 * @return The lambda term at the specified position
	 */
	public LambdaTerm get(int index) {
		if (index < 0)
			throw new IllegalArgumentException("Index needs to be non-negative");

		return elements.get(index % size);
	}

	/**
	 * Sets the lambda term at the given position in the ring buffer.
	 * @param index The position to access
	 * @param value The lambda term to set
	 */
	public void set(int index, LambdaTerm value) {
		if (index < 0)
			throw new IllegalArgumentException("Index needs to be non-negative");
		
		elements.set(index % size,  value);
	}

	@Override
	public StringBuilder serialize() {
		ArrayList<StringBuilder> all = new ArrayList<>();
		for (int i = 0; i < size; ++i) {
			if (elements.get(i) == null) {
				all.add(new StringBuilder("" + NULL_INDICATOR));
			} else {
				all.add(new StringBuilder("" + EXISTS_INDICATOR).append(elements.get(i).serialize()));
			}
		}
		return SerializationUtilities.enclose(all.toArray(new StringBuilder[0]));
	}
}
