package edu.kit.wavelength.client.view;

import java.util.List;

import edu.kit.wavelength.client.action.Action;

/**
 * A OptionBox provides a means for the User to set Options for a calculation.
 * 
 * It is represented as a ListBox. This Box can be blocked and unblocked if
 * changing the Options must not be possible.
 */
public class OptionBox implements Blockable {

	@Override
	public void block() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unblock() {
		// TODO Auto-generated method stub

	}

	/**
	 * Constructs a new OptionBox
	 * 
	 * @param labels
	 *            A List containing the names of the Options this OptionBox
	 *            provides
	 * @param actions
	 *            A List containing the Actions that are being performed when
	 *            one of the labels are clicked. It must have the same order as
	 *            the List providing the labels.
	 */
	public OptionBox(final List<String> labels, final List<Action> actions) {
		// TODO was wird hier übergeben?
	}
}
