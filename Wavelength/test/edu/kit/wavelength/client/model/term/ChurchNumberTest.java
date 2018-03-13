package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChurchNumberTest {

	@Test
	public void generateAndVisit() {
		for (int i = 0; i < 1000; ++i) {
			assertEquals(new Integer(i), LambdaTerm.churchNumber(i).acceptVisitor(new GetChurchNumberVisitor()));
		}
	}

	@Test
	public void basic() {
		assertEquals(new NamedTerm("0", new Abstraction("s", new Abstraction("z", new BoundVariable(1)))),
				LambdaTerm.churchNumber(0));
		assertEquals(
				new NamedTerm("1",
						new Abstraction("s",
								new Abstraction("z", new Application(new BoundVariable(2), new BoundVariable(1))))),
				LambdaTerm.churchNumber(1));
		assertEquals(
				new NamedTerm("2",
						new Abstraction("s",
								new Abstraction("z",
										new Application(new BoundVariable(2),
												new Application(new BoundVariable(2), new BoundVariable(1)))))),
				LambdaTerm.churchNumber(2));
	}
}
