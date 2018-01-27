package edu.kit.wavelength.client.view.webui.component;

import java.util.Iterator;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Lockable;

/**
 * This class wraps a GWT {@link DisclosurePanel}. It represents a Button that
 * slides open when clicked.
 */
public class DisclosureMenu implements Hideable, Lockable {

	private DisclosurePanel wrappedDisclosurePanel;
	private Panel contentPanel;
	private Boolean isLocked;

	/**
	 * Constructs a new DisclosureMenu. The content of the menu should already
	 * be added to it.
	 * 
	 * @param disclosurePanel
	 *            Panel to adapt (this is the "Button" with the sliding
	 *            mechanism itself - not the panel within it)
	 * @param content
	 *            the content of the DisclosuePanel that is shown when the Panel
	 *            is opened
	 */
	public DisclosureMenu(DisclosurePanel disclosurePanel, Panel content) {
		if (disclosurePanel == null) {
			throw new IllegalArgumentException("disclosurePanel must not be null");
		}
		if (content == null) {
			throw new IllegalArgumentException("contentPanel must not be null");
		}

		wrappedDisclosurePanel = disclosurePanel;
		contentPanel = content;
		isLocked = false;
	}

	@Override
	public void lock() {
		if (!isLocked) {
			enableAllChildren(false, contentPanel);
			isLocked = true;
		}
	}

	@Override
	public void unlock() {
		if (isLocked) {
			enableAllChildren(true, contentPanel);
			isLocked = false;
		}
	}

	@Override
	public boolean isLocked() {
		return isLocked;
	}

	@Override
	public void hide() {
		wrappedDisclosurePanel.setOpen(false);
	}

	@Override
	public void show() {
		wrappedDisclosurePanel.setOpen(true);
	}

	@Override
	public boolean isShown() {
		return wrappedDisclosurePanel.isOpen();
	}

	/**
	 * Locks or unlocks all children of a widget. This includes the children of
	 * a child widget.
	 * 
	 * @param enable
	 *            {@link true} if the widgets should be enabled {@link flas}
	 *            otherwise
	 * @param widget
	 *            the widget to disable its children
	 */
	private void enableAllChildren(boolean enable, Widget widget) {
		assert (widget != null);

		if (widget instanceof HasWidgets) {

			Iterator<Widget> iter = ((HasWidgets) widget).iterator();
			while (iter.hasNext()) {
				Widget nextWidget = iter.next();
				enableAllChildren(enable, nextWidget);
				if (nextWidget instanceof FocusWidget) {
					((FocusWidget) nextWidget).setEnabled(enable);
				}
			}
		}
	}
}
