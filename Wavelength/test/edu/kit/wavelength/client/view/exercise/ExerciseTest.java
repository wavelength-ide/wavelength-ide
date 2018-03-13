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
		for (int i = 0; i < allExercises.size(); i++) {
			StringBuilder serial = allExercises.get(i).serialize();
			Exercise deserialized = Exercises.deserialize(serial.toString());
			assertEquals(deserialized.getName(), allExercises.get(i).getName());
		}
	}
	
	@Test
	public void resetTest() {
		RedexExercise ex = new RedexExercise((new NormalOrder()));
		ex.reset();
		Parser parser = new Parser(new ArrayList<Library>());
		
	}
	
	
}
