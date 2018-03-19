package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OutputSizesTest {

	int testIterations = 1000;
	
	OutputSize shortened;
	OutputSize periodic;
	OutputSize full;
	OutputSize resultOnly;
	
	Random random = new Random();
	
	@Before
	public void setUp() {
		shortened = new Shortened(20);
		periodic = new Periodic(5);
		full = new Full();
		resultOnly = new ResultOnly();
		random = new Random();
	}
	
	@Test
	public void allTest() {
		List<OutputSize> expectedList = new ArrayList<OutputSize>();
		expectedList.add(new ResultOnly());
		expectedList.add(new Shortened(10));
		expectedList.add(new Periodic(50));
		expectedList.add(new Full());
		List<OutputSize> all = OutputSizes.all();
		for (int i = 0; i < all.size(); i++) {
			assertEquals(expectedList.get(i), all.get(i));
		}
	}
	

	@Test
	public void serializationTest() {		
		String fullSerial = full.serialize().toString();
		assertEquals(OutputSizes.deserialize(fullSerial), full);		
		String resultOnlySerial = resultOnly.serialize().toString();
		assertEquals(OutputSizes.deserialize(resultOnlySerial), resultOnly);		
		String periodicSerial = periodic.serialize().toString();
		assertEquals(OutputSizes.deserialize(periodicSerial), periodic);
		String shortenedSerial = shortened.serialize().toString();
		assertEquals(OutputSizes.deserialize(shortenedSerial), shortened);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullDeserializationTest() {
		OutputSizes.deserialize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyDeserializationTest() {
		OutputSizes.deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidDeserializationTest() {
		OutputSizes.deserialize("i");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidPeriodTest() {
		new Periodic(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCutoffTest() {
		new Shortened(0);
	}
	
	@Test
	public void equalsTrueTest() {
		OutputSize full = new Full();
		OutputSize otherFull = new Full();
		OutputSize resultOnly = new ResultOnly();
		OutputSize otherResultOnly = new ResultOnly();
		OutputSize shortened = new Shortened(10);
		OutputSize otherShortened = new Shortened(10);
		OutputSize periodic = new Periodic(10);
		OutputSize otherPeriodic = new Periodic(10);
		assertTrue(full.equals(full));
		assertTrue(full.equals(otherFull));
		assertTrue(resultOnly.equals(resultOnly));
		assertTrue(resultOnly.equals(otherResultOnly));
		assertTrue(shortened.equals(shortened));
		assertTrue(shortened.equals(otherShortened));
		assertTrue(periodic.equals(periodic));
		assertTrue(periodic.equals(otherPeriodic));
	}
	
	@Test
	public void equalsFalseTest() {
		OutputSize shortened = new Shortened(10);
		OutputSize otherShortened = new Shortened(32); 
		OutputSize periodic = new Periodic(10);
		OutputSize otherPeriodic = new Periodic(32);
		assertFalse(shortened.equals(otherShortened));
		assertFalse(periodic.equals(otherPeriodic));
	}
	
	@Test
	public void equalsOthersFalseTest() {
		OutputSize full = new Full();
		OutputSize resultOnly = new ResultOnly();
		OutputSize shortened = new Shortened(10);
		OutputSize periodic = new Periodic(10);
		assertFalse(full.equals(resultOnly));
		assertFalse(full.equals(shortened));
		assertFalse(full.equals(periodic));
		
		assertFalse(resultOnly.equals(full));
		assertFalse(resultOnly.equals(shortened));
		assertFalse(resultOnly.equals(periodic));
		
		assertFalse(shortened.equals(full));
		assertFalse(shortened.equals(resultOnly));
		assertFalse(shortened.equals(periodic));
		
		assertFalse(periodic.equals(full));
		assertFalse(periodic.equals(resultOnly));
		assertFalse(periodic.equals(shortened));
	}
	
	@Test
	public void numToPreserveTest() {
		assertEquals(new Periodic(5).numToPreserve(), 1);
		assertEquals(new Shortened(5).numToPreserve(), 5);
		assertEquals(new Full().numToPreserve(), 1);
		assertEquals(new ResultOnly().numToPreserve(), 1);
	}

	@Test
	public void nameTest() {
		assertEquals(new Full().getName(), "Full");
		assertEquals(new Periodic(50).getName(), "Periodically");
		assertEquals(new ResultOnly().getName(), "Result only");
		assertEquals(new Shortened(10).getName(), "Shortened");
	}

	@Test
	public void fullOutput() {
		Full out = new Full();
		for (int i = 0; i < testIterations; i++) {
			int cNumber = new Random().nextInt(10000);
			assertEquals(out.displayLive(cNumber), true);
			List<Integer> endTerms = out.displayAtEnd(cNumber, cNumber);
			assertEquals(endTerms.size(), 0);
		}
	}

	@Test
	public void resultOnlyOutput() {
		ResultOnly out = new ResultOnly();
		for (int i = 0; i < testIterations; i++) {
			int cNumber = new Random().nextInt(10000) + 1;
			assertEquals(out.displayLive(cNumber), false);
			List<Integer> endTerms = out.displayAtEnd(cNumber, 0);
			assertEquals(endTerms.size(), 1);
		}
	}
	
	@Test
	public void liveDisplayTest() {
		for (int i = 0; i < testIterations; i++) {
			int cNumber = new Random().nextInt(10000);
			int param = new Random().nextInt(100) + 1;
			Periodic periodic = new Periodic(param);
			Shortened shortened = new Shortened(param);
			assertEquals(cNumber % param == 0, periodic.displayLive(cNumber));
			assertEquals(cNumber < param, shortened.displayLive(cNumber));
		}
	}
	
	@Test
	public void shortenedAtEndTest() {
		for (int i = 0; i < testIterations; i++) {
			periodic = new Periodic(random.nextInt(Integer.MAX_VALUE) + 1);
			int totalSteps = random.nextInt(Integer.MAX_VALUE);
			int cutoff = random.nextInt(999) + 1; 
			shortened = new Shortened(cutoff);
			for (int j = 0; j < 100; j++) {
				int lastDisplayed = random.nextInt(totalSteps + 1 - cutoff) + cutoff;
				List<Integer> atEnd = shortened.displayAtEnd(totalSteps, lastDisplayed);
				int counter = 0;
				for (Integer step: atEnd) {
					assertFalse(shortened.displayLive(step));
					counter++;
				}
				assertTrue(counter <= cutoff);
			}
		}
	}
	
	@Test
	public void periodicAtEndEqualParamTest() {
		for (int i = 0; i < testIterations; i++) {
			int totalSteps = random.nextInt(Integer.MAX_VALUE);
			assertEquals(0, periodic.displayAtEnd(totalSteps, totalSteps).size());
		}
	}
	
	@Test
	public void periodicAtEndDifferenceParamTest() {
		for (int i = 0; i < testIterations; i++) {
			periodic = new Periodic(random.nextInt(Integer.MAX_VALUE) + 1);
			int totalSteps = random.nextInt(Integer.MAX_VALUE);
			int lastDisplayed = random.nextInt(totalSteps);
			assertEquals(1, periodic.displayAtEnd(totalSteps, lastDisplayed).size());
			assertEquals(totalSteps, periodic.displayAtEnd(totalSteps, lastDisplayed).get(0).intValue());
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void periodicAtEndDisplayInvalidInputTest() {
		periodic.displayAtEnd(-1, 5);
	}
}
