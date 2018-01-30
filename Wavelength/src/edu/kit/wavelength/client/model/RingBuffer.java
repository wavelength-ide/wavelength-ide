package edu.kit.wavelength.client.model;

import java.util.ArrayList;
import java.util.Collections;

final class RingBuffer<T> {

	private ArrayList<T> elements;
	private int size;

	public RingBuffer(int size) {
		if (size <= 0)
			throw new IllegalArgumentException("The ring buffer needs to have at least one element.");

		this.elements = new ArrayList<T>(Collections.nCopies(size, null));
		this.size = size;
	}

	public T get(int index) {
		if (index < 0)
			throw new IllegalArgumentException("Index needs to be non-negative");

		return elements.get(index % size);
	}

	public void set(int index, T value) {
		if (index < 0)
			throw new IllegalArgumentException("Index needs to be non-negative");
		
		elements.set(index % size,  value);
	}
}
