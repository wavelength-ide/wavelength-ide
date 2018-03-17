package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class ReductionOrder {
	
	private ReductionOrder() {}
	
	public final static String NormalOrder = "Normal Order";
	public final static String CallByName = "Call by Name";
	public final static String CallByValue = "Call by Value";
	public final static String ApplicativeOrder = "Applicative Order";
	
	public static List<String> all() {
		return Arrays.asList(NormalOrder, CallByName, CallByValue, ApplicativeOrder);
	}
	
}
