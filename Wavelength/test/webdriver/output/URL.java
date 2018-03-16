package webdriver.output;

import java.util.List;

import org.openqa.selenium.WebElement;

public class URL implements OutputNode {

	private final WebElement element;
	public final String content;
	public final List<String> classes;
	
	public URL(WebElement element, String content, List<String> classes) {
		this.element = element;
		this.content = content;
		this.classes = classes;
	}

	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitURL(this);
	}
	
	public void click() {
		element.click();
	}
	
}
