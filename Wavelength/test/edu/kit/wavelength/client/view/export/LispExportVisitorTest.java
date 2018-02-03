
package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

public class LispExportVisitorTest {

	@Test
	public void simpleSyntaxTest() {
		LispExportVisitor visitor = new LispExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm term2 = new Application(new FreeVariable("a"), new FreeVariable("b"));
		LambdaTerm term3 = new Abstraction("x", new Abstraction("y", new BoundVariable(2)));
		LambdaTerm term4 = new Application(new Application(new FreeVariable("a"), new FreeVariable("b")),
				new FreeVariable("c"));

		assertEquals("(lambda (x) x)", term1.acceptVisitor(visitor).toString());
		assertEquals("(a b)", term2.acceptVisitor(visitor).toString());
		assertEquals("(lambda (x) (lambda (y) x))", term3.acceptVisitor(visitor).toString());
		assertEquals("((a b) c)", term4.acceptVisitor(visitor).toString());
	}

	@Test
	public void complexSyntaxTest() {
		LispExportVisitor visitor = new LispExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new Application(new FreeVariable("a"), new Abstraction("y",
				new Abstraction("z", new Application(new FreeVariable("c"), new FreeVariable("d"))))));
		LambdaTerm term2 = new Application(new Abstraction("x", new Abstraction("y", new BoundVariable(2))),
				new FreeVariable("c"));
		LambdaTerm term3 = new Abstraction("p", new Application(new FreeVariable("a"), term2));

		assertEquals("(lambda (x) (a (lambda (y) (lambda (z) (c d)))))", term1.acceptVisitor(visitor).toString());
		assertEquals("((lambda (x) (lambda (y) x)) c)", term2.acceptVisitor(visitor).toString());
		assertEquals("(lambda (p) (a ((lambda (x) (lambda (y) x)) c)))", term3.acceptVisitor(visitor).toString());
	}
}