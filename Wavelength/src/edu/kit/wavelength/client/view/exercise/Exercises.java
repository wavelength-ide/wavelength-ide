package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Static class giving access to all available exercises.
 *
 */
public final class Exercises {

	/**
	 * Returns an unmodifiable list of all available exercises.
	 * 
	 * @return An unmodifiable list that contains exactly one instance of every
	 *         available exercise
	 */
	public static List<Exercise> all() {
		String firstName = "Exercise 01";
		String firstTask = "Dummy Exercise";
		String firstSolution = "Dummy Solution";
		String firstpredefinition = "";
		
		Exercise firstExercise = new ConcreteExercise(firstName, firstTask, firstSolution, firstpredefinition);
		
		ArrayList<Exercise> exerciseList= new ArrayList<Exercise>();
		exerciseList.add(firstExercise);
		
		return exerciseList;
	}
	
}
