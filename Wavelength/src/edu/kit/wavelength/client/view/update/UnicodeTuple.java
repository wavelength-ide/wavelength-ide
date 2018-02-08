package edu.kit.wavelength.client.view.update;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This class represents a tuple of a gwt FlowPanel widget and a gwt anchor
 * widget.
 */
public class UnicodeTuple {

	public final FlowPanel panel;
	public final Anchor anchor;

	/**
	 * Creates a new tuple with the given parameters.
	 * 
	 * @param panel
	 *            The panel of this tuple
	 * @param a
	 *            The anchor of this tuple
	 */
	public UnicodeTuple(FlowPanel panel, Anchor a) {
		this.panel = panel;
		this.anchor = a;
	}

}
