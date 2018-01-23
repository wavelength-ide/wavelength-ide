package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

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

}
