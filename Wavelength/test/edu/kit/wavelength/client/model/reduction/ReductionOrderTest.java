package edu.kit.wavelength.client.model.reduction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;

public class ReductionOrderTest {

	ReductionOrder normal = new NormalOrder();
	ReductionOrder applicative = new ApplicativeOrder();
	ReductionOrder byValue = new CallByValue();
	ReductionOrder byName = new CallByName();
	
	@Test
	public void freeVariableTest() {
		FreeVariable free = new FreeVariable("v");
		assertEquals(normal.next(free), null);
		assertEquals(applicative.next(free), null);
		assertEquals(byValue.next(free), null);
		assertEquals(byName.next(free), null);
	}
	
	@Test
	public void boundVariableTest() {
		BoundVariable bound = new BoundVariable(1);
		assertEquals(normal.next(bound), null);
		assertEquals(applicative.next(bound), null);
		assertEquals(byValue.next(bound), null);
		assertEquals(byName.next(bound), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermNormalTest() {
		normal.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermApplicativeTest() {
		applicative.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermByValueTest() {
		byValue.next(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullTermByNameTest() {
		byName.next(null);
	}
}
