package edu.kit.wavelength.client.action;

import edu.kit.wavelength.client.UIState;
import edu.kit.wavelength.client.view.Hideable;
import edu.kit.wavelength.client.view.Writable;

public class Share<T extends Hideable & Writable> implements Action {

	private UIState state;
	private T panel;

	public Share(UIState state, T panel) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
