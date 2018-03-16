package webdriver.output;

import java.util.List;

public class Text implements OutputNode {

	public final String content;
	public final List<String> classes;
	
	public Text(String content, List<String> classes) {
		this.content = content;
		this.classes = classes;
	}
	
	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitText(this);
	}
	
}
