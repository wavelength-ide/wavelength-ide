package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
		return element().getText();
	}
	
	public boolean isVisible() {
		return element().isDisplayed();
	}
	
}
