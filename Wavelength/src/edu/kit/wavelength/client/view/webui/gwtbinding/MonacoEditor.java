package edu.kit.wavelength.client.view.webui.gwtbinding;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

public class MonacoEditor {
	// Readable, Writable, Lockable, Serializable
	private JavaScriptObject editor;
	
	private static native JavaScriptObject config() /*-{
		var c = {
			"value": "hello word",
			"automaticLayout": true,
			"minimap": {"enabled": false},
			"mouseWheelZoom": true
		};
		return c;
	}-*/;
	
	public static native MonacoEditor load(Panel parent) /*-{
		var id = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var c = @edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::config()();
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), c);
		var wrapper = @edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::new()();
		wrapper.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor = editor;
		return wrapper;
	}-*/;
	
	public native String read() /*-{
		var e = this.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor;
		return e.getValue();
	}-*/;
	
	public native void write(String s) /*-{
		var e = this.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor;
		e.setValue(s);
	}-*/;
	
	public native void lock() /*-{
		var c = @edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::config()();
		c.readOnly = true;
		var e = this.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor;
		e.updateOptions(c);
	}-*/;
	
	public native void unlock() /*-{
		var c = @edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::config()();
		c.readOnly = false;
		var e = this.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor;
		e.updateOptions(c);
	}-*/;
	
	public native boolean isLocked() /*-{
		var e = this.@edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::editor;
		return e.getConfiguration().readOnly;
	}-*/;
}
