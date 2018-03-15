package edu.kit.wavelength.client.view.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.reduction.NormalOrder;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class ExerciseTest {

	@Test
	public void concreteExerciseTest() {
		Exercise ex = new ConcreteExercise("name","task","solution","predefinition");
		assertEquals(ex.getName(), "name");
		assertEquals(ex.getTask(), "task");
		assertEquals(ex.getSolution(), "solution");
		assertTrue(ex.hasPredefinitions());
		assertEquals(ex.getPredefinitions(), "predefinition");
	}
	
	@Test
	public void noPredefTest() {
		Exercise ex = new ConcreteExercise("name", "task", "solution", "");
		assertEquals(ex.getName(), "name");
		assertEquals(ex.getTask(), "task");
		assertEquals(ex.getSolution(), "solution");
		assertFalse(ex.hasPredefinitions());
		assertEquals(ex.getPredefinitions(), "");
	}
	
	@Test
	public void concreteExerciseSerialization() {
		Exercise ex = new ConcreteExercise("name", "task", "solution", "predefinitions");
		StringBuilder serial = ex.serialize();
		assertEquals(serial.toString(), "name");
	}

	@Test
	public void exerciseSerialization() {
		List<Exercise> allExercises = Exercises.all();
		for (int i = 4; i < allExercises.size(); i++) {
			StringBuilder serial = allExercises.get(i).serialize();
			Exercise deserialized = Exercises.deserialize(serial.toString());
			assertEquals(deserialized.getName(), allExercises.get(i).getName());
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDeserializationTest() {
		Exercises.deserialize("unknownExerciseName");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyDeserializationTest() {
		Exercises.deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullDeserializationTest() {
		Exercises.deserialize(null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void nullNameTest() {
		Exercise ex = new ConcreteExercise(null, "task", "solution", "predefinition");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullTaskTest() {
		Exercise ex = new ConcreteExercise("name", null, "solution", "predefinition");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullSolutionTest() {
		Exercise ex = new ConcreteExercise("name", "task", null, "predefinition");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullPredefinitionTest() {
		Exercise ex = new ConcreteExercise("name", "task", "solution", null);
	}
	
	
}
