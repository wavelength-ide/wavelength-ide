package edu.kit.wavelength.client.model.term;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Test;

public class AcceleratedMethodsTest {
	
	private void assertRepresents(int i, LambdaTerm t) {
		assertTrue(t.acceptVisitor(new IsChurchNumberVisitor()));
		assertEquals(new Integer(i), t.acceptVisitor(new GetChurchNumberVisitor()));
	}
	
	private LambdaTerm apply(PartialApplication app, int arg1, int arg2) {
		LambdaTerm interm = apply(app, arg1);
		assertThat(interm, instanceOf(PartialApplication.class));
		return apply((PartialApplication)interm, arg2);
	}
	
	private LambdaTerm apply(PartialApplication app, int arg) {
		return app.accept(LambdaTerm.churchNumber(arg));
	}

	@Test
	public void additionTest() {
		PartialApplication.Addition base = new PartialApplication.Addition();
		assertRepresents(5, apply(base, 2, 3));
		assertRepresents(0, apply(base, 0, 0));
		assertRepresents(10, apply(base, 0, 10));
	}
	
	@Test
	public void successorTest() {
		PartialApplication.Successor base = new PartialApplication.Successor();
		assertRepresents(6, apply(base, 5));
		assertRepresents(1, apply(base, 0));
		assertRepresents(101, apply(base, 100));
	}
	
	@Test
	public void multiplicationTest() {
		PartialApplication.Multiplication base = new PartialApplication.Multiplication();
		assertRepresents(6, apply(base, 2, 3));
		assertRepresents(0, apply(base, 100, 0));
		assertRepresents(0, apply(base, 0, 0));
		assertRepresents(20, apply(base, 10, 2));
	}
	
	@Test
	public void exponentiationTest() {
		PartialApplication.Exponentiation base = new PartialApplication.Exponentiation();
		assertRepresents(8, apply(base, 2, 3));
		assertRepresents(1, apply(base, 1, 0));
		assertRepresents(0, apply(base, 0, 1));
		
		// Forget mathematics, this needs to match what the non-accelerated term produces.
		assertRepresents(1, apply(base, 0, 0));
	}
	
	@Test
	public void predecessorTest() {
		PartialApplication.Predecessor base = new PartialApplication.Predecessor();
		assertRepresents(5, apply(base, 6));
		assertRepresents(0, apply(base, 1));
		assertRepresents(0, apply(base, 0));
	}
	
	@Test
	public void subtractionTest() {
		PartialApplication.Subtraction base = new PartialApplication.Subtraction();
		assertRepresents(1, apply(base, 3, 2));
		assertRepresents(0, apply(base, 2, 3));
		assertRepresents(0, apply(base, 20, 20));
		assertRepresents(5, apply(base, 10, 5));
	}

}
