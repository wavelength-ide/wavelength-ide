package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SerializationTest {

	@Test
	public void basicTest() {
		LambdaTerm[] ts = new LambdaTerm[] {
				new Application(new Abstraction("x", new BoundVariable(1)),
						new Abstraction("y", new BoundVariable(1))),
				new Application(new Abstraction("x", new BoundVariable(1)),
						new FreeVariable("y")),
				new Application(new NamedTerm("AEinziemlichlangerNameAaaabffnnn+^",
						new Abstraction("x", new Application(new BoundVariable(1), new FreeVariable("q")))),
						new Application(new BoundVariable(25), new BoundVariable(13))),
				new Application(new PartialApplication.Addition(), LambdaTerm.churchNumber(12)),
				new Application(
						new Application(new PartialApplication.Subtraction(), new PartialApplication.Successor()),
						new Application(new PartialApplication.Multiplication(),
								new Application(new PartialApplication.Predecessor(), new PartialApplication.Exponentiation()))),
				new PartialApplication.Addition().accept(LambdaTerm.churchNumber(12))
		};
		
		for (int i = 0; i < ts.length; ++i) {
			assertEquals(ts[i], LambdaTerm.deserialize(ts[i].serialize().toString()));
		}
	}
}
