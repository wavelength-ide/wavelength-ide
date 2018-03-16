package webdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import webdriver.ui.Page;

public class Demo {
	
	private Page p;
	
	@Before
	public void setUp() {
		p = new Page();
	}
	
	@Test
	public void demoTest() {
		p.editor().write("(\\x. x x)(\\x. x x)");
		p.forwardButton().click();
		p.forwardButton().click();
		String output = p.unicodeOutput().readText();
		assertEquals(output, "(λx.x x) (λx.x x)\n(λx.x x) (λx.x x)");
		p.reset();
		p.forwardButton().click();
		p.backwardButton().click();
		assertTrue(p.unicodeOutput().isEmpty());
	}
	
	@After
	public void tearDown() {
		p.close();
	}
	
}
