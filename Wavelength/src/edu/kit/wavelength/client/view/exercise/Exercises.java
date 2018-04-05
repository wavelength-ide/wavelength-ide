package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;

/**
 * Static class giving access to all available exercises.
 *
 */
public final class Exercises {
	interface ExerciseData extends ClientBundle {
		ExerciseData instance = GWT.create(ExerciseData.class);
		  
		@Source("/exerciseData.txt")
		TextResource exerciseData();
	}
	

	private static final String EXERCISE_SEPARATOR_PREFIX = "=====";
	private static final String SECTION_SEPARATOR_PREFIX = "-----";
	  
	/* format of an exercise:
	 * ============
	 * My Exercise
	 * ----------- task description
	 * In this exercise, define some lambda-terms or something
	 * ----------- solution
	 * (\x. x)
	 * ----------- template text
	 * (YOUR AD HERE)
	 */
	
	private static boolean isSeparator(String line) {
		return line.startsWith(EXERCISE_SEPARATOR_PREFIX) || line.startsWith(SECTION_SEPARATOR_PREFIX);
	}
  
	private static ArrayList<Exercise> parseData() {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();

		String[] contents = ExerciseData.instance.exerciseData().getText().split("\\n");
		int i = 0;
		while (i < contents.length) {
			// parse Name, Task Description, Solution, and Template Text
			String[] sections = new String[4];
			for (int sec = 0; sec < 4; sec++) {
				assert isSeparator(contents[i]);
				i++;
				StringBuilder str = new StringBuilder();
				while (i < contents.length && !isSeparator(contents[i])) {
					str.append(contents[i] + "\n");
					i++;
				}
				sections[sec] = str.toString();
			}
			exercises.add(new ConcreteExercise(sections[0], sections[1], sections[2], sections[3]));
		}
		return exercises;
	}

	/**
	 * Returns an unmodifiable list of all available exercises.
	 * 
	 * @return An unmodifiable list that contains exactly one instance of every
	 *         available exercise
	 */
	public static List<Exercise> all() {

		ArrayList<Exercise> exerciseList = parseData();

		List<ReductionOrder> reductions = ReductionOrders.all();
		for (ReductionOrder cOrder : reductions) {
			exerciseList.add(new RedexExercise(cOrder));
		}

		return exerciseList;
	}

	/**
	 * Deserializes an {@link Exercise}.
	 * 
	 * @param serialized
	 *            supposedly a serialization of an Exercise
	 * @return Exercise belonging to serialization
	 * @throws IllegalArgumentException
	 *             if serialized is {@code null} or empty or exercise could not be
	 *             found
	 */
	public static Exercise deserialize(String serialized) {
		if (serialized == null || serialized.isEmpty()) {
			throw new IllegalArgumentException("This is not a valid serialization of an Exercise!");
		}
		for (Exercise exercise : Exercises.all()) {
			if (exercise.getName().equals(serialized)) {
				return exercise;
			}
		}
		throw new IllegalArgumentException("Exercise not found!");

	}
}
