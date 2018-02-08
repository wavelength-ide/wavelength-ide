package edu.kit.wavelength.client.model.term;

import java.util.ArrayList;

// An array list that is indexed 1-based and right-to-left.
final class DeBruijnList<T> {
	ArrayList<T> elements;

	public DeBruijnList() {
		elements = new ArrayList<>();
	}

	public void push(T element) {
		elements.add(element);
	}

	public void pop() {
		elements.remove(elements.size() - 1);
	}

	public T get(int index) {
		if (index < 1 || index > elements.size())
			throw new IllegalArgumentException("index must be in [1, size]");

		return elements.get(elements.size() - index);
	}

	public int size() {
		return elements.size();
	}
}
