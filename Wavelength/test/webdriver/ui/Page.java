package webdriver.ui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import webdriver.components.Button;
import webdriver.components.CheckBox;
import webdriver.components.Editor;
import webdriver.components.Label;
import webdriver.components.ListBox;
import webdriver.components.Popup;
import webdriver.components.TextElement;
import webdriver.driver.Driver;
import webdriver.output.UnicodeOutput;

public class Page {	
			
	private final Driver driver;
	
	public Page() {
		driver = new Driver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:8888/Wavelength.html");
		waitForLoading();
	}
	
	public void reset() {
		if (!driver.getCurrentUrl().equals("http://127.0.0.1:8888/Wavelength.html")) {
			navigate("http://127.0.0.1:8888/Wavelength.html");
			editor().clear();
			return;
		}
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
		Library.all().stream().forEach(name -> {
			CheckBox box = libraryCheckBox(name);
			if (box.isSelected()) {
				box.toggle();
			}
		});
		openMainMenuButton().click();
		if (clearButton().isEnabled()) {
			clearButton().click();
		}
		outputFormatBox().select(OutputFormat.Unicode);
		reductionOrderBox().select(ReductionOrder.NormalOrder);
		outputSizeBox().select(OutputSize.Full);
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
	
	public void waitForLoading() {
		new WebDriverWait(driver, 20).until(d -> driver.hasElement(By.id("mainPanel")));
	}
	
	public boolean isLoaded() {
		return driver.getTitle().equals("Wavelength") && driver.hasElement(By.id("mainPanel"));
	}
	
	public void waitForCompletion() {
		new WebDriverWait(driver, 20).until(d -> !spinner().isVisible());
	}
	
	public void navigate(String url) {
		driver.get(url);
		waitForLoading();
	}
	
	public Button openMainMenuButton() {
		return Button.byID(driver, "openMainMenuButton");
	}
	
	public Popup mainMenuPanel() {
		return Popup.byID(driver, "mainMenuPanel");
	}
	
	public CheckBox libraryCheckBox(String name) {
		return CheckBox.byText(driver, "mainMenu", name);
	}
	
	public Button exerciseButton(String name) {
		return Button.byText(driver, "mainMenu", name);
	}
	
	public Editor editor() {
		return Editor.byID(driver, "editor");
	}
	
	public Popup exercisePanel() {
		return Popup.byID(driver, "exercisePanel");
	}
	
	public Label exerciseDescriptionLabel() {
		return Label.byID(driver, "exerciseDescriptionLabel");
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
	
	public Button clearButton() {
		return Button.byID(driver, "clearButton");
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
	
	public Button exportButton(String name) {
		return Button.byText(driver, "exportMenu", name);
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
