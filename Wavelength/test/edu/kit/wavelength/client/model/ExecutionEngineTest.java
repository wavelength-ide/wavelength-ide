package edu.kit.wavelength.client.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import edu.kit.wavelength.client.model.ExecutionEngine;
import edu.kit.wavelength.client.model.ExecutionException;
import edu.kit.wavelength.client.model.library.Libraries;
import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.library.NaturalNumbers;
import edu.kit.wavelength.client.model.output.Full;
import edu.kit.wavelength.client.model.output.OutputSize;
import edu.kit.wavelength.client.model.output.OutputSizes;
import edu.kit.wavelength.client.model.output.Periodic;
import edu.kit.wavelength.client.model.output.ResultOnly;
import edu.kit.wavelength.client.model.output.Shortened;
import edu.kit.wavelength.client.model.reduction.ApplicativeOrder;
import edu.kit.wavelength.client.model.reduction.NormalOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.GetChurchNumberVisitor;
import edu.kit.wavelength.client.model.term.IsChurchNumberVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.ToStringVisitor;
import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class ExecutionEngineTest {

	private final String idRedex = "(λx.x) y";
	private final String twoRedex = "(λx.x ((λy.y) z)) v";
	private final String infinite = "(λx. x x)(λx. x x)";
	private final String nestedId = "(\\x. x) (\\x. x) (\\x. x) (\\x. x) (\\x. x) (\\x. x) (\\x. x) (\\x. x) (\\x. x)";
	private final String multi = "(\\x.x)(\\x.x)((\\x.x)(\\x.x))";
	private Parser testParser;

	@Before
	public void setUp() {
		testParser = new Parser(Collections.emptyList());
	}

	@Test
	public void basicInitAndGetter() throws ParseException {
		ExecutionEngine engine = new ExecutionEngine(idRedex, new NormalOrder(), new ResultOnly(),
				Collections.emptyList());
		assertFalse(engine.canStepBackward());
		assertEquals(engine.getDisplayed().size(), 1);
		assertEquals(engine.getLibraries().size(), 1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidDeserialization() {
		ExecutionEngine engine = new ExecutionEngine("invalidEngine");
	}

	@Test
	public void initTest() throws ParseException {
		for (ReductionOrder reduction : ReductionOrders.all()) {
			for (OutputSize out : OutputSizes.all()) {
				for (Library lib : Libraries.all()) {
					ArrayList<Library> libraries = new ArrayList<Library>();
					libraries.add(lib);
					ExecutionEngine engine = new ExecutionEngine(idRedex, reduction, out, libraries);
					StringBuilder serial = engine.serialize();
					ExecutionEngine deserialEngine = new ExecutionEngine(serial.toString());
					assertEquals(engine.getDisplayed(), deserialEngine.getDisplayed());
					assertEquals(engine.canStepBackward(), deserialEngine.canStepBackward());
					assertEquals(engine.isFinished(), engine.isFinished());
					assertEquals(engine.serialize().toString(), deserialEngine.serialize().toString());
				}
			}
		}
	}

	@Test
	public void deserialize1Test() throws ParseException, ExecutionException {
		ExecutionEngine original = new ExecutionEngine(idRedex, new NormalOrder(), new Full(), Collections.emptyList());
		original.stepForward();
		ExecutionEngine restored = new ExecutionEngine(original.serialize().toString());
		assertArrayEquals(original.getDisplayed().toArray(new LambdaTerm[0]),
				restored.getDisplayed().toArray(new LambdaTerm[0]));
		restored.stepBackward();
		assertEquals(1, restored.getDisplayed().size());
		assertEquals(testParser.parse(idRedex), restored.getDisplayed().get(0));
	}

	@Test
	public void basicReductionTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(idRedex, new NormalOrder(), new Full(), Collections.emptyList());
		List<LambdaTerm> terms = engine.stepForward();
		assertEquals(terms.size(), 1);
		assertEquals(terms.get(0), new FreeVariable("y"));
		assertEquals(engine.getDisplayed().size(), 2);
		assertTrue(engine.canStepBackward());
		engine.stepBackward();
		assertEquals(engine.getDisplayed().size(), 1);
	}

	@Test(expected = IllegalStateException.class)
	public void invalidStepBackwards() throws ParseException {
		ExecutionEngine engine = new ExecutionEngine(idRedex, new NormalOrder(), new Full(), Collections.emptyList());
		engine.stepBackward();
	}

	@Test
	public void changeReductionOrder() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(twoRedex, new NormalOrder(), new Full(), Collections.emptyList());
		List<LambdaTerm> normalTerms = engine.stepForward();
		engine.stepBackward();
		engine.setReductionOrder(new ApplicativeOrder());
		List<LambdaTerm> applicativeTerms = engine.stepForward();
		assertEquals(normalTerms.size(), 1);
		assertEquals(applicativeTerms.size(), 1);
		LambdaTerm normalResult = testParser.parse("v ((λy.y) z)");
		LambdaTerm applicativeResult = testParser.parse("(λx.x z) v");
		assertEquals(normalTerms.get(0), normalResult);
		assertEquals(applicativeTerms.get(0), applicativeResult);
	}

	@Test
	public void displayTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(infinite, new NormalOrder(), new Periodic(5),
				Collections.emptyList());
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		List<LambdaTerm> terms = engine.stepForward();
		assertEquals(terms.size(), 1);
		engine.stepBackward();
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		assertTrue(engine.stepForward().isEmpty());
		terms = engine.stepForward();
		assertEquals(terms.size(), 1);
	}

	@Test(expected = IllegalStateException.class)
	public void invalidDisplayCTest() throws ParseException {
		ExecutionEngine engine = new ExecutionEngine(infinite, new NormalOrder(), new Periodic(3),
				Collections.emptyList());
		engine.displayCurrent();
	}

	@Test
	public void validDisplayCTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(infinite, new NormalOrder(), new Periodic(3),
				Collections.emptyList());
		LambdaTerm firstTerm = testParser.parse(infinite);
		engine.stepForward();
		LambdaTerm secondTerm = engine.displayCurrent();
		assertEquals(firstTerm, secondTerm);
		assertEquals(engine.getDisplayed().size(), 2);
		engine.stepBackward();
		assertEquals(engine.getDisplayed().size(), 1);
	}

	@Test(expected = IllegalStateException.class)
	public void finishedStepForwardTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(idRedex, new NormalOrder(), new Full(), Collections.emptyList());
		engine.stepForward();
		engine.stepForward();
	}

	@Test
	public void setOutputSizeTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(nestedId, new NormalOrder(), new Periodic(2),
				Collections.emptyList());
		assertEquals(0, engine.stepForward().size());
		assertEquals(1, engine.stepForward().size());
		engine.setOutputSize(new Full());
		assertEquals(1, engine.stepForward().size());
		assertEquals(1, engine.stepForward().size());
		engine.setOutputSize(new Shortened(2));
		assertEquals(0, engine.stepForward().size());
		assertEquals(0, engine.stepForward().size());
		engine.setOutputSize(new Shortened(6));
		assertEquals(0, engine.stepForward().size());
		assertEquals(3, engine.stepForward().size());
	}

	@Test
	public void getStepNumber1Test() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(nestedId, new NormalOrder(), new Periodic(2),
				Collections.emptyList());
		assertEquals(0, engine.getStepNumber());
		for (int i = 1; i <= 8; ++i) {
			engine.stepForward();
			assertEquals(i, engine.getStepNumber());
		}
	}

	@Test
	public void getStepNumber2Test() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(nestedId, new NormalOrder(), new Periodic(2),
				Collections.emptyList());
		assertEquals(0, engine.getStepNumber());
		assertEquals(0, engine.stepForward().size());
		assertEquals(1, engine.stepForward().size());
		assertEquals(2, engine.getStepNumber());
		engine.stepBackward();
		assertEquals(0, engine.getStepNumber());
		assertEquals(0, engine.stepForward().size());
		assertEquals(1, engine.getStepNumber());
		assertEquals(1, engine.stepForward().size());
		assertEquals(0, engine.stepForward().size());
		assertEquals(1, engine.stepForward().size());
		assertEquals(4, engine.getStepNumber());
		engine.stepBackward();
		assertEquals(2, engine.getStepNumber());
		engine.stepBackward();
		assertEquals(0, engine.getStepNumber());
	}

	@Test
	public void pushTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine(multi, new NormalOrder(), new Periodic(2),
				Collections.emptyList());
		LambdaTerm start = engine.getDisplayed().get(engine.getDisplayed().size() - 1);
		assertThat(start, instanceOf(Application.class));
		LambdaTerm right = ((Application) start).getRightHandSide();
		assertEquals(testParser.parse("(\\x.x)(\\x.x)"), right);
		engine.stepForward((Application) right);

		// In particular, _not_ (\x.x)((\x.x)(\x.x))
		assertEquals(testParser.parse("(\\x.x)(\\x.x)(\\x.x)"),
				engine.getDisplayed().get(engine.getDisplayed().size() - 1));
	}

	@Test
	public void selfReferenceTest() throws ParseException, ExecutionException {
		ExecutionEngine engine = new ExecutionEngine("plus = plus 1\nplus=plus\nplus 2", new NormalOrder(), new Full(),
				Collections.singletonList(new NaturalNumbers(false)));

		engine.stepForward();
		engine.stepForward();
		engine.stepForward();
		engine.stepForward();
		engine.stepForward();
		engine.stepForward();
		assertTrue(engine.isFinished());
		LambdaTerm result = engine.getDisplayed().get(engine.getDisplayed().size() - 1);
		assertTrue(result.acceptVisitor(new IsChurchNumberVisitor()));
		assertEquals(new Integer(3), result.acceptVisitor(new GetChurchNumberVisitor()));
	}
}
