package wavelength.client.view;

import com.google.gwt.user.client.ui.TextArea;

public class TestAction {
	
	private static int counter = 0;
	
	
	public static void run(TextArea input) {
		counter++;
		String output = "";
		for (int i = 0; i < counter; i++) {
			output += "test";
		}
		input.setText(output);
	}

}
