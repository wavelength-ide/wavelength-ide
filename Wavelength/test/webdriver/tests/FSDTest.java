package webdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import webdriver.output.URL;
import webdriver.ui.Page;

public class FSDTest {

	private static Page p;
	
	@BeforeClass
	public static void setUp() {
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
		
		p.reset();
	}
	
	@Test
	public void T2() {
		// adjusted to fit new control
		// T2.1
		p.editor().write("(\\z. (\\y. (\\x. x) y) z) a");
		p.forwardButton().click();
		assertEquals("(λz.(λy.(λx.x) y) z) a", p.unicodeOutput().readText());
		String expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.2
		p.forwardButton().click();
		assertEquals("(λz.(λy.(λx.x) y) z) a\n" + 
		             "(λy.(λx.x) y) a", p.unicodeOutput().readText());
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
				            "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.3
		p.backwardButton().click();
		assertEquals("(λz.(λy.(λx.x) y) z) a", p.unicodeOutput().readText());
		
		// T2.4
		String htmlBeforeHover = p.unicodeOutput().readHTML();
		URL u = (URL) p.unicodeOutput().find("(span/span/span/span/span/span/a[@class='gwt-Anchor abstraction clickable'])[1]");
		u.hover();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application hover'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.5
		p.openMainMenuButton().hover();
		assertEquals(htmlBeforeHover, p.unicodeOutput().readHTML());
		
		// T2.6
		u.click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.7
		p.reductionOrderBox().select("Call by Name");
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.8
		p.forwardButton().click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.9
		p.backwardButton().click();
		p.reductionOrderBox().select("Call by Value");
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		p.reductionOrderBox().select("Applicative Order");
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		p.forwardButton().click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>.</span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>.</span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>.</span><span class='outputText'>z</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
	}
	
	@AfterClass
	public static void tearDown() {
		p.close();
	}
	
}
