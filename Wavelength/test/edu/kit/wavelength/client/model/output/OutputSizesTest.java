package edu.kit.wavelength.client.model.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutputSizesTest {

	
	
	private int[] nextRndInts() {
		Random rndm = new Random();
		int[] rndNumbers = new int[50];
		for (int i = 0; i < rndNumbers.length; i++) {
			rndNumbers[i] = Math.abs(rndm.nextInt());
		}
		return rndNumbers;
	}

	@Test
	public void allTest() {
		List<OutputSize> expectedList = new ArrayList<OutputSize>();
		expectedList.add(new Full());
		expectedList.add(new Periodic(50));
		expectedList.add(new Shortened(10));
		expectedList.add(new ResultOnly());
		List<OutputSize> all = OutputSizes.all();
		for (int i = 0; i < all.size(); i++) {
			assertEquals(expectedList.get(i), all.get(i));
		}
	}

	@Test
	public void nameTest() {
		assertEquals(new Full().getName(), "Full");
		assertEquals(new Periodic(50).getName(), "Periodically");
		assertEquals(new ResultOnly().getName(), "Result only");
		assertEquals(new Shortened(10).getName(), "Shortened");
	}

	@Test
	public void serializationTest() {
		String serial = new Full().serialize().toString();
		assertEquals(serial, "f");
		serial = new Periodic(50).serialize().toString();
		assertEquals(serial, "p50");
		serial = new ResultOnly().serialize().toString();
		assertEquals(serial, "r");
		serial = new Shortened(10).serialize().toString();
		assertEquals(serial, "s10");
	}

	@Test
	public void fullOutput() {
		Full out = new Full();
		int[] numbers = nextRndInts();
		for (int number : numbers) {
			assertEquals(out.displayLive(number), true);
			List<Integer> endTerms = out.displayAtEnd(number, number);
			assertEquals(endTerms.size(), 0);
		}
	}

	@Test
	public void resultOnlyOutput() {
		ResultOnly out = new ResultOnly();
		int[] numbers = nextRndInts();
		for (int number : numbers) {
			assertEquals(out.displayLive(number), false);
			List<Integer> endTerms = out.displayAtEnd(number, 0);
			assertEquals(endTerms.size(), 1);
		}
	}
	
	@Test
	public void liveDisplayTest() {
		int[] rndNumbers = nextRndInts(); 
		for (int cNumber: rndNumbers) {
			int param = new Random().nextInt(100) + 1;
			Periodic periodic = new Periodic(param);
			Shortened shortened = new Shortened(param);
			assertEquals(cNumber % param == 0, periodic.displayLive(cNumber));
			assertEquals(cNumber < param, shortened.displayLive(cNumber));
		}
	}
	
	public void atEndDisplayPeriodicTest() {
		int[] rndNumbers = nextRndInts();
		for (int cNumber: rndNumbers) {
			int param = new Random().nextInt(100) + 1;
			Periodic periodic = new Periodic(param);
			if (periodic.displayLive(cNumber) == true) {
				List<Integer> displaySteps = periodic.displayAtEnd(cNumber, cNumber);
			}
		}
	}
}
