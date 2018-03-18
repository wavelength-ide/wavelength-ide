package edu.kit.wavelength.client.model.term;

/**
 * Thrown when attempting to construct a lambda term that
 * exceeds the maximum allowed size or depth.
 *
 */
@SuppressWarnings("serial")
public final class TermNotAcceptableException extends RuntimeException {
	public TermNotAcceptableException(String message) {
		super(message);
	}
}
