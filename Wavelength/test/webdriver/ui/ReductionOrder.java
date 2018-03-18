package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class ReductionOrder {
	
	private ReductionOrder() {}
	
	public static final String NormalOrder = "Normal Order";
	public static final String CallByName = "Call by Name";
	public static final String CallByValue = "Call by Value";
	public static final String ApplicativeOrder = "Applicative Order";
	
	public static List<String> all() {
		return Arrays.asList(NormalOrder, CallByName, CallByValue, ApplicativeOrder);
	}
	
}
