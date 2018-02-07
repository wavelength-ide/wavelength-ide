package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BetaReductionTest {

	@Test
	public void basicTest() {
		LambdaTerm id1 = new Abstraction("x", new BoundVariable(1));
		LambdaTerm id2 = new Abstraction("x", new BoundVariable(1));
		Application triv = new Application(id1, id2);
		
		LambdaTerm reduced = triv.acceptVisitor(new BetaReducer(triv));
		
		assertEquals(id2, reduced);
	}
	
	@Test
	public void indexAdjustment1Test() {
		BoundVariable one = new BoundVariable(1);
		BoundVariable two = new BoundVariable(2);
		Abstraction z = new Abstraction("z", two);
		Abstraction x = new Abstraction("x", z);
		Application app = new Application(x, one);
		Abstraction y = new Abstraction("y", app);
		
		LambdaTerm reduced = y.acceptVisitor(new BetaReducer(app));
		
		assertEquals(new Abstraction("y", new Abstraction("z", new BoundVariable(2))), reduced);
	}
	
	@Test
	public void indexAdjustment2Test() {
		//right side
		BoundVariable one = new BoundVariable(1);
		BoundVariable two = new BoundVariable(2);
		Application a1 = new Application(one, two);
		Abstraction x = new Abstraction("x", a1);
		
		//left side
		BoundVariable one1 = new BoundVariable(1);
		BoundVariable four = new BoundVariable(4);
		Application a2 = new Application(one1, four);
		BoundVariable three = new BoundVariable(3);
		Application a3 = new Application(a2, three);
		Abstraction w = new Abstraction("w", a3);
		Abstraction z = new Abstraction("z", w);
		Abstraction x2 = new Abstraction("x", z);
		
		Application app = new Application(x2, x);
		Abstraction y = new Abstraction("y", app);
		
		LambdaTerm reduced = y.acceptVisitor(new BetaReducer(app));
		
		BoundVariable _one = new BoundVariable(1);
		BoundVariable _three = new BoundVariable(3);
		BoundVariable _one1 = new BoundVariable(1);
		BoundVariable _four = new BoundVariable(4);
		
		Application _a1 = new Application(_one, _three);
		Application _a2 = new Application(_one1, _four);
		Abstraction _x = new Abstraction("x", _a2);
		Application _a3 = new Application(_a1, _x);
		Abstraction _y = new Abstraction("y", new Abstraction("z", new Abstraction("w", _a3)));
		
		assertEquals(_y, reduced);
	}
	
	@Test
	public void indexAdjustment3Test() {
		Application a1 = new Application(
				new Abstraction("x",
						new BoundVariable(1)),
				new BoundVariable(1));
		Abstraction a2 = new Abstraction("x", a1);
		
		LambdaTerm reduced = a2.acceptVisitor(new BetaReducer(a1));
		
		assertEquals(new Abstraction("x", new BoundVariable(1)), reduced);
	}
	
	@Test
	public void indexAdjustment4Test() {
		Application a1 = new Application(
				new Abstraction("x",
					new BoundVariable(2)),
				new FreeVariable("y"));
		Abstraction a2 = new Abstraction("z", a1);
		
		LambdaTerm reduced = a2.acceptVisitor(new BetaReducer(a1));
		
		assertEquals(new Abstraction("z", new BoundVariable(1)), reduced);
	}
	
	@Test
	public void aliasing1Test() {
		BoundVariable o1 = new BoundVariable(1);
		Abstraction x1 = new Abstraction("x", o1);
		FreeVariable fv1 = new FreeVariable("x");
		Application a1 = new Application(x1, fv1);
		
		BoundVariable o2 = new BoundVariable(1);
		Abstraction x2 = new Abstraction("x", o2);
		FreeVariable fv2 = new FreeVariable("x");
		Application a2 = new Application(x2, fv2);
		
		Application a3 = new Application(a1, a2);
		
		LambdaTerm reducedLeft = a3.acceptVisitor(new BetaReducer(a1));
		
		LambdaTerm exLeft = new Application(
				new FreeVariable("x"),
				new Application(
						new Abstraction("x",
								new BoundVariable(1)),
						new FreeVariable("x")));
		
		assertEquals(exLeft, reducedLeft);
		
		LambdaTerm reducedRight = a3.acceptVisitor(new BetaReducer(a2));
		
		LambdaTerm exRight = new Application(
				new Application(
						new Abstraction("x",
								new BoundVariable(1)),
						new FreeVariable("x")),
				new FreeVariable("x"));
		
		assertEquals(exRight, reducedRight);
	}
	
	@Test
	public void fixpointTest() {
		Application a = new Application(
				new Abstraction("x",
						new Application(
								new BoundVariable(1),
								new BoundVariable(1))),
				new Abstraction("x",
						new Application(
								new BoundVariable(1),
								new BoundVariable(1))));
		
		assertEquals(a, a.acceptVisitor(new BetaReducer(a)));
	}
	
	@Test
	public void growthTest() {
		Application a = new Application(
				new Abstraction("x",
					new Application(
							new Application(
									new BoundVariable(1),
									new BoundVariable(1)),
							new BoundVariable(1))),
				new Abstraction("x",
						new Application(
								new Application(
										new BoundVariable(1),
										new BoundVariable(1)),
								new BoundVariable(1))));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		Abstraction e = new Abstraction("x",
				new Application(
						new Application(
								new BoundVariable(1),
								new BoundVariable(1)),
						new BoundVariable(1)));
		
		// Not technically valid, but it works here
		Application want1 = new Application(new Application(e, e), e);
		Application want2 = new Application(want1, e);		
		
		assertEquals(want1, reduced);
		Application next = (Application)((Application)reduced).getLeftHandSide();	
		LambdaTerm reduced2 = reduced.acceptVisitor(new BetaReducer(next));	
		assertEquals(want2, reduced2);
	}
	
	@Test
	public void namedTermErased1Test() {
		Application a = new Application(
				new NamedTerm("Hi",
						new Abstraction("x",
								new BoundVariable(1))),
				new FreeVariable("y"));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new FreeVariable("y"), reduced);
	}
	
	@Test
	public void namedTermErased2Test() {
		// Not really a valid lambda term
		Application a = new Application(
				new Abstraction("x",
						new NamedTerm("Hi",
								new BoundVariable(1))),
				new FreeVariable("y"));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new FreeVariable("y"), reduced);
	}
	
	@Test
	public void namedTermErased3Test() {
		Application a = new Application(
				new NamedTerm("Hi",
						new Abstraction("x",
								new FreeVariable("y"))),
				new FreeVariable("z"));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new FreeVariable("y"), reduced);
	}
	
	@Test
	public void namedTermErased4Test() {
		Application a = new Application(
				new Abstraction("x",
						new BoundVariable(1)),
				new FreeVariable("y"));
		
		NamedTerm n = new NamedTerm("Hi", a);
		
		LambdaTerm reduced = n.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new FreeVariable("y"), reduced);
	}
	
	@Test
	public void namedTermErased5Test() {
		Application a = new Application(
				new Abstraction("x",
						new BoundVariable(1)),
				new Abstraction("x",
						new BoundVariable(1)));
		
		NamedTerm n = new NamedTerm("Hi", a);
		
		LambdaTerm reduced = n.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new Abstraction("x", new BoundVariable(1)), reduced);
	}
	
	@Test
	public void namedTermPreserved1Test() {
		Application a = new Application(
				new NamedTerm("Hi",
						new Abstraction("x",
								new BoundVariable(1))),
				new Abstraction("x",
						new BoundVariable(1)));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new NamedTerm("Hi", new Abstraction("x", new BoundVariable(1))), reduced);
	}
	
	@Test
	public void namedTermPreserved2Test() {
		Application a = new Application(
				new Abstraction("x",
						new NamedTerm("Hi",
								new FreeVariable("y"))),
				new FreeVariable("z"));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new NamedTerm("Hi", new FreeVariable("y")), reduced);
	}
	
	@Test
	public void namedTermPreserved3Test() {
		Application a = new Application(
				new Abstraction("x",
						new BoundVariable(1)),
				new NamedTerm("Hi",
						new FreeVariable("y")));
		
		LambdaTerm reduced = a.acceptVisitor(new BetaReducer(a));
		
		assertEquals(new NamedTerm("Hi", new FreeVariable("y")), reduced);
	}
	
	@Rule
	public ExpectedException expect = ExpectedException.none();
	
	@Test
	public void notARegexTest() {
		Application a = new Application(new FreeVariable("y"), new FreeVariable("z"));
		Application app = new Application(a, new FreeVariable("y"));
		
		expect.expect(IllegalArgumentException.class);
		app.acceptVisitor(new BetaReducer(a));
	}
	
	private class MockPartial extends PartialApplication {

		private LambdaTerm onAccel;
		
		public MockPartial(LambdaTerm inner, int numParameters, List<Visitor<Boolean>> checks, LambdaTerm onAccel) {
			super("Nothing", inner, numParameters, checks);
			this.onAccel = onAccel;
		}
		
		private MockPartial() {
			super();
		}

		@Override
		public StringBuilder serialize() {
			return null;
		}

		@Override
		protected LambdaTerm accelerate(LambdaTerm[] parameters) {
			return onAccel;
		}

		@Override
		public boolean equals(Object other) {
			if (!super.equals(other))
				return false;
			
			if (!(other instanceof MockPartial))
				return false;
			
			MockPartial otherMP = (MockPartial)other;
			
			return onAccel.equals(otherMP.onAccel);
		}

		@Override
		public PartialApplication clone() {
			MockPartial cloned = new MockPartial();
			cloned.absorbClone(this);
			cloned.onAccel = onAccel.clone();
			return cloned;
		}
		
	}
	
	private class ConstantVisitor<T> extends NameAgnosticVisitor<T> {
		T result;
		
		public ConstantVisitor(T value) {
			this.result = value;
		}

		@Override
		public T visitPartialApplication(PartialApplication app) {
			return result;
		}

		@Override
		public T visitAbstraction(Abstraction abs) {
			return result;
		}

		@Override
		public T visitApplication(Application app) {
			return result;
		}

		@Override
		public T visitBoundVariable(BoundVariable var) {
			return result;
		}

		@Override
		public T visitFreeVariable(FreeVariable var) {
			return result;
		}
	}
	
	@Test
	public void partialApplication1Test() {
		LambdaTerm namedId = new NamedTerm("named", new Abstraction("x", new Abstraction("y", new BoundVariable(1))));
		LambdaTerm accelId = new NamedTerm("accel", new Abstraction("x", new BoundVariable(1)));
		
		MockPartial part = new MockPartial(namedId, 2,
				Arrays.asList(new ConstantVisitor<>(true), new ConstantVisitor<>(false)), accelId);
		Application a = new Application(part, new FreeVariable("a"));
		Application b = new Application(a, new FreeVariable("b"));
		
		LambdaTerm reduced1 = b.acceptVisitor(new BetaReducer(a));
		
		LambdaTerm want1 = new Application(part.accept(new FreeVariable("a")), new FreeVariable("b"));
		assertEquals(want1, reduced1);
		
		LambdaTerm reduced2 = reduced1.acceptVisitor(new BetaReducer((Application)reduced1));
		LambdaTerm want2 = new Application(
				new Application(new NamedTerm("Nothing", namedId), new FreeVariable("a")),
				new FreeVariable("b"));
		
		assertEquals(want2, reduced2);
	}
	
	@Test
	public void partialApplication2Test() {
		LambdaTerm namedId = new NamedTerm("named", new Abstraction("x", new Abstraction("y", new BoundVariable(1))));
		LambdaTerm accelId = new NamedTerm("accel", new Abstraction("x", new BoundVariable(1)));
		
		MockPartial part = new MockPartial(namedId, 2,
				Arrays.asList(new ConstantVisitor<>(true), new ConstantVisitor<>(true)), accelId);
		Application a = new Application(part, new FreeVariable("a"));
		Application b = new Application(a, new FreeVariable("b"));
		
		LambdaTerm reduced1 = b.acceptVisitor(new BetaReducer(a));
		
		LambdaTerm want1 = new Application(part.accept(new FreeVariable("a")), new FreeVariable("b"));
		assertEquals(want1, reduced1);
		
		LambdaTerm reduced2 = reduced1.acceptVisitor(new BetaReducer((Application)reduced1));
		
		assertEquals(accelId, reduced2);
	}

}
