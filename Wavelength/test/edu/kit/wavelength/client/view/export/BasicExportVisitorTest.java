package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import edu.kit.wavelength.client.model.library.NaturalNumbers;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.PartialApplication.Addition;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class BasicExportVisitorTest {

	@Test
	public void printFreeVariableTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm var1 = new FreeVariable("a");
		LambdaTerm var2 = new FreeVariable("abcdefghijklmnopqrstuvwxyz");
		LambdaTerm var3 = new FreeVariable("");

		assertEquals("a", var1.acceptVisitor(visitor).toString());
		assertEquals("abcdefghijklmnopqrstuvwxyz", var2.acceptVisitor(visitor).toString());
		assertEquals("", var3.acceptVisitor(visitor).toString());
	}

	@Test
	public void printNamedTermTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm id = new Abstraction("x", new BoundVariable(1));
		LambdaTerm named1 = new NamedTerm("named", id);
		LambdaTerm named2 = new NamedTerm("abcdefghijklmnopqrstuvwxyz", id);

		assertEquals("named", named1.acceptVisitor(visitor).toString());
		assertEquals("abcdefghijklmnopqrstuvwxyz", named2.acceptVisitor(visitor).toString());
	}

	@Test
	public void printAbstractionTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm id1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm id2 = new Abstraction("y", new BoundVariable(1));
		LambdaTerm abs = new Abstraction("x", new Abstraction("y", new BoundVariable(2)));

		assertEquals("\\x. x", id1.acceptVisitor(visitor).toString());
		assertEquals("\\y. y", id2.acceptVisitor(visitor).toString());
		assertEquals("\\x. \\y. x", abs.acceptVisitor(visitor).toString());
	}

	@Test
	public void unicodeLambdaTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "λ");

		LambdaTerm id1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm id2 = new Abstraction("y", new BoundVariable(1));
		LambdaTerm abs = new Abstraction("x", new Abstraction("y", new BoundVariable(2)));

		assertEquals("λx. x", id1.acceptVisitor(visitor).toString());
		assertEquals("λy. y", id2.acceptVisitor(visitor).toString());
		assertEquals("λx. λy. x", abs.acceptVisitor(visitor).toString());
	}

	@Test
	public void printApplicationTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm var1 = new FreeVariable("x");
		LambdaTerm var2 = new FreeVariable("y");
		LambdaTerm var3 = new FreeVariable("z");
		LambdaTerm app1 = new Application(var1, var2);
		LambdaTerm app2 = new Application(var2, var3);
		LambdaTerm app3 = new Application(app1, var3);

		assertEquals("x y", app1.acceptVisitor(visitor).toString());
		assertEquals("y z", app2.acceptVisitor(visitor).toString());
		assertEquals("x y z", app3.acceptVisitor(visitor).toString());
	}

	@Test
	public void printPartialApplication() throws ParseException {
		BasicExportVisitor visitor = new BasicExportVisitor(Arrays.asList(new NaturalNumbers(true)), "\\");

		LambdaTerm parApp1 = new PartialApplication.Addition();
		LambdaTerm parApp2 = new PartialApplication.Successor();
		LambdaTerm parApp3 = new PartialApplication.Multiplication();
		LambdaTerm parApp4 = new PartialApplication.Exponentiation();
		LambdaTerm parApp5 = new PartialApplication.Predecessor();
		LambdaTerm parApp6 = new PartialApplication.Subtraction();
		
		assertEquals("plus", parApp1.acceptVisitor(visitor).toString());
		assertEquals("succ", parApp2.acceptVisitor(visitor).toString());
		assertEquals("times", parApp3.acceptVisitor(visitor).toString());
		assertEquals("pow", parApp4.acceptVisitor(visitor).toString());
		assertEquals("pred", parApp5.acceptVisitor(visitor).toString());
		assertEquals("minus", parApp6.acceptVisitor(visitor).toString());
	}
	
	@Test
	public void simpleBracketsTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm app1 = new Application(new FreeVariable("a"), new FreeVariable("b"));
		LambdaTerm id1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm id2 = new Abstraction("y", new BoundVariable(1));
		LambdaTerm term1 = new Abstraction("x", new Application(new BoundVariable(1), new FreeVariable("y")));
		LambdaTerm term2 = new Application(app1, new FreeVariable("c"));
		LambdaTerm term3 = new Application(new FreeVariable("c"), app1);
		LambdaTerm term4 = new Application(id1, new FreeVariable("c"));
		LambdaTerm term5 = new Application(new FreeVariable("c"), id1);
		LambdaTerm term6 = new Application(id1, id2);

		assertEquals("\\x. x y", term1.acceptVisitor(visitor).toString());
		assertEquals("a b c", term2.acceptVisitor(visitor).toString());
		assertEquals("c (a b)", term3.acceptVisitor(visitor).toString());
		assertEquals("(\\x. x) c", term4.acceptVisitor(visitor).toString());
		assertEquals("c (\\x. x)", term5.acceptVisitor(visitor).toString());
		assertEquals("(\\x. x) (\\y. y)", term6.acceptVisitor(visitor).toString());
	}

	@Test
	public void complexBracketTest() {
		BasicExportVisitor visitor = new BasicExportVisitor(Collections.emptyList(), "\\");

		LambdaTerm term1 = new Abstraction("x", new Application(new FreeVariable("a"), new Abstraction("y",
				new Abstraction("z", new Application(new FreeVariable("c"), new FreeVariable("d"))))));
		LambdaTerm term2 = new Application(new Abstraction("x", new Abstraction("y", new BoundVariable(2))),
				new FreeVariable("c"));
		LambdaTerm term3 = new Application(new FreeVariable("a"), new Application(new FreeVariable("b"),
				new Application(new FreeVariable("c"), new Application(new FreeVariable("d"), new FreeVariable("e")))));
		LambdaTerm term4 = new Abstraction("p", new Application(new FreeVariable("a"), term2));

		assertEquals("\\x. a (\\y. \\z. c d)", term1.acceptVisitor(visitor).toString());
		assertEquals("(\\x. \\y. x) c", term2.acceptVisitor(visitor).toString());
		assertEquals("a (b (c (d e)))", term3.acceptVisitor(visitor).toString());
		assertEquals("\\p. a ((\\x. \\y. x) c)", term4.acceptVisitor(visitor).toString());
	}
}
