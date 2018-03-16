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
	
	private List<WebElement> outputs() {
		return driver.findElements(By.cssSelector("#" + id + " > div > span"));
	}
	
	private OutputNode readStructure(WebElement current) {
		List<String> classes = Arrays.asList(current.getAttribute("class").split(" "));
		if (classes.contains("outputText")) {
			String text = current.getText();
			// selenium removes the whitespace in our space leaf, so we add it back
			if (text.equals("")) {
				text = " ";
			}
			return new Text(text, classes);
		}
		if (classes.contains("abstraction")) {
			return new URL(current, current.getText(), classes);
		}
		List<WebElement> children = current.findElements(By.xpath("child::node()"));
		List<OutputNode> childNodes = children.stream().map(this::readStructure).collect(Collectors.toList());
		return new Container(classes, childNodes);
	}
	
	public List<OutputNode> readStructure() {
		return outputs().stream().map(this::readStructure).collect(Collectors.toList());
	}
	
	public String readText() {
		return outputs().stream().map(WebElement::getText).collect(Collectors.joining("\n"));
	}
	
	public boolean isEmpty() {
		driver.removeImplicitTimeout();
		boolean isEmpty = outputs().size() == 0;
		driver.resetImplicitTimeout();
		return isEmpty;
	}
	
}
