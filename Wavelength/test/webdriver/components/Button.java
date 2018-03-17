package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import webdriver.driver.Driver;

public class Button {

	private final Driver driver;
	private final String id;
	private final String text;
	
	private Button(Driver driver, String id, String text) {
		this.driver = driver;
		this.id = id;
		this.text = text;
	}
	
	public static Button byID(Driver driver, String id) {
		return new Button(driver, id, null);
	}
	
	public static Button byText(Driver driver, String parentID, String text) {
		return new Button(driver, parentID, text);
	}
	
	private WebElement button() {
		WebElement button = driver.findElement(By.id(id));
		if (text != null) {
			button = button.findElement(By.xpath("//*[text()='" + text + "']"));
		}
		return button;
	}
	
	public void click() {
		// send enter instead of click to avoid issue
		// where chromedriver will complain that
		// button is not clickable if
		// invisible dropdown backdrop
		// is in front of button
		button().sendKeys(Keys.RETURN);
	}
	
	public boolean isEnabled() {
		return button().isEnabled();
	}
	
	public boolean isVisible() {
		boolean hasElement = true;
		driver.removeImplicitTimeout();
		if (driver.findElements(By.id(id)).size() == 0) {
			hasElement = false;
		} else {
			WebElement button = driver.findElement(By.id(id));
			if (text != null && button.findElements(By.xpath("//*[text()='" + text + "']")).size() == 0) {
				hasElement = false;
			}
		}
		driver.resetImplicitTimeout();
		return hasElement && button().isDisplayed();
	}
	
	public void hover() {
		new Actions(driver).moveToElement(button()).perform();
	}
	
}
