package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class OutputFormat {
	
	private OutputFormat() {}
	
	public static final String Unicode = "Unicode Output";
	public static final String Tree = "Tree Output";
	
	public static List<String> all() {
		return Arrays.asList(Unicode, Tree);
	}
	
}
