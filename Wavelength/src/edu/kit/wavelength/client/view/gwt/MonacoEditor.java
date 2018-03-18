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
	
	private static final String introPredef =
			  "-- __\n"
			+ "-- \\ \\\n"
			+ "--  \\ \\            Welcome to Wavelength, the λ-calculus IDE.\n"
			+ "--   > \\\n"
			+ "--  / ^ \\\n"
			+ "-- /_/ \\_\\\n"
			+ "--\n"
			+ "-- At its core, Wavelength is a lambda calculus interpreter.  You may enter any\n"
			+ "-- λ-term and reduce it down to its normal form using one of Wavelength's four\n"
			+ "-- reduction orders. For example, try uncommenting the following line and pressing\n"
			+ "-- the fast-forward button on the right:\n"
			+ "--\n"
			+ "-- (λm.λn.λs.λz. m (n s) z) (λs.λz.s (s z)) (λs.λz.s (s (s z)))\n"
			+ "--\n"
			+ "-- Of course, you can also use backslashes instead of lambda symbols:\n"
			+ "--\n"
			+ "-- (\\m.\\n.\\s.\\z. m (n s) z) (\\s.\\z.s (s z)) (\\s.\\z.s (s (s z)))\n"
			+ "--\n"
			+ "-- For large computations, it may be desirable not to see all reductions steps\n"
			+ "-- that are performed. Using the rightmost combobox, you can adjust which\n"
			+ "-- reductions steps are shown.\n"
			+ "--\n"
			+ "-- If you want even more transparency and control, Wavelength offers a step by\n"
			+ "-- step mode, allowing you to initiate each reduction yourself. By simply clicking\n"
			+ "-- on it, you can select a redex to be reduced. You can also click on the\n"
			+ "-- step-forward button to let the current reduction order decide which redex to\n"
			+ "-- reduce next. The step-back button jumps back to the last step.\n"
			+ "--\n"
			+ "-- Using the combobox on the left, you can also choose to show λ-terms as trees\n"
			+ "-- instead of text. Don't overdo it though, you have been warned.\n"
			+ "--\n"
			+ "-- Wavelength allows you to bind λ-terms to names like so:\n"
			+ "--\n"
			+ "-- id = λx. x\n"
			+ "-- id (λs.λz. z)\n"
			+ "--\n"
			+ "-- To make common tasks easier, Wavelength provides several libraries out of the\n"
			+ "-- box. Simply select the libraries you like from the hamburger menu on the top\n"
			+ "-- left and you are good to go. At the same place, you can find a set of exercises\n"
			+ "-- to deepen your understanding of λ-calculus.\n"
			+ "--\n"
			+ "-- If you want to keep or share your work, you can choose to either export your\n"
			+ "-- results to one of several different formats or generate a shareable link that\n"
			+ "-- will bring you right back to where you left off. Both features can be found in\n"
			+ "-- the lower left of the application.";
	
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
