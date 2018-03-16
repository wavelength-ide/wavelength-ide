package webdriver.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

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
		return Finder.find(driver, id, text);
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
		return Finder.hasElement(driver, id, text) && button().isDisplayed();
	}
	
}
