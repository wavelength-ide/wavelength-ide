package webdriver.output;

public interface OutputNode {

	public <T> T acceptVisitor(OutputVisitor<T> v);
	
}
