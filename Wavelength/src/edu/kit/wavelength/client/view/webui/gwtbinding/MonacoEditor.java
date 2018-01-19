package edu.kit.wavelength.client.view.webui.gwtbinding;

import com.google.gwt.user.client.ui.Panel;

public class MonacoEditor {
	public static native void load(Panel parent) /*-{
		var id = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), {
			value: [
				'function x() {',
				'\tconsole.log("Hello world!");',
				'}'
			].join('\n'),
			language: 'javascript',
			automaticLayout: true
		});
	}-*/;
}
