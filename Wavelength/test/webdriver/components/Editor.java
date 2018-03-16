package webdriver.components;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import webdriver.driver.Driver;

public class Editor {
	
	private final Driver driver;
	private final String id;
	
	private Editor(Driver driver, String id) {
		this.driver = driver;
		this.id = id;
	}
	
	public static Editor byID(Driver driver, String id) {
		return new Editor(driver, id);
	}
	
	private WebElement editor() {
		return driver.findElement(By.id(id));
	}
	
	private WebElement area() {
		return editor().findElement(By.className("inputarea"));
	}
	
	public void clear() {
		WebElement area = area();
		area.sendKeys(Keys.CONTROL + "a");
		area.sendKeys(Keys.DELETE);
	}
	
	public void write(String s) {
		clear();
		area().sendKeys(s);
	}
	
	public String read() {
		List<WebElement> lines = editor().findElements(By.cssSelector(".view-line > span"));
		String text = lines.stream().map(line -> 
				line.findElements(By.cssSelector("span"))
					.stream()
					.map(WebElement::getText)
					.collect(Collectors.joining()))
			.collect(Collectors.joining("\n"));
		return text;
	}
	
}
