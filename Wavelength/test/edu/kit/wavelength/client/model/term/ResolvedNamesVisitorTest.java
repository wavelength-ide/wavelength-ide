package edu.kit.wavelength.client.model.term;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import edu.kit.wavelength.client.model.library.Library;

public class ResolvedNamesVisitorTest {
	
	static class ResolvedNamesVisitorTester extends ResolvedNamesVisitor<Map<LambdaTerm, String>> {

		public ResolvedNamesVisitorTester(List<Library> libraries) {
			super(libraries);
		}

		@Override
		public Map<LambdaTerm, String> visitApplication(Application app) {
			HashMap<LambdaTerm, String> t = new HashMap<>();
			t.putAll(app.getLeftHandSide().acceptVisitor(this));
			t.putAll(app.getRightHandSide().acceptVisitor(this));
			return t;
		}

		@Override
		public Map<LambdaTerm, String> visitNamedTerm(NamedTerm term) {
			return term.getInner().acceptVisitor(this);
		}

		@Override
		public Map<LambdaTerm, String> visitPartialApplication(PartialApplication app) {
			return app.getRepresented().acceptVisitor(this);
		}

		@Override
		public Map<LambdaTerm, String> visitFreeVariable(FreeVariable var) {
			return Collections.emptyMap();
		}

		@Override
		protected Map<LambdaTerm, String> visitBoundVariable(BoundVariable var, String resolvedName) {
			return Collections.singletonMap(var, resolvedName);
		}

		@Override
		protected Map<LambdaTerm, String> visitAbstraction(Abstraction abs, String resolvedName) {
			HashMap<LambdaTerm, String> t = new HashMap<>();
			t.putAll(abs.getInner().acceptVisitor(this));
			t.put(abs, resolvedName);
			return t;
		}
		
	}

	@Test
	public void basicTest() {
		FreeVariable f = new FreeVariable("x");
		Abstraction a = new Abstraction("x", f);
		LambdaTerm t = new Abstraction("x", a);
		
		Map<LambdaTerm, String> m = t.acceptVisitor(new ResolvedNamesVisitorTester(Collections.emptyList()));
		
		assertNotEquals(m.get(a), m.get(t));
		assertNotEquals(m.get(a), "x");
		assertNotEquals(m.get(t), "x");
	}
	
	@Test
	public void abstractionMatchingTest() {
		BoundVariable b1 = new BoundVariable(1);
		BoundVariable b2 = new BoundVariable(2);
		
		Application a = new Application(b1, b2);
		
		Abstraction a1 = new Abstraction("x", a);
		Abstraction a2 = new Abstraction("x", a1);
		
		Map<LambdaTerm, String> m = a2.acceptVisitor(new ResolvedNamesVisitorTester(Collections.emptyList()));
		
		assertNotEquals(m.get(a1), m.get(a2));
		assertEquals(m.get(a1), m.get(b1));
		assertEquals(m.get(a2), m.get(b2));
	}
	
	private <T> void assertPairwiseNotEquals(List<T> items) {
		int size = items.size();
		for (int i = 0; i < size; ++i) {
			for (int j = i + 1; j < size; ++j) {
				assertNotEquals(items.get(i), items.get(j));
			}
		}
	}
	
	@Test
	public void namedTermTest() {
		FreeVariable f = new FreeVariable("x1");
		NamedTerm t = new NamedTerm("x", f);
		Abstraction a = new Abstraction("x", t);
		
		Map<LambdaTerm, String> m = a.acceptVisitor(new ResolvedNamesVisitorTester(Collections.emptyList()));
		
		assertPairwiseNotEquals(Arrays.asList("x1", "x", m.get(a)));
	}
	
	@Test
	public void libraryTest() {
		Library l = new Library() {
			@Override
			public StringBuilder serialize() {
				return null;
			}

			@Override
			public LambdaTerm getTerm(String name) {
				return new FreeVariable("y");
			}

			@Override
			public boolean containsName(String name) {
				return name.equals("x");
			}

			@Override
			public String getName() {
				return "Test Library";
			}
		};
	
		FreeVariable f = new FreeVariable("x1");
		Abstraction a1 = new Abstraction("y", f);
		Abstraction a2 = new Abstraction("x", a1);
		
		Map<LambdaTerm, String> m = a2.acceptVisitor(new ResolvedNamesVisitorTester(Collections.singletonList(l)));
		
		assertPairwiseNotEquals(Arrays.asList("x", "x1", m.get(a1), m.get(a2)));
		assertEquals(m.get(a1), "y");
	}

}
