package edu.kit.wavelength.client.view.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.kit.wavelength.client.model.reduction.TestTermGenerator;

public class TermGeneratorTest {

	TermGenerator generator = new TestTermGenerator();
	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidParamsA() {
		generator.getNewTerm(10, 5, 0, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidParamsB() {
		generator.getNewTerm(5, 15, -1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidParamsC() {
		generator.getNewTerm(5, 15, 0, -1);
	}
	
	@Test
	public void invalidParamsD() {
		
	}
}
