package webdriver.output;

import java.util.List;

public class Container implements OutputNode {
	
	public final List<String> classes;
	public final List<OutputNode> children;
	
	public Container(List<String> classes, List<OutputNode> children) {
		this.classes = classes;
		this.children = children;
	}

	@Override
	public <T> T acceptVisitor(OutputVisitor<T> v) {
		return v.visitContainer(this);
	}
	
}
