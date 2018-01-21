package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.view.action.Action;
import edu.kit.wavelength.client.view.api.Clickable;
import edu.kit.wavelength.client.view.api.Hideable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;

/**
 * The PopUpTextBox is an adapter class for the GWT {@link DialogBox}
 * 
 * It is represented by a PopUpWindow that holds a TextField and possibly some
 * Buttons (e.g. a close Button). It can be hidden or shown. Content can be
 * written and read from the TextField.
 */
public class PopUpWindow implements Writable, Hideable, Readable, Clickable {

	private DialogBox wrappedDialogBox;
	private TextArea textArea;
	private Button actionButton;
	private Button closeButton;
	private HandlerRegistration currentEvent;

	/**
	 * Creates a new and empty {@link DialogBox}. It consists of a
	 * {@link TextArea} and two {@link Button}s. One Button is for closing the
	 * DialogBox and the other is for performing an action.
	 * 
	 * The close Button will always close the DialogBox. The Action performed by
	 * the other Button can be set by the {@link setAction()} method.
	 * 
	 * @param dialogBox
	 *            the wrapped {@link DialogBox}
	 * @param textArea
	 *            the {@link TextArea} of the DialogBox
	 * @param actionButton
	 *            the Button that performs the action dedicated to the DialogBox
	 * @param closeButton
	 *            the Button that will close the DialogBox
	 */
	public PopUpWindow(final DialogBox dialogBox, final TextArea textArea, final Button actionButton,
			final Button closeButton) {
		wrappedDialogBox = dialogBox;
		this.textArea = textArea;
		this.actionButton = actionButton;
		this.closeButton = closeButton;
		/*
		 * TODO wie viel Einfluss will die App Initialisierung auf die
		 * Gestaltung des PopUpWindow haben Soll die die DialogBox
		 * zusammengesetzt werden? Dann muss auch weniger im Konstruktor
		 * übergeben werden.
		 */
	}

	/*
	 * TODO was soll man machen, wenn man nicht nur das Textfeld, sondern auch
	 * die Bezechnung des anderen Knopfes ändern will? Der Fall tritt momentan
	 * nicht auf und es kann alles über die Initialisierung in der App Klasse
	 * geregelt werden.
	 */
	@Override
	public void write(String input) {
		textArea.setText(input);
	}

	@Override
	public void hide() {
		wrappedDialogBox.hide();
	}

	@Override
	public void show() {
		wrappedDialogBox.center();
		wrappedDialogBox.show();
	}

	@Override
	public boolean isShown() {
		return wrappedDialogBox.isShowing();
	}

	@Override
	public String read() {
		return textArea.getText();
	}

	@Override
	public void setAction(Action action) {
		if (currentEvent != null) {
			currentEvent.removeHandler();
		}
		currentEvent = actionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				action.run();
			}
		});
	}
}
