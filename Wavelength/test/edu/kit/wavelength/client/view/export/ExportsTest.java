package edu.kit.wavelength.client.view.export;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ExportsTest {

	@Test
	public void allTest() {
		List<Export> exports = Exports.all();
		assertEquals(exports.size(), 5);
		String[] names = { "Plaintext",
				"Unicode",
				"LaTeX",
				"Haskell",
				"Lisp" };
		for (int i = 0; i < exports.size(); i++) {
			assertEquals(exports.get(i).getName(), names[i]);
		}
	}
}
