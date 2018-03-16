package webdriver.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import webdriver.driver.Driver;

public class CheckBox {

	private final Driver driver;
	private final String id;
	private final String text;
	
	private CheckBox(Driver driver, String id, String text) {
		this.driver = driver;
		this.id = id;
		this.text = text;
	}
	
	public static CheckBox byID(Driver driver, String id) {
		return new CheckBox(driver, id, null);
	}
	
	public static CheckBox byText(Driver driver, String parentID, String text) {
		return new CheckBox(driver, parentID, text);
	}
	
	private WebElement box() {
		return Finder.find(driver, id, text);
	}
	
	public void toggle() {
		box().click();
	}
	
	public boolean isSet() {
		return box().isSelected();
	}
	
	public boolean isSelected() {
		return box().isSelected();
	}
	
	public void hover() {
		new Actions(driver).moveToElement(box()).perform();
	}
	
}
