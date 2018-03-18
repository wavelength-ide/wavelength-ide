package webdriver.output;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import webdriver.driver.Driver;

public class UnicodeOutput {
	
	private final Driver driver;
	private final String id;
	
	private UnicodeOutput(Driver driver, String id) {
		this.driver = driver;
		this.id = id;
	}
	
	public static UnicodeOutput byID(Driver driver, String id) {
		return new UnicodeOutput(driver, id);
	}
	
	private WebElement parent() {
		return driver.findElement(By.cssSelector("#" + id + " > div"));
	}
	
	private List<WebElement> outputs() {
		return driver.findElements(By.cssSelector("#" + id + " > div > span"));
	}
	
	private OutputNode readElement(WebElement current) {
		List<String> classes = Arrays.asList(current.getAttribute("class").split(" "));
		if (classes.contains("outputText") || classes.contains("errorText")) {
			return new Text(current);
		}
		if (classes.contains("abstraction")) {
			return new URL(driver, current);
		}
		List<WebElement> children = current.findElements(By.xpath("child::node()"));
		List<OutputNode> childNodes = children.stream().map(this::readElement).collect(Collectors.toList());
		return new Container(current, childNodes);
	}
	
	private List<OutputNode> readLines() {
		return outputs().stream().map(this::readElement).collect(Collectors.toList());
	}
	
	public List<OutputNode> readStructure() {
		return readLines();
	}
	
	public String readText() {
		return outputs().stream().map(WebElement::getText).collect(Collectors.joining("\n"));
	}
	
	public String readHTML() {
		return outputs().stream().map(o -> o.getAttribute("outerHTML").replaceAll("\"", "'"))
				.collect(Collectors.joining("\n"));
	}
	
	public OutputNode find(String xpath) {
		return readElement(parent().findElement(By.xpath(xpath)));
	}
	
	public boolean isEmpty() {
		driver.removeImplicitTimeout();
		boolean isEmpty = outputs().size() == 0;
		driver.resetImplicitTimeout();
		return isEmpty;
	}
	
}
