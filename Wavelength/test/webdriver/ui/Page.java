package webdriver.ui;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import webdriver.components.Button;
import webdriver.components.CheckBox;
import webdriver.components.Editor;
import webdriver.components.ListBox;
import webdriver.components.Popup;
import webdriver.components.TextElement;
import webdriver.driver.Driver;
import webdriver.output.UnicodeOutput;

public class Page {

	public static final List<String> libraryNames = Arrays.asList(
			"Accelerated Natural Numbers", 
			"Natural Numbers", 
			"Church Boolean", 
			"Church Tuples and Lists", 
			"Y-Combinator");
	
	public static final List<String> exerciseNames = Arrays.asList(
			"β-Redex - Normal Order*",
			"β-Redex - Call by Name*",
			"β-Redex - Call by Value*",
			"β-Redex - Applicative Order*",
			"Exercise mode",
			"Variables",
			"Variable II",
			"Normal Order",
			"Applicative Order",
			"Call-by-Name",
			"Call-by-Value",
			"α-conversion");
	
	public static final List<String> exportFormatNames = Arrays.asList(
			"Plaintext",
			"Unicode",
			"LaTeX",
			"Haskell",
			"Lisp");	
			
	private final Driver driver;
	
	public Page() {
		driver = new Driver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:8888/Wavelength.html");
	}
	
	public void reset() {
		if (loadExercisePopup().isVisible()) {
			loadExercisePopupCancelButton().click();
		}
		if (exportPopup().isVisible()) {
			exportPopupOkButton().click();
		}
		if (exercisePanel().isVisible()) {
			closeExerciseButton().click();
		}
		if (closeExercisePopup().isVisible()) {
			closeExercisePopupOkButton().click();
		}
		if (!mainMenuPanel().isVisible()) {
			openMainMenuButton().click();
		}
		libraryNames.stream().forEach(name -> {
			CheckBox box = libraryCheckBox(name);
			if (box.isSelected()) {
				box.toggle();
			}
		});
		openMainMenuButton().click();
		if (terminateButton().isEnabled()) {
			terminateButton().click();
		}
		outputFormatBox().select("Unicode Output");
		reductionOrderBox().select("Normal Order");
		outputSizeBox().select("Full");
		if (exportMenu().isVisible()) {
			openExportMenuButton().click();
		}
		if (sharePanel().isVisible()) {
			shareButton().click();
		}
		editor().clear();
	}
	
	public void close() {
		driver.quit();
	}
	
	public boolean isLoaded() {
		return driver.getTitle().equals("Wavelength") && driver.findElements(By.id("mainPanel")).size() > 0;
	}
	
	public void waitForCompletion() {
		new WebDriverWait(driver, 20).until(d -> !spinner().isVisible());
	}
	
	public Button openMainMenuButton() {
		return Button.byID(driver, "openMainMenuButton");
	}
	
	public Popup mainMenuPanel() {
		return Popup.byID(driver, "mainMenuPanel");
	}
	
	public CheckBox libraryCheckBox(String text) {
		return CheckBox.byText(driver, "mainMenu", text);
	}
	
	public Button exerciseButton(String text) {
		return Button.byText(driver, "mainMenu", text);
	}
	
	public Editor editor() {
		return Editor.byID(driver, "editor");
	}
	
	public Popup exercisePanel() {
		return Popup.byID(driver, "exercisePanel");
	}
	
	public TextElement exerciseDescriptionLabel() {
		return TextElement.byID(driver, "exerciseDescriptionLabel");
	}
	
	public Button toggleSolutionButton() {
		return Button.byID(driver, "toggleSolutionButton");
	}
	
	public Button closeExerciseButton() {
		return Button.byID(driver, "closeExerciseButton");
	}
	
	public TextElement solutionArea() {
		return TextElement.byID(driver, "solutionArea");
	}
	
	public ListBox outputFormatBox() {
		return ListBox.byID(driver, "outputFormatBox");
	}
	
	public ListBox reductionOrderBox() {
		return ListBox.byID(driver, "reductionOrderBox");
	}
	
	public ListBox outputSizeBox() {
		return ListBox.byID(driver, "outputSizeBox");
	}
	
	public Button backwardButton() {
		return Button.byID(driver, "backwardButton");
	}
	
	public Button pauseButton() {
		return Button.byID(driver, "pauseButton");
	}
	
	public Button unpauseButton() {
		return Button.byID(driver, "unpauseButton");
	}
	
	public Button forwardButton() {
		return Button.byID(driver, "forwardButton");
	}
	
	public Popup spinner() {
		return Popup.byID(driver, "spinner");
	}
	
	public Button terminateButton() {
		return Button.byID(driver, "terminateButton");
	}
	
	public Button runButton() {
		return Button.byID(driver, "runButton");
	}
	
	public UnicodeOutput unicodeOutput() {
		return UnicodeOutput.byID(driver, "network");
	}
	
	public Button openExportMenuButton() {
		return Button.byID(driver, "openExportMenuButton");
	}
	
	public Popup exportMenu() {
		return Popup.byID(driver, "exportMenu");
	}
	
	public Button exportButton(String text) {
		return Button.byText(driver, "exportMenu", text);
	}
	
	public Button shareButton() {
		return Button.byID(driver, "shareButton");
	}
	
	public TextElement sharePanel() {
		return TextElement.byID(driver, "sharePanel");
	}
	
	public Popup loadExercisePopup() {
		return Popup.byID(driver, "loadExercisePopup");
	}
	
	public Button loadExercisePopupOkButton() {
		return Button.byID(driver, "loadExercisePopupOkButton");
	}
	
	public Button loadExercisePopupCancelButton() {
		return Button.byID(driver, "loadExercisePopupCancelButton");
	}
	
	public Popup closeExercisePopup() {
		return Popup.byID(driver, "closeExercisePopup");
	}
	
	public Button closeExercisePopupOkButton() {
		return Button.byID(driver, "closeExercisePopupOkButton");
	}
	
	public Button closeExercisePopupCancelButton() {
		return Button.byID(driver, "closeExercisePopupCancelButton");
	}
	
	public Popup exportPopup() {
		return Popup.byID(driver, "exportPopup");
	}
	
	public TextElement exportArea() {
		return TextElement.byID(driver, "exportArea");
	}
	
	public Button exportPopupOkButton() {
		return Button.byID(driver, "exportPopupOkButton");
	}
	
}
