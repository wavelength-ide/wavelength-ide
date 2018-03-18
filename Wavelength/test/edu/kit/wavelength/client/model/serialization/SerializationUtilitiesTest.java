package edu.kit.wavelength.client.model.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class SerializationUtilitiesTest {

	@Test
	public void extract1Test() {
		String input = "12ab210abcdefghij";

		List<String> ans = SerializationUtilities.extract(input);

		assertNotNull(ans);
		assertEquals(2, ans.size());
		assertEquals("ab", ans.get(0));
		assertEquals("abcdefghij", ans.get(1));
	}

	@Test
	public void extract2Test() {
		String input = "11a1011b";

		List<String> ans = SerializationUtilities.extract(input);

		assertNotNull(ans);
		assertEquals(3, ans.size());
		assertEquals("a", ans.get(0));
		assertEquals("", ans.get(1));
		assertEquals("b", ans.get(2));
	}

	@Test
	public void extract3Test() {
		String input = "1010";

		List<String> ans = SerializationUtilities.extract(input);

		assertNotNull(ans);
		assertEquals(2, ans.size());
		assertEquals("", ans.get(0));
		assertEquals("", ans.get(1));
	}

	@Test
	public void enclose1Test() {
		String ans = SerializationUtilities.enclose(new StringBuilder("ab"), new StringBuilder("abcdefghij"))
				.toString();

		assertEquals(ans, "12ab210abcdefghij");
	}

	@Test
	public void enclose2Test() {
		String ans = SerializationUtilities
				.enclose(new StringBuilder("a"), new StringBuilder(""), new StringBuilder("b")).toString();

		assertEquals(ans, "11a1011b");
	}

	@Test(expected = IllegalArgumentException.class)
	public void noIndirectLength1() {
		SerializationUtilities.extract("a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noIndirectLength2() {
		SerializationUtilities.extract("11ab");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noDirectLength1() {
		SerializationUtilities.extract("1a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noDirectLength2() {
		SerializationUtilities.extract("11a1a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noDirectLength3() {
		SerializationUtilities.extract("0a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noDirectLength4() {
		SerializationUtilities.extract("01a");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noDirectLength5() {
		SerializationUtilities.extract("5");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noContent1() {
		SerializationUtilities.extract("15abcd");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noContent2() {
		SerializationUtilities.extract("15");
	}

}
