package edu.kit.wavelength.client.view.gwt;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

/**
 * Wrapper for the monaco-js library.
 * Provides a subset of functions of the library that is useful to the application.
 */
public class MonacoEditor {
	private JavaScriptObject editor;
	// keeping track of decorations to delete them when editing
	private JavaScriptObject decorations;
	
	private static final String introPredef = "--Welcome to Wavelength, the lambda calculus IDE. "
			+ "At its core Wavelength is a lamba calculus interpreter. "
			+ "You may enter any λ-term and reduce it down to its normal form using one of Wavelength's four reduction orders. "
			+ "By result only the final reduction result is displayed. To better understand the reduction process, "
			+ " you may select a different output size from the rightmost combobox. If you want even more transparency and control,"
			+ " Wavelength offers a step-by-step mode, enabling you to initiate every reduction yourself. "
			+ "If a redex is clicked in this mode, it is reduced no matter the active reduction order. "
			+ "Using the step-back button you may undo a past reduction. "
			+ "The reduced λ-term and all intermediate steps display in the output window can be exported in different formats."
			+ "These can be selected from the export-menu in the lower left corner and for example include Haskell- or Lisp-code. "
			+ "To ease working with complex terms, Wavelength includes a number of predefined libraries containing commonly used λ-terms "
			+ "like the Church encodings of Natural Numbers. " 
			+ "Wavelength also includes a set of exercises for you to complete.";
	
	private static native JavaScriptObject config() /*-{
		var c = {
			value: @edu.kit.wavelength.client.view.gwt.MonacoEditor::introPredef,
			// automatic editor resizing
			automaticLayout: true,
			// disable minimap since most scripts are small
			minimap: {"enabled": false},
			mouseWheelZoom: true,
			lineNumbersMinChars: 3,
			wordWrap: "on",
			theme: "lambdaTheme",
			language: "lambda",
			// adds space for error icons on the side
			glyphMargin: true
		};
		return c;
	}-*/;
	
	/**
	 * Loads the editor into the specified parent and creates a wrapper to control the editor through GWT.
	 * @param parent - parent to load into
	 * @return wrapper
	 */
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
			inherit: true,
			rules: [ 
		        { token: "comment", foreground: "008000" } 
			]
		});
		$wnd.monaco.editor.defineTheme("locked", {
		    base: 'vs',
		    inherit: true,
		    rules: [{ background: "fafafa" }],
		    colors: {
		        "editor.background": "#fafafa",
		    }
		});
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), c);
		var wrapper = @edu.kit.wavelength.client.view.gwt.MonacoEditor::new()();
		wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor = editor;
		editor.onDidChangeModelContent(function(event) {
			// remove errors when user starts entering text
			wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::unerror()();
		});
		return wrapper;
	}-*/;
	
	/**
	 * Reads the contents of the editor.
	 * @return contents
	 */
	public native String read() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getValue();
	}-*/;
	
	/**
	 * Writes the specified contents to the editor.
	 * @param s - string to replace editor content with
	 */
	public native void write(String s) /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.setValue(s);
	}-*/;
	
	/**
	 * Disables editor input.
	 */
	public native void lock() /*-{
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		c.readOnly = true;
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.updateOptions(c);
		$wnd.monaco.editor.setTheme("locked"); 
	}-*/;
	
	/**
	 * Enables editor input.
	 */
	public native void unlock() /*-{
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		c.readOnly = false;
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		e.updateOptions(c);
		$wnd.monaco.editor.setTheme("lambdaTheme"); 
	}-*/;
	
	/**
	 * Checks whether the editor is editable.
	 * @return whether the editor is editable
	 */
	public native boolean isLocked() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getConfiguration().readOnly;
	}-*/;
	
	/**
	 * Displays an error in the editor.
	 * @param message - message of the error
	 * @param startLineNumber - start line number position of the error
	 * @param endLineNumber - end line number position of the error
	 * @param startColumn - start column number position of the error
	 * @param endColumn - end column number position of the error
	 */
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
		// adds icon on the left
		this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::decorations = e.deltaDecorations([], [
			{
				range: new $wnd.monaco.Range(startLineNumber, startColumn, endLineNumber, endColumn),
				options: {
					isWholeLine: true,
					glyphMarginClassName: 'editorErrorGlyphMargin',
					glyphMarginHoverMessage: message
				}
			}
		]);
	}-*/;
	
	/**
	 * Removes all error indicators from the editor.
	 */
	public native void unerror() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		$wnd.monaco.editor.setModelMarkers(e.getModel(), "", [])
		var decorations = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::decorations;
		if (decorations !== undefined) {
			e.deltaDecorations(decorations, []);
		}
	}-*/;
}
