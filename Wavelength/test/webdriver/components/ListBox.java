package webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import webdriver.driver.Driver;

public class ListBox {

	private final Driver driver;
	private final String id;
	
	private ListBox(Driver driver, String id) {
		this.driver = driver;
		this.id = id;
	}
	
	public static ListBox byID(Driver driver, String id) {
		return new ListBox(driver, id);
	}
	
	private WebElement element() {
		return driver.findElement(By.id(id));
	}
	
	private Select box() {
		return new Select(element());
	}
	
	public String readSelected() {
		return box().getFirstSelectedOption().getAttribute("value");
	}
	
	public void select(String value) {
		box().selectByValue(value);
	}
	
	public void hover() {
		new Actions(driver).moveToElement(element()).perform();
	}
	
}
