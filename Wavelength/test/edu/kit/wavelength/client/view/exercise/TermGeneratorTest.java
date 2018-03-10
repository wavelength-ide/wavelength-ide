package edu.kit.wavelength.client.view.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TermGeneratorTest {

	TermGenerator generator = new TermGenerator();
	
	@Test
	public void randomVarNameTest() {
		for (int i = 0; i < 1000; i++) {
			String name = generator.getRandomVarName();
			assertEquals(name.length(), 1);
			int nameValue = (int)name.charAt(0);
			assertTrue(nameValue <= (int) 'z');
			assertTrue(nameValue >= (int) 'a');
		}
	}
}
