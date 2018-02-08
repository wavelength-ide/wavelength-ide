package edu.kit.wavelength.client.view.gwt;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

public class MonacoEditor {
	// Readable, Writable, Lockable, Serializable
	private JavaScriptObject editor;
	private JavaScriptObject decorations;
	
	private static native JavaScriptObject config() /*-{
		var c = {
			value: "(\\x.x) x",
			automaticLayout: true,
			minimap: {"enabled": false},
			mouseWheelZoom: true,
			lineNumbersMinChars: 3,
			wordWrap: "on",
			theme: "lambdaTheme",
			language: "lambda",
			glyphMargin: true
		};
		return c;
	}-*/;
	
	public static native MonacoEditor load(Panel parent) /*-{
		var id = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		$wnd.monaco.languages.register({ id: "lambda" });
		$wnd.monaco.languages.setLanguageConfiguration("lambda", {
			brackets: [["(", ")"]],
			comments: {
				lineComment: "--"
			}
		});
		$wnd.monaco.languages.setMonarchTokensProvider("lambda", {
			tokenizer: {
				root: [
		            ["--.*$", "comment"] 
				]
			}
		});
		$wnd.monaco.editor.defineTheme("lambdaTheme", {
			base: "vs",
			inherit: false,
			rules: [ 
		        { token: "comment", foreground: "008000" } 
			]
		});
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), c);
		var wrapper = @edu.kit.wavelength.client.view.gwt.MonacoEditor::new()();
		wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor = editor;
		editor.onDidChangeModelContent(function(event) {
			wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::unerror()();
		});
		return wrapper;
	}-*/;
	
	public native String read() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getValue();
	}-*/;
	
	public native void write(String s) /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.setValue(s);
	}-*/;
	
	public native void lock() /*-{
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		c.readOnly = true;
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.updateOptions(c);
	}-*/;
	
	public native void unlock() /*-{
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		c.readOnly = false;
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.updateOptions(c);
	}-*/;
	
	public native boolean isLocked() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getConfiguration().readOnly;
	}-*/;
	
	public native void error(String message, int startLineNumber, int endLineNumber, int startColumn, int endColumn) /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		var marker = {
			severity: $wnd.monaco.Severity.Error,
			startLineNumber: startLineNumber,
			endLineNumber: endLineNumber,
			startColumn: startColumn,
			endColumn: endColumn,
			message: message
		};
		$wnd.monaco.editor.setModelMarkers(e.getModel(), "", [marker]);
		this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::decorations = e.deltaDecorations([], [
			{
				range: new $wnd.monaco.Range(startLineNumber, startColumn, endLineNumber, endColumn),
				options: {
					isWholeLine: true,
					className: 'editorErrorContent',
					glyphMarginClassName: 'editorErrorGlyphMargin',
					glyphMarginHoverMessage: message
				}
			}
		]);
	}-*/;
	
	public native void unerror() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		$wnd.monaco.editor.setModelMarkers(e.getModel(), "", [])
		var decorations = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::decorations;
		if (decorations !== undefined) {
			e.deltaDecorations(decorations, []);
		}
	}-*/;
}
