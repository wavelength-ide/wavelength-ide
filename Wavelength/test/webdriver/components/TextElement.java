package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import webdriver.driver.Driver;

public class TextElement {
	
	private final Driver driver;
	private final String id;
	
	private TextElement(Driver driver, String id) {
		this.driver = driver;
		this.id = id;
	}
	
	public static TextElement byID(Driver driver, String id) {
		return new TextElement(driver, id);
	}
	
	private WebElement element() {
		return driver.findElement(By.id(id));
	}
	
	public String read() {
		return element().getAttribute("value");
	}
	
	public boolean isVisible() {
		return element().isDisplayed();
	}
	
	public void hover() {
		new Actions(driver).moveToElement(element()).perform();
	}
	
	public void waitForText() {
		new WebDriverWait(driver, 2).until(d -> read().length() > 0);
	}
	
}
