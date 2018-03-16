package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import webdriver.driver.Driver;

public class Popup {
	
	private final Driver driver;
	private final String id;
	
	private Popup(Driver driver, String id) {
		this.driver = driver;
		this.id = id;
	}
	
	public static Popup byID(Driver driver, String id) {
		return new Popup(driver, id);
	}
	
	public WebElement popup() {
		return driver.findElement(By.id(id));
	}
	
	public boolean isVisible() {
		return driver.hasElement(By.id(id)) && popup().isDisplayed();
	}
	
	public void hover() {
		new Actions(driver).moveToElement(popup()).perform();
	}
	
}
