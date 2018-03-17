package webdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
		
		p.reset();
		
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
		p.reset();
		
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
	
	@Test
	public void T3() {
		p.reset();
		
		// T3.1
		p.editor().write("(\\x. x x) y");
		p.reductionOrderBox().select("Applicative Order");
		p.outputSizeBox().select("Result only");
		p.forwardButton().click();
		p.forwardButton().click();
		String oldOutput = p.unicodeOutput().readText();
		assertEquals("(λx.x x) y\n" + 
		             "y y", oldOutput);
		
		// T3.2
		p.shareButton().click();
		assertTrue(p.sharePanel().isVisible());
		p.sharePanel().waitForText();
		assertTrue(!p.sharePanel().read().isEmpty());
		
		// T3.3
		String url = p.sharePanel().read();
		p.navigate(url);
		assertEquals("(\\x. x x) y", p.editor().read());
		assertEquals(oldOutput, p.unicodeOutput().readText());
		assertEquals("Applicative Order", p.reductionOrderBox().readSelected());
		assertEquals("Result only", p.outputSizeBox().readSelected());
		
		// T3.4
		p.backwardButton().click();
		assertEquals("(λx.x x) y", p.unicodeOutput().readText());
	}
	
	@Test
	public void T4() {
		p.reset();
		
		// T4.1
		p.openMainMenuButton().click();
		assertTrue(p.mainMenuPanel().isVisible());
		p.exerciseButton("Exercise mode").click();
		assertTrue(p.loadExercisePopup().isVisible());
		p.loadExercisePopupOkButton().click();
		assertTrue(p.exerciseDescriptionLabel().isVisible());
		assertTrue(p.exerciseDescriptionLabel().read().length() > 0);
		
		// T4.2
		// feature for testing of exercise solutions was removed in design phase
		
		// T4.3
		// feature for testing of exercise solutions was removed in design phase
		
		// T4.4
		p.openMainMenuButton().click();
		p.exerciseButton("Variable II").click();
		p.loadExercisePopupOkButton().click();
		assertTrue(p.exerciseDescriptionLabel().isVisible());
		assertTrue(!p.exerciseDescriptionLabel().read().isEmpty());
		
		// T4.5
		// (feature for testing of exercise solutions was removed in design phase)
		while (p.forwardButton().isEnabled()) {
			p.forwardButton().click();
		}
		assertEquals("(λx.x y) (λy.y x) ((λv.x v) (λx.x))\n" + 
		             "(λy.y x) y ((λv.x v) (λx.x))\n" + 
		             "y x ((λv.x v) (λx.x))\n" + 
		             "y x (x (λx.x))", p.unicodeOutput().readText());
		
		// T4.6
		p.toggleSolutionButton().click();
		assertTrue(p.solutionArea().isVisible());
		assertTrue(!p.solutionArea().read().isEmpty());
		
		// T4.7
		// not testable as there are no exercises where the solution can be copied to the input
		
		// T4.8
		p.toggleSolutionButton().click();
		assertFalse(p.solutionArea().isVisible());
		
		// T4.9
		p.closeExerciseButton().click();
		assertTrue(p.closeExercisePopup().isVisible());
		p.closeExercisePopupOkButton().click();
		assertFalse(p.exerciseDescriptionLabel().isVisible());
	}
	
	@Test
	public void T7() {
		p.reset();
		
		// T7.1
		p.openMainMenuButton().click();
		assertTrue(p.mainMenuPanel().isVisible());
		p.libraryCheckBox("Church Tuples and Lists").toggle();
		assertTrue(p.libraryCheckBox("Church Tuples and Lists").isSelected());
		p.openMainMenuButton().click();
		assertFalse(p.mainMenuPanel().isVisible());
		p.editor().write("curry = \\f. \\a. \\b. f (pair a b)\n" + 
		                 "(curry (\\p. (first p) (second p))) x x");
		p.runButton().click();
		assertEquals("curry (λp.first p (second p)) x x\n" + 
		             "(λa.λb.(λp.first p (second p)) (pair a b)) x x\n" + 
		             "(λb.(λp.first p (second p)) (pair x b)) x\n" + 
		             "(λp.first p (second p)) (pair x x)\n" + 
		             "first (pair x x) (second (pair x x))\n" + 
		             "pair x x (λx.λy.x) (second (pair x x))\n" + 
		             "(λy.λz.z x y) x (λx.λy.x) (second (pair x x))\n" + 
		             "(λz.z x x) (λx.λy.x) (second (pair x x))\n" + 
		             "(λx.λy.x) x x (second (pair x x))\n" + 
		             "(λy.x) x (second (pair x x))\n" + 
		             "x (second (pair x x))\n" + 
		             "x (pair x x (λx.λy.y))\n" + 
		             "x ((λy.λz.z x y) x (λx.λy.y))\n" + 
		             "x ((λz.z x x) (λx.λy.y))\n" + 
		             "x ((λx.λy.y) x x)\n" + 
		             "x ((λy.y) x)\n" + 
		             "x x", p.unicodeOutput().readText());
		
		// T7.2
		p.editor().write("foo.bar = \\x. x\n" + 
		                 "foo.bar y");
		p.runButton().click();
		assertEquals("\"foo.bar = \\x. x\" is not a valid name assignment (row 1, colums 1-16)", p.unicodeOutput().readText());
		
		// T7.3
		// spec for binding two terms was changed to shadow earlier terms, so we test that instead
		p.editor().write("f = \\x. x\n" +
		                 "f = \\x. x x\n" +
		                 "f y");
		p.runButton().click();
		assertEquals("f y\n" +
		             "y y", p.unicodeOutput().readText());
	}
	
	@Test
	public void T8() {
		p.reset();
		
		// T8.1
		p.openMainMenuButton().click();
		p.libraryCheckBox("Church Tuples and Lists").toggle();
		p.openMainMenuButton().click();
		p.editor().write("curry = \\f. \\a. \\b. f (pair a b)\n" + 
		                 "(curry (\\p. (first p) (second p))) x x");
		p.outputSizeBox().select("Result only");
		assertEquals("Result only", p.outputSizeBox().readSelected());
		p.runButton().click();
		assertEquals("curry (λp.first p (second p)) x x\n" + 
		             "x x", p.unicodeOutput().readText());
		
		// T8.2
		p.outputSizeBox().select("Full");
		assertEquals("Full", p.outputSizeBox().readSelected());
		p.runButton().click();
		assertEquals("curry (λp.first p (second p)) x x\n" + 
		             "(λa.λb.(λp.first p (second p)) (pair a b)) x x\n" + 
		             "(λb.(λp.first p (second p)) (pair x b)) x\n" + 
		             "(λp.first p (second p)) (pair x x)\n" + 
		             "first (pair x x) (second (pair x x))\n" + 
		             "pair x x (λx.λy.x) (second (pair x x))\n" + 
		             "(λy.λz.z x y) x (λx.λy.x) (second (pair x x))\n" + 
		             "(λz.z x x) (λx.λy.x) (second (pair x x))\n" + 
		             "(λx.λy.x) x x (second (pair x x))\n" + 
		             "(λy.x) x (second (pair x x))\n" + 
		             "x (second (pair x x))\n" + 
		             "x (pair x x (λx.λy.y))\n" + 
		             "x ((λy.λz.z x y) x (λx.λy.y))\n" + 
		             "x ((λz.z x x) (λx.λy.y))\n" + 
		             "x ((λx.λy.y) x x)\n" + 
		             "x ((λy.y) x)\n" + 
		             "x x", p.unicodeOutput().readText());
		
		// T8.3
		// too hard to test
	}
	
	@Test
	public void T9() {
		p.reset();
		
		// T9.1
		p.editor().write("(\\x. x x)(\\x. x x)");
		p.outputSizeBox().select("Result only");
		p.runButton().click();
		try { Thread.sleep(500); } catch (InterruptedException e) {}
		assertTrue(p.spinner().isVisible());
		p.pauseButton().click();
		assertFalse(p.spinner().isVisible());
		assertEquals("(λx.x x) (λx.x x)\n" + 
		             "(λx.x x) (λx.x x)", p.unicodeOutput().readText());
		
		// T9.2
		p.unpauseButton().click();
		assertTrue(p.spinner().isVisible());
		
		// T9.3
		// clear button was respecified to do something else
		p.clearButton().click();
		assertTrue(p.unicodeOutput().readText().isEmpty());
		assertFalse(p.spinner().isVisible());
	}
	
	@AfterClass
	public static void tearDown() {
		p.close();
	}
	
}
