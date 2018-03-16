package webdriver.output;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import webdriver.driver.Driver;

public class URL implements OutputNode {

	private final Driver driver;
	private final WebElement element;
	
	public URL(Driver driver, WebElement element) {
		this.driver = driver;
		this.element = element;
	}

	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitURL(this);
	}
	
	public String content() {
		return element.getText();
	}
	
	public List<String> classes() {
		return Arrays.asList(element.getAttribute("class").split(" "));
	}
	
	public void click() {
		// send enter instead of click because clicking will also (randomly) focus the element
		element.sendKeys(Keys.RETURN);
	}
	
	public void hover() {
		new Actions(driver).moveToElement(element).perform();
	}
	
}
