package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Image;

import edu.kit.wavelength.client.view.api.Deactivatable;

/**
 * This class wraps GWT's {@link DisclosurePanel}.
 * 
 * A PopUpPanel is represented by an icon that pops up a menu on click.
 * The icon depends on whether the Panel is deactivated or not according to the {@link Deactivatable} interface.
 */
public class PopUpPanel implements Deactivatable {

	/**
	 * Creates a new PopUpPanel.
	 * @param panel the wrapped {@link PopUpPanel}
	 * @param imageWhenUnblocked icon shown when this is not blocked
	 * @param imageWhenBlocked icon shown when this is blocked
	 */
	public PopUpPanel(final DisclosurePanel panel, final Image imageWhenUnblocked, final Image imageWhenBlocked) {
		
	}
	@Override
	public void block() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unblock() {
		// TODO Auto-generated method stub
		
	}

}
