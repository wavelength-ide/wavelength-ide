package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class OutputSize {

	private OutputSize() {}
	
	public static final String Full = "Full";
	public static final String ResultOnly = "Result only";
	public static final String Periodically = "Periodically";
	public static final String Shortened = "Shortened";
	
	public static List<String> all() {
		return Arrays.asList(Full, ResultOnly, Periodically, Shortened);
	}
	
}
