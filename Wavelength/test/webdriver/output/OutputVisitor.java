package webdriver.output;

public interface OutputVisitor<T> {
	
	T visitContainer(Container s);
	
	T visitURL(URL u);
	
	T visitText(Text t);
	
}
