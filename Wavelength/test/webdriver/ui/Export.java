package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class Export {

	private Export() {}
	
	public static final String Plaintext = "Plaintext";
	public static final String Unicode = "Unicode";
	public static final String LaTeX = "LaTeX";
	public static final String Haskell = "Haskell";
	public static final String Lisp = "Lisp";
	
	public static List<String> all() {
		return Arrays.asList(Plaintext, Unicode, LaTeX, Haskell, Lisp);
	}
	
}
