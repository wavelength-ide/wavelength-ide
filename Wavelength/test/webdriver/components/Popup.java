package webdriver.components;

import org.openqa.selenium.By;

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
	
	public boolean isVisible() {
		return driver.hasElement(By.id(id)) && driver.findElement(By.id(id)).isDisplayed();
	}
	
}
