package webdriver.ui;

import java.util.Arrays;
import java.util.List;

public final class Exercise {

	private Exercise() {
	}

	public static final String RandomNormalOrder = "β-Redex - Normal Order*";
	public static final String RandomCallByName = "β-Redex - Call by Name*";
	public static final String RandomCallByValue = "β-Redex - Call by Value*";
	public static final String RandomApplicativeOrder = "β-Redex - Applicative Order*";
	public static final String ExerciseMode = "Exercise mode";
	public static final String Variables1 = "Variables";
	public static final String Variables2 = "Variables II";
	public static final String NormalOrder = "Normal Order";
	public static final String ApplicativeOrder = "Applicative Order";
	public static final String CallByName = "Call-by-Name";
	public static final String CallByValue = "Call-by-Value";
	public static final String AlphaConversion = "α-conversion";

	public static final List<String> all() {
		return Arrays.asList(RandomNormalOrder, RandomCallByName, RandomCallByValue, RandomApplicativeOrder,
				ExerciseMode, Variables1, Variables2, NormalOrder, ApplicativeOrder, CallByName, CallByValue,
				AlphaConversion);
	}

}
