package webdriver.output;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

public class Text implements OutputNode {

	private final WebElement element;
	
	public Text(WebElement element) {
		this.element = element;
	}
	
	public String content() {
		String text = element.getText();
		// selenium removes the whitespace in our space leaf, so we add it back
		if (text.equals("")) {
			text = " ";
		}
		return text;
	}
	
	public List<String> classes() {
		return Arrays.asList(element.getAttribute("class").split(" "));
	}
	
	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitText(this);
	}
	
}
