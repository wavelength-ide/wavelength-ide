package edu.kit.wavelength.client.view.gwt;

import java.util.List;
import edu.kit.wavelength.client.model.library.Library;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Panel;

/**
 * Wrapper for the monaco-js library. Provides a subset of functions of the
 * library that is useful to the application.
 */
public class MonacoEditor {
	private JavaScriptObject editor;
	// keeping track of things to delete them when editing
	private JavaScriptObject decorations;
	private JavaScriptObject completionItemProvider;

	private static final String introPredef = 
			  "--  __\n"
			+ "--  \\ \\\n"
			+ "--   ⟩ \\            Welcome to Wavelength, the λ-calculus IDE.\n"
			+ "--  / ^ \\\n"
			+ "-- /_/ \\_\\\n"
			+ "--\n"
			+ "-- Try pressing the fast-forward button (>>|) on the right or Ctrl+Enter to see a reduction of the following term:\n"
			+ "\n"
			+ "(\\m. \\n. \\s. \\z. m (n s) z) (\\s. \\z. s (s z)) (\\s. \\z. s (s (s z)))\n"
			+ "\n"
			+ "-- The drop-downs on the left allow you to choose how the reduction steps are rendered, how they are reduced, and which steps are displayed.\n"
			+ "-- While execution is paused, you can click on the last result in the lower panel to choose which subexpression to reduce next.\n"
			+ "-- You can also use the chevron buttons (< and >) to step through a reduction.\n"
			+ "-- The clear button on the right (x next to >>|) or Ctrl+Backspace will immediately empty the output.\n"
			+ "--\n"
			+ "-- Wavelength allows you to define names for λ-terms like so:\n"
			+ "--\n"
			+ "-- id = λx. x\n"
			+ "-- id (λs.λz. z)\n"
			+ "--\n"
			+ "-- We provide several libraries out of the box. These, and some introductory exercises and examples, can be found in the menu on the top left.\n"
			+ "-- The contents of any enabled libraries will also show up in the auto-completion of the editor that can be opened via Ctrl+Space.\n"
			+ "-- Finally, you can generate a permalink to your work or export your output via the buttons on the lower left.\n"
			+ "-- Have fun!\n";

	private static native JavaScriptObject config() /*-{
		var c = {
			value : @edu.kit.wavelength.client.view.gwt.MonacoEditor::introPredef,
			// automatic editor resizing
			automaticLayout : true,
			// disable minimap since most scripts are small
			minimap : {
				"enabled" : false
			},
			mouseWheelZoom : true,
			lineNumbersMinChars : 3,
			wordWrap : "on",
			theme : "lambdaTheme",
			language : "lambda",
			quickSuggestions : false,
			// adds space for error icons on the side
			glyphMargin : true
		};
		return c;
	}-*/;

	public native void setLibraries(List<Library> libraries) /*-{
		function uniqBy(a, key) {
			var seen = {};
			return a.filter(function(item) {
				var k = key(item);
				return seen.hasOwnProperty(k) ? false : (seen[k] = true);
			});
		}
		var items = [];
		for (var i = 0; i < libraries.@java.util.List::size()(); i++) {
			var lib = libraries.@java.util.List::get(I)(i);
			var infos = lib.@edu.kit.wavelength.client.model.library.Library::getTermInfos()();
			for (var j = 0; j < infos.@java.util.List::size()(); j++) {
				var info = infos.@java.util.List::get(I)(j);
				var name = info.@edu.kit.wavelength.client.model.library.TermInfo::name;
				var desc = info.@edu.kit.wavelength.client.model.library.TermInfo::description;
				items.push({
					label : name,
					kind : $wnd.monaco.languages.CompletionItemKind.Function,
					detail : desc
				});
			}
		}
		items = uniqBy(items, function(item) {
			return item.label;
		});
		var completionItemProvider = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::completionItemProvider;
		if (completionItemProvider !== undefined) {
			completionItemProvider.dispose();
		}
		this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::completionItemProvider = $wnd.monaco.languages
				.registerCompletionItemProvider("lambda", {
					provideCompletionItems : function(model, position) {
						return items;
					}
				});
	}-*/;

	/**
	 * Loads the editor into the specified parent and creates a wrapper to control
	 * the editor through GWT.
	 * 
	 * @param parent
	 *            - parent to load into
	 * @return wrapper
	 */
	public static native MonacoEditor load(Panel parent) /*-{
		var id = parent.@com.google.gwt.user.client.ui.Panel::getElement()().@com.google.gwt.dom.client.Element::getId()();
		var c = @edu.kit.wavelength.client.view.gwt.MonacoEditor::config()();
		$wnd.monaco.languages.register({
			id : "lambda"
		});
		$wnd.monaco.languages.setLanguageConfiguration("lambda", {
			brackets : [ [ "(", ")" ] ],
			comments : {
				lineComment : "--"
			}
		});
		$wnd.monaco.languages.setMonarchTokensProvider("lambda", {
			tokenizer : {
				root : [ [ "--.*$", "comment" ] ]
			}
		});

		$wnd.monaco.editor.defineTheme("lambdaTheme", {
			base : "vs",
			inherit : true,
			rules : [ {
				token : "comment",
				foreground : "008000"
			} ]
		});
		$wnd.monaco.editor.defineTheme("locked", {
			base : 'vs',
			inherit : true,
			rules : [ {
				background : "fafafa"
			} ],
			colors : {
				"editor.background" : "#fafafa",
			}
		});
		var editor = $wnd.monaco.editor.create($doc.getElementById(id), c);
		var wrapper = @edu.kit.wavelength.client.view.gwt.MonacoEditor::new()();
		wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor = editor;
		editor
				.onDidChangeModelContent(function(event) {
					// remove errors when user starts entering text
					wrapper.@edu.kit.wavelength.client.view.gwt.MonacoEditor::unerror()();
				});
		return wrapper;
	}-*/;

	/**
	 * Reads the contents of the editor.
	 * 
	 * @return contents
	 */
	public native String read() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getValue();
	}-*/;

	/**
	 * Writes the specified contents to the editor.
	 * 
	 * @param s
	 *            - string to replace editor content with
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
	 * 
	 * @return whether the editor is editable
	 */
	public native boolean isLocked() /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		return e.getConfiguration().readOnly;
	}-*/;

	/**
	 * Displays an error in the editor.
	 * 
	 * @param message
	 *            - message of the error
	 * @param startLineNumber
	 *            - start line number position of the error
	 * @param endLineNumber
	 *            - end line number position of the error
	 * @param startColumn
	 *            - start column number position of the error
	 * @param endColumn
	 *            - end column number position of the error
	 */
	public native void error(String message, int startLineNumber, int endLineNumber, int startColumn,
			int endColumn) /*-{
		var e = this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::editor;
		var marker = {
			severity : $wnd.monaco.Severity.Error,
			startLineNumber : startLineNumber,
			endLineNumber : endLineNumber,
			startColumn : startColumn,
			endColumn : endColumn,
			message : message
		};
		$wnd.monaco.editor.setModelMarkers(e.getModel(), "", [ marker ]);
		// adds icon on the left
		this.@edu.kit.wavelength.client.view.gwt.MonacoEditor::decorations = e
				.deltaDecorations([], [ {
					range : new $wnd.monaco.Range(startLineNumber, startColumn,
							endLineNumber, endColumn),
					options : {
						isWholeLine : true,
						glyphMarginClassName : 'editorErrorGlyphMargin',
						glyphMarginHoverMessage : message
					}
				} ]);
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
