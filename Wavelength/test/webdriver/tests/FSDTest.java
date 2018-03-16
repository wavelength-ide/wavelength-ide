package webdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import webdriver.ui.Page;

public class FSDTest {

	private Page p;
	
	@Before
	public void setUp() {
		p = new Page();
	}
	
	@Test
	public void T1() {
		// T1.1
		assertTrue(p.isLoaded());
		
		// T1.2
		// editor control testing is left out (responsibility of monaco)
		p.editor().write("(\\z. \\x. (\\y.y) x) z");
		assertEquals("(\\z. \\x. (\\y.y) x) z", p.editor().read());
		p.outputSizeBox().select("Result only");
		assertEquals("Result only", p.outputSizeBox().readSelected());
		p.outputSizeBox().select("Full");
		assertEquals("Full", p.outputSizeBox().readSelected());
		p.reductionOrderBox().select("Call by Name");
		assertEquals("Call by Name", p.reductionOrderBox().readSelected());
		
		// T1.3
		p.runButton().click();
		p.waitForCompletion();
		assertEquals("(λz.λx.(λy.y) x) z\n" + 
		             "λx.(λy.y) x", p.unicodeOutput().readText());
	}
	
	@After
	public void tearDown() {
		p.close();
	}
	
}
