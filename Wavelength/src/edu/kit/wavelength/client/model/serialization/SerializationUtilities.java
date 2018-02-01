package edu.kit.wavelength.client.model.serialization;

import java.util.ArrayList;
import java.util.List;

public class SerializationUtilities {
	private SerializationUtilities() {
		
	}
	
	public static List<String> extract(String input) {
		ArrayList<String> result = new ArrayList<>();
		
		int currentIndex = 0;
		
		while (currentIndex < input.length()) {
			int count;
			try {
				count = Integer.valueOf(input.substring(currentIndex, currentIndex + 1));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Input does not have the correct format");
			}
			
			++currentIndex;
			
			if (currentIndex + count >= input.length())
				throw new IllegalArgumentException("Input does not have the correct format");
			
			int actualLength;
			
			try {
				actualLength = Integer.valueOf(input.substring(currentIndex, currentIndex + count));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Input does not have the correct format");
			}
			
			currentIndex += count;
			
			if (currentIndex + actualLength > input.length())
				throw new IllegalArgumentException("Input does not have the correct format");
			
			result.add(input.substring(currentIndex, currentIndex + actualLength));
			
			currentIndex += actualLength;
		}
		
		return result;
	}
	
	public static StringBuilder enclose(StringBuilder... content) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < content.length; ++i) {
			String lens = String.valueOf(content[i].length());
			result.append(String.valueOf(lens.length()));
			result.append(lens);
			result.append(content[i]);
		}
		return result;
	}
}
