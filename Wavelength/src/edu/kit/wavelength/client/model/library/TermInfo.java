package edu.kit.wavelength.client.model.library;

/**
 * Contains the metadata of a term provided by a library.
 *
 */
public class TermInfo {

	/**
	 * Name of the term
	 */
	public final String name;
	/**
	 * Description of what the term does
	 */
	public final String description;
	
	/**
	 * Constructor
	 * @param name - Name of the term
	 * @param description - Description of what the term does
	 */
	public TermInfo(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
}
