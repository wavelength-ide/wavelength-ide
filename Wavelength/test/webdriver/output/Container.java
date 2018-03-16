package webdriver.output;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

public class Container implements OutputNode {
	
	private final WebElement element;
	public final List<OutputNode> children;
	
	public Container(WebElement element, List<OutputNode> children) {
		this.element = element;
		this.children = children;
	}
	
	public List<String> classes() {
		return Arrays.asList(element.getAttribute("class").split(" "));
	}
	
	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitContainer(this);
	}
	
}
