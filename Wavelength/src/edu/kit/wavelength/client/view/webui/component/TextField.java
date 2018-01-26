package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.api.Readable;

/**
 * A TextField is an adapter class for a GWT {@link TextArea}.
 * 
 * A TextField can only be read. It provides a means to display text to the User
 * and can be hidden and shown.
 */
public class TextField implements Hideable, Writable, Readable {

	private TextArea wrappedTextArea;

	/**
	 * Constructs a new and empty TextField.
	 * 
	 * @param textField
	 *            The GWT TextArea that this class wraps
	 */
	public TextField(TextArea textField) {
		if (textField == null){
			throw new IllegalArgumentException("textField must not be null");
		}
		
		wrappedTextArea = textField;
	}

	@Override
	public void write(String input) {
		wrappedTextArea.setText(input);
	}

	@Override
	public void hide() {
		wrappedTextArea.setVisible(false);
	}

	@Override
	public void show() {
		wrappedTextArea.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return wrappedTextArea.isVisible();
	}

	@Override
	public String read() {
		return wrappedTextArea.getText();
	}
}
