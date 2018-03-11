package edu.kit.wavelength.client.model.term.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.BoundVariable;

/**
 * A class containing parser tests.
 *
 */
public class ParserTest {
	
	Parser testParser;
	private String triple = "x y z";
	private String stringA = "λy.(y y)";
	private String stringB = "((λx.(λy. (y  y))) v)";
	private String stringC = "(λx. ((x  x) x))";
	private String stringD = "((λx.λy.λz. z) (λx. ((x  x) x)))";
	private String stringE = "((λx. x (λy. y (λz.x z))) (x λx.v))";
	private String stringF = "((\\x. (v x)) v)";
	private String errorA = "λλx.(x y)";
	private String uselessBrackets = "((x) v)";
	private String mismatchedBrackets = "\\v. x )";
	private String shortAbs = "λx.λy.λz.x z";
	private String id = "(λx.x)";
	private String app2 = id + id;
	private String app3 = id + id + id;
	private String app4 = id + id + id + id;
	private String app5 = id + id + id + id + id;
	private String firstDecl = "term = \\x.x y";
	private String julString = "(( ((\\x.x)(\\x.x)) (\\x.x)) ( ((\\x.x)(\\x.x)) (\\x.x) ) )";
	private String varString = "(( (v v) v) ( (v v) v ) )";
	private String singleUnicodeLib = "Σ = z v";
	private String stringUnicodeLibWUpCaLa = "ΔΘΛ =j";
	private String stringUnicodeLib = "ΦΨη = (\\x. x x)";
	private String libUsingString =  "(ΦΨη x)(Σ ΔΘΛ)";
	private String lBracketAbs = "(\\x. (y) x)";
	
	LambdaTerm lBracketAbsTerm = new Abstraction("x", new Application (new FreeVariable("y") ,new BoundVariable(1)));
	
	LambdaTerm someBlankLinesTerm = new Application(new NamedTerm("abc", new FreeVariable("x")), new NamedTerm("def", new Application(new FreeVariable("v"), new FreeVariable("v"))));
	
	LambdaTerm singeUnicodeLibTerm = new NamedTerm("Σ", new Application(new FreeVariable("z"), new FreeVariable("v")));
	LambdaTerm stringUnicodeLibWUpCaLaTerm = new NamedTerm("ΔΘΛ", new FreeVariable("v"));
	LambdaTerm stringUnicodeLibTerm = new NamedTerm("", new Abstraction("x", new Application(
			new BoundVariable(1), new BoundVariable(1))));
	LambdaTerm uLibTestTerm = null;
	
	LambdaTerm idTerm = new Abstraction("x", new BoundVariable(1));
	LambdaTerm var = new FreeVariable("v");
	
	LambdaTerm julTerm =  new Application(new Application(new Application(idTerm, idTerm), idTerm),
			new Application(new Application(idTerm, idTerm), idTerm));
	LambdaTerm varTerm = new Application(new Application(new Application(var, var), var),
			new Application(new Application(var, var), var));
	
	LambdaTerm termA = new Abstraction("y", new Application(new BoundVariable(1), new BoundVariable(1)));
	LambdaTerm termB = new Application(new Abstraction("x", termA), new FreeVariable("v"));
	LambdaTerm termC = new Abstraction("x",
			new Application(new Application(new BoundVariable(1), new BoundVariable(1)), new BoundVariable(1)));
	LambdaTerm termD = new Application(
			new Abstraction("x", new Abstraction("y", new Abstraction("z", new BoundVariable(1)))), termC);
	LambdaTerm termF = new Application(
			new Abstraction("x", new Application(new FreeVariable("v"), new BoundVariable(1))), new FreeVariable("v"));
	LambdaTerm shortAbsTerm = new Abstraction("x",
			new Abstraction("y", new Abstraction("z", new Application(new BoundVariable(3), new BoundVariable(1)))));
	LambdaTerm app2Term = new Application(idTerm, idTerm);
	LambdaTerm app3Term = new Application(app2Term, idTerm);
	LambdaTerm app4Term = new Application(app3Term, idTerm);
	LambdaTerm app5Term = new Application(app4Term, idTerm);
	
	LambdaTerm uslessBracketsTerm = new Application(new FreeVariable("x"), new FreeVariable("v"));
	LambdaTerm tripleTerm = new Application(new Application(new FreeVariable("x"), new FreeVariable("y")), new FreeVariable("z"));
			
	@Before
	public void setUp() {
		testParser = new Parser(new ArrayList<Library>());
	}
	
	@Test
	public void varTriple() throws ParseException {
		LambdaTerm term = testParser.parse(triple);
		assertEquals(term, tripleTerm);
	}
	
	@Test
	public void lbracketBreaksAbs() throws ParseException {
		LambdaTerm term = testParser.parse(lBracketAbs);
		assertEquals(lBracketAbsTerm, term);
	}
	
	@Ignore
	public void someBlankLinesTest() throws ParseException {
		String myTest = "a = v \n b = k \n \n\n \n a b";
		LambdaTerm term = testParser.parse(myTest);
		assertEquals(term, someBlankLinesTerm);
	}
	
	@Test
	public void unicodeLibTerms() throws ParseException {
		String input = singleUnicodeLib + "\n" + firstDecl + "\n" + stringUnicodeLibWUpCaLa + "\n"
					   + libUsingString;
	}
	
	@Test
	public void julTest() throws ParseException {
		LambdaTerm term = testParser.parse(julString);
		assertEquals(term, julTerm);
	}
	
	@Test
	public void varJulTest() throws ParseException {
		LambdaTerm term = testParser.parse(varString);
		assertEquals(term, varTerm);
	}

	@Test
	public void shortAbsTest() {
		try {
			LambdaTerm term = testParser.parse(shortAbs);
			assertEquals(term, shortAbsTerm);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testidentity() {
		try {
			LambdaTerm term = testParser.parse(id);
			assertEquals(term, idTerm);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test2App() {
		try {
			LambdaTerm term = testParser.parse(app2);
			assertEquals(term, app2Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test3App() {
		try {
			LambdaTerm term = testParser.parse(app3);
			assertEquals(term, app3Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test4App() {
		try {
			LambdaTerm term = testParser.parse(app4);
			assertEquals(term, app4Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test5App() {
		try {
			LambdaTerm term = testParser.parse(app5);
			assertEquals(term, app5Term);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testcaseA() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringA);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termA);
	}

	@Test
	public void testcaseB() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringB);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termB);
	}

	@Test
	public void testCaseBwSpaces() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringB + "     ");
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termB);
	}

	@Test
	public void testcaseC() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringC);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termC);
	}

	@Test
	public void testcaseD() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringD);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termD);
	}

	@Test
	public void testCaseF() {
		LambdaTerm term = null;
		try {
			term = testParser.parse(stringF);
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(term, termF);
	}

	@Test(expected = ParseException.class)
	public void failureTestCaseA() throws ParseException {
		testParser.parse(errorA);
	}

	@Test(expected = ParseException.class)
	public void failureTestCaseB() throws ParseException {
		testParser.parse(errorA);
		fail();
	}

	@Test
	public void failureBrackets() throws ParseException {
		LambdaTerm term = testParser.parse(uselessBrackets);
		assertEquals(term, uslessBracketsTerm);
	}

	@Test(expected = ParseException.class)
	public void failureMismatch() throws ParseException {
		testParser.parse(mismatchedBrackets);
	}

	@Test
	public void firstLibraryTest() throws ParseException {
		String testString = "lib = λx.x" + "\n" + "λv.(lib lib)";
		LambdaTerm libTerm = new NamedTerm("lib", new Abstraction("x", new BoundVariable(1)));
		LambdaTerm expectedTerm = new Abstraction("v", new Application(libTerm, libTerm));
		LambdaTerm term = null;
		try {
			term = testParser.parse(testString);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(term, expectedTerm);
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput1() throws ParseException {
		testParser.parse(" ");
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput2() throws ParseException {
		testParser.parse("\n");
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput3() throws ParseException {
		testParser.parse("\n ");
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput4() throws ParseException {
		testParser.parse(" \n ");
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput5() throws ParseException {
		testParser.parse("");
	}
	
	@Test(expected = ParseException.class)
	public void whitespaceInput6() throws ParseException {
		testParser.parse("   \n \n\n  \n");
	}
	
	@Test(expected = ParseException.class)
	public void malformedNameAssignment1() throws ParseException {
		testParser.parse("==a==\n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedNameAssignment2() throws ParseException {
		testParser.parse("8==\n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedNameAssignment3() throws ParseException {
		testParser.parse("= C= C<\n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void singleDash1() throws ParseException {
		testParser.parse("-\n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void singleDash2() throws ParseException {
		testParser.parse("a=b -\n\\x.x");
	}
	
	@Test
	public void termOrder1() throws ParseException {
		LambdaTerm t = testParser.parse("a=b\na=c\na");
		assertEquals(new NamedTerm("a", new FreeVariable("c")), t);
	}
	
	@Test
	public void termOrder2() throws ParseException {
		LambdaTerm t = testParser.parse("a=b\nb=a\na=a\na");
		assertEquals(new NamedTerm("a", new NamedTerm("a", new FreeVariable("b"))), t);
	}
	
	@Test(expected = ParseException.class)
	public void unbalancedParens1() throws ParseException {
		testParser.parse("(\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void unbalancedParens2() throws ParseException {
		testParser.parse("\\x.x)");
	}
	
	@Test(expected = ParseException.class)
	public void unbalancedParens3() throws ParseException {
		testParser.parse(")\\x.x(");
	}
	
	@Test(expected = ParseException.class)
	public void unbalancedParens4() throws ParseException {
		testParser.parse("\\x.x)(");
	}
	
	@Test(expected = ParseException.class)
	public void unbalancedParens5() throws ParseException {
		testParser.parse("(\\x.((x x)) x))");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput1() throws ParseException {
		testParser.parse("");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput2() throws ParseException {
		testParser.parse(" ");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput3() throws ParseException {
		testParser.parse("\n");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput4() throws ParseException {
		testParser.parse(" \n      ");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput5() throws ParseException {
		testParser.parse("x=y");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput6() throws ParseException {
		testParser.parse("x=y\n");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput7() throws ParseException {
		testParser.parse("x=y\n     ");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput8() throws ParseException {
		testParser.parse("\\x.()");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput9() throws ParseException {
		testParser.parse("u=\n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void emptyInput10() throws ParseException {
		testParser.parse("u=    \n\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression1() throws ParseException {
		testParser.parse("\\\\x.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression2() throws ParseException {
		testParser.parse("\\x\\x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression3() throws ParseException {
		testParser.parse("\\x..x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression4() throws ParseException {
		testParser.parse("\\.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression5() throws ParseException {
		testParser.parse("\\x y.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression6() throws ParseException {
		testParser.parse("\\(x).x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression7() throws ParseException {
		testParser.parse("\\(.x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression8() throws ParseException {
		testParser.parse("\\(x x).x");
	}
	
	@Test(expected = ParseException.class)
	public void malformedLambdaExpression9() throws ParseException {
		testParser.parse("\\(\\y. y).x");
	}
}
