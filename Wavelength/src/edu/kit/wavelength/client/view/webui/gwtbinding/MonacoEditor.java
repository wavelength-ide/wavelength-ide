package edu.kit.wavelength.client.view.webui.gwtbinding;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

public class MonacoEditor {
	
	private static native JavaScriptObject config() /*-{
		var c = {
			"value": "hello word",
			"automaticLayout": true,
			"minimap": {"enabled": false},
			"mouseWheelZoom": true
		};
		return c;
	}-*/;
	
	public static native void load(Panel parent) /*-{
		var id = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var c = @edu.kit.wavelength.client.view.webui.gwtbinding.MonacoEditor::config()();
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), c);
	}-*/;
}
