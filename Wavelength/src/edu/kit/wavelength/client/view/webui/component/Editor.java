package edu.kit.wavelength.client.view.webui.component;

import edu.kit.wavelength.client.model.serialization.Serializable;
import edu.kit.wavelength.client.view.api.Lockable;
import edu.kit.wavelength.client.view.api.Readable;
import edu.kit.wavelength.client.view.api.Writable;
import edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor;

/**
 * This Editor is the main means for the user to input content into the
 * application.
 * 
 * The User can input the Terms he wants to calculate. In Addition text can be
 * written into the Editor to communicate with the User. The Editor can be
 * blocked too to prevent the User from changing its content.
 */
public class Editor implements Readable, Writable, Lockable, Serializable {

	private MonacoEditor e;
	
	public Editor(MonacoEditor e) {
		this.e = e;
	}
	
	/**
	 * Creates a new Editor from the specified serialized string.
	 * @param serialized serialized string, as created in serialize
	 * @return new Editor
	 */
	public static Editor deserialize(String serialized) {
		return null;
	}
	
	@Override
	public String read() {
		return e.read();
	}

	@Override
	public void write(String input) {
		e.write(input);
	}

	@Override
	public void lock() {
		e.lock();
	}

	@Override
	public void unlock() {
		e.unlock();
	}

	@Override
	public boolean isLocked() {
		return e.isLocked();
	}
	
	@Override
	public String serialize() {
		return null;
	}
}
