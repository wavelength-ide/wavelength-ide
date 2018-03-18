package edu.kit.wavelength.client.model;

/**
 * Thrown when an error during an operation on a
 * lambda term occurrs.
 *
 */
@SuppressWarnings("serial")
public final class ExecutionException extends Exception {
	public ExecutionException(String message) {
		super(message);
	}
}
