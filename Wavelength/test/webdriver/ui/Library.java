package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class Library {
	
	private Library() {}
	
	public static final String AcceleratedNaturalNumbers = "Accelerated Natural Numbers";
	public static final String NaturalNumbers = "Natural Numbers";
	public static final String ChurchBooleans = "Church Boolean";
	public static final String ChurchLists = "Church Tuples and Lists";
	public static final String YCombinator = "Y-Combinator";
	
	public static List<String> all() {
		return Arrays.asList(AcceleratedNaturalNumbers, NaturalNumbers, ChurchBooleans, ChurchLists, YCombinator);
	}
	
}
