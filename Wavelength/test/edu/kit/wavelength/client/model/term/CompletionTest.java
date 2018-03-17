package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CompletionTest {

	final LambdaTerm idRedex = new Application(new Abstraction("x", new BoundVariable(1)), new FreeVariable("y"));
	
	@Test
	public void isRedexVisitorTest() {
		IsRedexVisitor isRedex = new IsRedexVisitor();
		assertFalse(new Abstraction("x", new BoundVariable(1)).acceptVisitor(isRedex));
		assertFalse(new BoundVariable(1).acceptVisitor(isRedex));
		assertFalse(new FreeVariable("x").acceptVisitor(isRedex));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidLambdaDeserialization() {
		LambdaTerm.deserialize("notATermSerial");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyLambdaDeserialization() {
		LambdaTerm.deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullLambdaDeserialization() {
		LambdaTerm.deserialize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeChurchNumber() {
		LambdaTerm.churchNumber(-2);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void negativeBoundVar() {
		new BoundVariable(-5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidBoundVar() {
		BoundVariable.fromSerialized("String");
	}
	
	@Test
	public void partialAppResolvedNames() {
		new PartialApplication.Addition().acceptVisitor(new BlockedNamesVisitor());
	}
	
	@Test
	public void isChurchNumTest() {
		assertFalse(new PartialApplication.Addition().acceptVisitor(new IsChurchNumberVisitor()));
		LambdaTerm term = new Application(new FreeVariable("x"), new FreeVariable("x"));
		assertFalse(term.acceptVisitor(new IsChurchNumberVisitor()));
		assertFalse(new FreeVariable("y").acceptVisitor(new IsChurchNumberVisitor()));
	}
	
	@Test
	public void isPartialAppTest() {
		assertFalse(new Abstraction("x", new FreeVariable("y")).acceptVisitor(new IsPartialApplicationVisitor()));
	}
	
	@Test
	public void isRedexTest() {
		assertFalse(new PartialApplication.Addition().acceptVisitor(new IsRedexVisitor()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void resolveFreeVarTest() {
		new FreeVariable("x").acceptVisitor(new RedexResolutionVisitor(idRedex));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void resolveBoundVarTest() {
		new BoundVariable(1).acceptVisitor(new RedexResolutionVisitor(idRedex));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void resolveAppTest() {
		new Application(new FreeVariable("x"), new FreeVariable("y")).acceptVisitor(new RedexResolutionVisitor(idRedex));
	}
	
	@Test
	public void partialAppReceivedTest() {
		assertEquals(new PartialApplication.Addition().getReceived().length, 2);
	}
	
	@Test
	public void partialAppNumReceivedTest() {
		assertEquals(new PartialApplication.Addition().getNumReceived(), 0);
	}
}

