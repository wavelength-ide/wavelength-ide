package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

public class HaskellExportVisitorTest {

	@Test
	public void simpleSyntaxTest() {
		HaskellExportVisitor visitor = new HaskellExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm term2 = new Abstraction("x", new Abstraction("y", new BoundVariable(2)));
		LambdaTerm term3 = new Application(new Application(new FreeVariable("a"), new FreeVariable("b")),
				new FreeVariable("c"));
		
		assertEquals("\\x -> x", term1.acceptVisitor(visitor).toString());
		assertEquals("\\x -> \\y -> x", term2.acceptVisitor(visitor).toString());
		assertEquals("a b c", term3.acceptVisitor(visitor).toString());
	}
	
	@Test
	public void complexSyntaxTest() {
		HaskellExportVisitor visitor = new HaskellExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new Application(new FreeVariable("a"), new Abstraction("y",
				new Abstraction("z", new Application(new FreeVariable("c"), new FreeVariable("d"))))));
		LambdaTerm term2 = new Application(new Abstraction("x", new Abstraction("y", new BoundVariable(2))),
				new FreeVariable("c"));
		LambdaTerm term3 = new Abstraction("p", new Application(new FreeVariable("a"), term2));

		assertEquals("\\x -> a (\\y -> \\z -> c d)", term1.acceptVisitor(visitor).toString());
		assertEquals("(\\x -> \\y -> x) c", term2.acceptVisitor(visitor).toString());
		assertEquals("\\p -> a ((\\x -> \\y -> x) c)", term3.acceptVisitor(visitor).toString());
	}
}
