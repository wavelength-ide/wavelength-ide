package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;

public class LaTeXExportVisitorTest {

	@Test
	public void simpleSyntaxTest() {
		LaTeXExportVisitor visitor = new LaTeXExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm term2 = new Abstraction("x", new Abstraction("y", new BoundVariable(2)));
		LambdaTerm term3 = new Application(new Application(new FreeVariable("a"), new FreeVariable("b")),
				new FreeVariable("c"));

		assertEquals("\\lambda x.\\, x", term1.acceptVisitor(visitor).toString());
		assertEquals("\\lambda x.\\, \\lambda y.\\, x", term2.acceptVisitor(visitor).toString());
		assertEquals("a\\: b\\: c", term3.acceptVisitor(visitor).toString());
	}

	@Test
	public void textPrintingTest() {
		LaTeXExportVisitor visitor = new LaTeXExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new NamedTerm("identity", new Abstraction("x", new BoundVariable(1)));
		LambdaTerm term2 = new Abstraction("x", new Application(term1,
				new Abstraction("y", new Abstraction("z", new Application(new FreeVariable("c"), term1)))));

		assertEquals("\\text{identity}", term1.acceptVisitor(visitor).toString());
		assertEquals("\\lambda x.\\, \\text{identity}\\: (\\lambda y.\\, \\lambda z.\\, c\\: \\text{identity})",
				term2.acceptVisitor(visitor).toString());
	}

	@Test
	public void complexSyntaxTest() {
		LaTeXExportVisitor visitor = new LaTeXExportVisitor(Collections.emptyList());

		LambdaTerm term1 = new Abstraction("x", new Application(new FreeVariable("a"), new Abstraction("y",
				new Abstraction("z", new Application(new FreeVariable("c"), new FreeVariable("d"))))));
		LambdaTerm term2 = new Application(new Abstraction("x", new Abstraction("y", new BoundVariable(2))),
				new FreeVariable("c"));
		LambdaTerm term3 = new Abstraction("p", new Application(new FreeVariable("a"), term2));

		assertEquals("\\lambda x.\\, a\\: (\\lambda y.\\, \\lambda z.\\, c\\: d)", term1.acceptVisitor(visitor).toString());
		assertEquals("(\\lambda x.\\, \\lambda y.\\, x)\\: c", term2.acceptVisitor(visitor).toString());
		assertEquals("\\lambda p.\\, a\\: ((\\lambda x.\\, \\lambda y.\\, x)\\: c)", term3.acceptVisitor(visitor).toString());
	}
}
