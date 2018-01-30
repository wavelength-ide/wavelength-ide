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
		String firstPredefinition = "";
		
		String secondName = "Exercise 02";
		String secondTask = "Another Dummy";
		String secondSolution = "Another missing solution";
		String secondPredefinition = "";
		
		Exercise firstExercise = new ConcreteExercise(firstName, firstTask, firstSolution, firstPredefinition);
		Exercise secondExercise = new ConcreteExercise(secondName, secondTask, secondSolution, secondPredefinition);
		
		ArrayList<Exercise> exerciseList= new ArrayList<Exercise>();
		exerciseList.add(firstExercise);
		exerciseList.add(secondExercise);
		
		return exerciseList;
	}
	
}
