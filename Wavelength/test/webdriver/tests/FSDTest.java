package webdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import webdriver.output.URL;
import webdriver.ui.Exercise;
import webdriver.ui.Export;
import webdriver.ui.Library;
import webdriver.ui.OutputSize;
import webdriver.ui.Page;
import webdriver.ui.ReductionOrder;

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
		p.editor().write("(\\z. \\x. (\\y. y) x) z");
		assertEquals("(\\z. \\x. (\\y. y) x) z", p.editor().read());
		p.outputSizeBox().select(OutputSize.ResultOnly);
		assertEquals(OutputSize.ResultOnly, p.outputSizeBox().readSelected());
		p.outputSizeBox().select(OutputSize.Full);
		assertEquals(OutputSize.Full, p.outputSizeBox().readSelected());
		p.reductionOrderBox().select(ReductionOrder.CallByName);
		assertEquals(ReductionOrder.CallByName, p.reductionOrderBox().readSelected());
		
		// T1.3
		p.runAndWait();
		assertEquals("(λz. λx. (λy. y) x) z\n" + 
		             "λx. (λy. y) x", p.unicodeOutput().readText());
		
		p.reset();
	}
	
	@Test
	public void T2() {
		p.reset();
		
		// adjusted to fit new control
		// T2.1
		p.editor().write("(\\z. (\\y. (\\x. x) y) z) a");
		p.forwardButton().click();
		assertEquals("(λz. (λy. (λx. x) y) z) a", p.unicodeOutput().readText());
		
		String expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.2
		p.forwardButton().click();
		assertEquals("(λz. (λy. (λx. x) y) z) a\n" + 
		             "(λy. (λx. x) y) a", p.unicodeOutput().readText());
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.3
		p.backwardButton().click();
		assertEquals("(λz. (λy. (λx. x) y) z) a", p.unicodeOutput().readText());
		
		// T2.4
		String htmlBeforeHover = p.unicodeOutput().readHTML();
		URL u = (URL) p.unicodeOutput().find("(span/span/span/span/span/span/a[@class='gwt-Anchor abstraction clickable'])[1]");
		u.hover();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application hover'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.5
		p.openMainMenuButton().hover();
		assertEquals(htmlBeforeHover, p.unicodeOutput().readHTML());
		
		// T2.6
		u.click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.7
		p.reductionOrderBox().select(ReductionOrder.CallByName);
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.8
		p.forwardButton().click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
				"<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
				"<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T2.9
		p.backwardButton().click();
		p.reductionOrderBox().select(ReductionOrder.CallByValue);
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		p.reductionOrderBox().select(ReductionOrder.ApplicativeOrder);
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		p.forwardButton().click();
		expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='applicationWrapper application reduced'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λy</a><span class='outputText'>. </span><span class='outputText'>y</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>z</span></span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>\n" + 
		               "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λz</a><span class='outputText'>. </span><span class='outputText'>z</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>a</span></span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
	}
	
	@Test
	public void T3() {
		p.reset();
		
		// T3.1
		p.editor().write("(\\x. x x) y");
		p.reductionOrderBox().select(ReductionOrder.ApplicativeOrder);
		p.outputSizeBox().select(OutputSize.ResultOnly);
		p.forwardButton().click();
		p.forwardButton().click();
		String oldOutput = p.unicodeOutput().readText();
		assertEquals("(λx. x x) y\n" + 
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
		assertEquals(ReductionOrder.ApplicativeOrder, p.reductionOrderBox().readSelected());
		assertEquals(OutputSize.ResultOnly, p.outputSizeBox().readSelected());
		
		// T3.4
		p.backwardButton().click();
		assertEquals("(λx. x x) y", p.unicodeOutput().readText());
	}
	
	@Test
	public void T4() {
		p.reset();
		
		// T4.1
		p.openMainMenuButton().click();
		assertTrue(p.mainMenuPanel().isVisible());
		p.exerciseButton(Exercise.ExerciseMode).click();
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
		p.exerciseButton(Exercise.Variables2).click();
		p.loadExercisePopupOkButton().click();
		assertTrue(p.exerciseDescriptionLabel().isVisible());
		assertTrue(!p.exerciseDescriptionLabel().read().isEmpty());
		
		// T4.5
		// (feature for testing of exercise solutions was removed in design phase)
		while (p.forwardButton().isEnabled()) {
			p.forwardButton().click();
		}
		assertEquals("(λx. x y) (λy. y x) ((λv. x v) (λx. x))\n" + 
		             "(λy. y x) y ((λv. x v) (λx. x))\n" + 
		             "y x ((λv. x v) (λx. x))\n" + 
		             "y x (x (λx. x))", p.unicodeOutput().readText());
		
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
	public void T5() {
		p.reset();
		
		// T5.1
		p.editor().write("(\\x. x) y");
		p.runAndWait();
		String expectedHTML = "<span class='applicationWrapper nextRedex application'><span class='abstractionWrapper'><span class='outputText'>(</span><a class='gwt-Anchor abstraction clickable' href='javascript:;'>λx</a><span class='outputText'>. </span><span class='outputText'>x</span><span class='outputText'>)</span></span><span class='outputText'> </span><span class='outputText'>y</span></span>\n" + 
		                      "<span class='outputText'>y</span>";
		assertEquals(expectedHTML, p.unicodeOutput().readHTML());
		
		// T5.2
		// feature was disregarded at some point during development
		
		// T5.3
		// too hard to test
	}
	
	@Test
	public void T6() {
		p.reset();
		
		// T6.1
		p.editor().write("(\\x. x x) (\\x. x) (\\x. x) x");
		p.runAndWait();
		p.openExportMenuButton().click();
		assertTrue(p.exportMenu().isVisible());
		
		// T6.2
		p.exportButton(Export.LaTeX).click();
		assertTrue(p.exportPopup().isVisible());
		assertEquals("\\Rightarrow\\ (\\lambda x.\\, x\\: x)\\: (\\lambda x.\\, x)\\: (\\lambda x.\\, x)\\: x\\\\\n" + 
		             "\\Rightarrow\\ (\\lambda x.\\, x)\\: (\\lambda x.\\, x)\\: (\\lambda x.\\, x)\\: x\\\\\n" + 
		             "\\Rightarrow\\ (\\lambda x.\\, x)\\: (\\lambda x.\\, x)\\: x\\\\\n" + 
		             "\\Rightarrow\\ (\\lambda x.\\, x)\\: x\\\\\n" + 
		             "\\Rightarrow\\ x", p.exportArea().read());
		
		// T6.3
		p.exportPopupOkButton().click();
		assertFalse(p.exportPopup().isVisible());
	}
	
	@Test
	public void T7() {
		p.reset();
		
		// T7.1
		p.openMainMenuButton().click();
		assertTrue(p.mainMenuPanel().isVisible());
		p.libraryCheckBox(Library.ChurchLists).toggle();
		assertTrue(p.libraryCheckBox(Library.ChurchLists).isSelected());
		p.openMainMenuButton().click();
		assertFalse(p.mainMenuPanel().isVisible());
		p.editor().write("curry = \\f. \\a. \\b. f (pair a b)\n" + 
		                 "(curry (\\p. (first p) (second p))) x x");
		p.runAndWait();
		assertEquals("curry (λp. first p (second p)) x x\n" + 
		             "(λa. λb. (λp. first p (second p)) (pair a b)) x x\n" + 
		             "(λb. (λp. first p (second p)) (pair x b)) x\n" + 
		             "(λp. first p (second p)) (pair x x)\n" + 
		             "first (pair x x) (second (pair x x))\n" + 
		             "pair x x (λx. λy. x) (second (pair x x))\n" + 
		             "(λy. λz. z x y) x (λx. λy. x) (second (pair x x))\n" + 
		             "(λz. z x x) (λx. λy. x) (second (pair x x))\n" + 
		             "(λx. λy. x) x x (second (pair x x))\n" + 
		             "(λy. x) x (second (pair x x))\n" + 
		             "x (second (pair x x))\n" + 
		             "x (pair x x (λx. λy. y))\n" + 
		             "x ((λy. λz. z x y) x (λx. λy. y))\n" + 
		             "x ((λz. z x x) (λx. λy. y))\n" + 
		             "x ((λx. λy. y) x x)\n" + 
		             "x ((λy. y) x)\n" + 
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
		p.runAndWait();
		assertEquals("f y\n" +
		             "y y", p.unicodeOutput().readText());
	}
	
	@Test
	public void T8() {
		p.reset();
		
		// T8.1
		p.openMainMenuButton().click();
		p.libraryCheckBox(Library.ChurchLists).toggle();
		p.openMainMenuButton().click();
		p.editor().write("curry = \\f. \\a. \\b. f (pair a b)\n" + 
		                 "(curry (\\p. (first p) (second p))) x x");
		p.outputSizeBox().select(OutputSize.ResultOnly);
		assertEquals(OutputSize.ResultOnly, p.outputSizeBox().readSelected());
		p.runAndWait();
		assertEquals("curry (λp. first p (second p)) x x\n" + 
		             "x x", p.unicodeOutput().readText());
		
		// T8.2
		p.outputSizeBox().select(OutputSize.Full);
		assertEquals(OutputSize.Full, p.outputSizeBox().readSelected());
		p.runAndWait();
		assertEquals("curry (λp. first p (second p)) x x\n" + 
		             "(λa. λb. (λp. first p (second p)) (pair a b)) x x\n" + 
		             "(λb. (λp. first p (second p)) (pair x b)) x\n" + 
		             "(λp. first p (second p)) (pair x x)\n" + 
		             "first (pair x x) (second (pair x x))\n" + 
		             "pair x x (λx. λy. x) (second (pair x x))\n" + 
		             "(λy. λz. z x y) x (λx. λy. x) (second (pair x x))\n" + 
		             "(λz. z x x) (λx. λy. x) (second (pair x x))\n" + 
		             "(λx. λy. x) x x (second (pair x x))\n" + 
		             "(λy. x) x (second (pair x x))\n" + 
		             "x (second (pair x x))\n" + 
		             "x (pair x x (λx. λy. y))\n" + 
		             "x ((λy. λz. z x y) x (λx. λy. y))\n" + 
		             "x ((λz. z x x) (λx. λy. y))\n" + 
		             "x ((λx. λy. y) x x)\n" + 
		             "x ((λy. y) x)\n" + 
		             "x x", p.unicodeOutput().readText());
		
		// T8.3
		// too hard to test
	}
	
	@Test
	public void T9() {
		p.reset();
		
		// T9.1
		p.editor().write("(\\x. x x)(\\x. x x)");
		p.outputSizeBox().select(OutputSize.ResultOnly);
		p.runButton().click();
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertTrue(p.spinner().isVisible());
		p.pauseButton().click();
		assertFalse(p.spinner().isVisible());
		assertEquals("(λx. x x) (λx. x x)\n" + 
		             "(λx. x x) (λx. x x)", p.unicodeOutput().readText());
		
		// T9.2
		p.unpauseButton().click();
		assertTrue(p.spinner().isVisible());
		
		// T9.3
		// clear button was respecified to do something else
		p.clearButton().click();
		assertTrue(p.unicodeOutput().readText().isEmpty());
		assertFalse(p.spinner().isVisible());
	}
	
	@Test
	public void T10() {
		p.reset();
		
		// T10.1
		p.openMainMenuButton().click();
		p.libraryCheckBox(Library.YCombinator).toggle();
		assertTrue(p.libraryCheckBox(Library.YCombinator).isSelected());
		
		// T10.2
		p.libraryCheckBox(Library.NaturalNumbers).toggle();
		p.libraryCheckBox(Library.ChurchBooleans).toggle();
		p.openMainMenuButton().click();
		p.editor().write("isZero = \\n. n (\\x. false) true\n" + 
		                 "Y (\\f. \\x. (ifThenElse (isZero x) 1 (times x (f (pred x))))) 2");
		p.outputSizeBox().select(OutputSize.ResultOnly);
		p.runAndWait();
		assertEquals("Y (λf. λx. ifThenElse (isZero x) 1 (times x (f (pred x)))) 2\n" + 
		             "λs. λz. s (s z)", p.unicodeOutput().readText());
		
		// T10.3
		p.editor().write("pred (succ (succ 1))");
		p.runAndWait();
		assertEquals("pred (succ (succ 1))\n" + 
		             "λs. λz. s (s z)", p.unicodeOutput().readText());
		
		// T10.4
		p.editor().write("pow (times 2 (minus (plus 2 2) 3)) 2");
		p.runAndWait();
		assertEquals("pow (times 2 (minus (plus 2 2) 3)) 2\n" + 
		             "λs. λz. s (s (s (s z)))", p.unicodeOutput().readText());
		
		// T10.5
		p.editor().write("times 2 3");
		p.runAndWait();
		assertEquals("times 2 3\n" + 
		             "λs. λz. s (s (s (s (s (s z)))))", p.unicodeOutput().readText());
		
		// T10.6
		p.editor().write("true a b");
		p.runAndWait();
		assertEquals("true a b\n" +
		             "a", p.unicodeOutput().readText());
		
		// T10.7
		p.editor().write("false a b");
		p.runAndWait();
		assertEquals("false a b\n" +
		             "b", p.unicodeOutput().readText());
		
		// T10.8
		p.openMainMenuButton().click();
		p.libraryCheckBox(Library.ChurchLists).toggle();
		p.openMainMenuButton().click();
		// car and cdr are now first and second
		p.editor().write("p = pair 1 2\n" + 
		                 "plus (first p) (second p)");
		p.runAndWait();
		assertEquals("plus (first p) (second p)\n" + 
				     "λs. λz. s (s (s z))", p.unicodeOutput().readText());
		
		// T10.9
		// null is now newList, cons is now prepend
		// spec contained error at this point: first and second are analogous to head and tail in lists
		p.editor().write("list = newList\n" + 
		                 "list2 = prepend a list\n" + 
		                 "list3 = prepend b list2\n" + 
		                 "(first list3) (second list3)");
		p.runAndWait();
		assertEquals("first list3 (second list3)\n" + 
		             "b (λz. z a list)", p.unicodeOutput().readText());
	}
	
	@AfterClass
	public static void tearDown() {
		p.close();
	}
	
}
