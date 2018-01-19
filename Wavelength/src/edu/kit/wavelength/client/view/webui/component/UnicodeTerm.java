package edu.kit.wavelength.client.view.webui.component;

/**
 * Represents the visual display of a lambda term in text with unicode symbols.
 */
public class UnicodeTerm {
	
	private String repr;
	
	public UnicodeTerm(String repr) {
		this.repr = repr;
	}
	
	public String getRepresentation() {
		return this.repr;
	}


}
