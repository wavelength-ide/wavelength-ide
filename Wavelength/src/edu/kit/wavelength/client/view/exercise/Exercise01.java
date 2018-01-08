package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Example Exercise
 */
public class Exercise01 implements Exercise {

	public Exercise01() {

	}

	@Override
	public String getName() {
		return "Exercise 01";
	}
	
	@Override
	public String getTask() {
		return "Implement the identity function and apply it on 5.";
	}

	@Override
	public String getSolution() {
		return "(\\x.x)(5)";
	}

	@Override
	public boolean hasPredefinitions() {
		return false;
	}

	@Override
	public String getPredefinitions() {
		return null;
	}

	@Override
	public Iterable<String> getTestCases() {
		List<String> testCases = new ArrayList<String>();
		testCases.add("5");
		return testCases;
	}

}
