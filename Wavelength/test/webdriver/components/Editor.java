package webdriver.components;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.sun.javafx.PlatformUtil;

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
		String os = System.getProperty("os.name");
		if (os.startsWith("Mac")) {
			// for mac compat
			area.sendKeys(Keys.COMMAND + "a");
			area.sendKeys(Keys.DELETE);
		} else {
			area.sendKeys(Keys.CONTROL + "a");
			area.sendKeys(Keys.DELETE);
		}
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
	
	public void hover() {
		new Actions(driver).moveToElement(editor()).perform();
	}
	
	public boolean hasMarginErrorMarker(int line) {
		List<WebElement> overlays = editor().findElements(By.cssSelector(".margin-view-overlays > div"));
		if (overlays.size() <= line) {
			return false;
		}
		WebElement parent = overlays.get(line);
		return driver.parentHasElement(parent, By.className("editorErrorGlyphMargin"));
	}
	
	public boolean hasUnderlineErrorMarker(int line) {
		List<WebElement> overlays = editor().findElements(By.cssSelector(".view-overlays > div"));
		if (overlays.size() <= line) {
			return false;
		}
		WebElement parent = overlays.get(line);
		return driver.parentHasElement(parent, By.className("redsquiggly"));
	}
	
	public void hoverOverMarginErrorMarker(int line) {
		List<WebElement> overlays = editor().findElements(By.cssSelector(".margin-view-overlays > div"));
		WebElement marker = overlays.get(line).findElement(By.className("editorErrorGlyphMargin"));
		new Actions(driver).moveToElement(marker).perform();
	}
	
	public String readHoverError() {
		return editor().findElement(By.cssSelector("div.hover-row > div > p")).getText();
	}
	
}
