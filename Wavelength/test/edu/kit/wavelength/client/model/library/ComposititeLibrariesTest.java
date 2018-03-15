package edu.kit.wavelength.client.model.library;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.kit.wavelength.client.model.term.parsing.ParseException;
import edu.kit.wavelength.client.model.term.parsing.Parser;

public class ComposititeLibrariesTest {

	@Test
	public void factorial() throws ParseException {
		String factorialProgram = "isZero = \\n.n (\\x.false) true\nY (\\f.\\x.(ifThenElse (isZero x) 1 (times x (f (pred x)))))";

		List<Library> libraries = Arrays.asList(new Boolean(), new YCombinator(), new NaturalNumbers(true));
		int j = 1;
		for (int i = 0; i < 6; i++) {
			j *= i > 0 ? i : 1;
			LibraryTestUltilities.assertRepresents(j,
					LibraryTestUltilities.reduce(factorialProgram + " " + i, libraries));
		}

		libraries = Arrays.asList(new Boolean(), new YCombinator(), new NaturalNumbers(false));
		j = 1;
		for (int i = 0; i < 5; i++) {
			j *= i > 0 ? i : 1;
			LibraryTestUltilities.assertRepresents(j,
					LibraryTestUltilities.reduce(factorialProgram + " " + i, libraries));
		}
	}

	@Test
	public void isPrime() throws ParseException {
		String isPrimeProgram = "isZero = \\n. n (\\p. false) (true)\nEQ = \\eq. \\n. \\m. isZero n (isZero m) (isZero m false (eq (pred n) (pred m)))\neq = Y EQ\nless = \\a. \\b. (isZero (minus a b)) (isZero (minus b a) false true) false\ndivides = Y (\\divides. \\a. \\b. isZero b true ((less b a) false (divides a (minus b a))))\nisPrimeUpTo = Y (\\isPrimeUpTo. \\n. \\acc. isZero (pred acc) true (divides acc n false (isPrimeUpTo n (pred acc))))\nisPrime = \\n. isZero (pred n) false (isPrimeUpTo n (pred n))\nisPrime";

		List<Library> libraries = Arrays.asList(new Boolean(), new YCombinator(), new NaturalNumbers(true));
		for (int i = 0; i < 15; i++) {
			if (isPrime(i)) {
				assertEquals(LibraryTestUltilities.reduce(isPrimeProgram + " " + i, libraries),
						new Parser(libraries).parse("true"));
			} else {
				assertEquals(LibraryTestUltilities.reduce(isPrimeProgram + " " + i, libraries),
						new Parser(libraries).parse("false"));
			}
		}
	}

	private static boolean isPrime(int num) {
		if (num < 2)
			return false;
		if (num == 2)
			return true;
		if (num % 2 == 0)
			return false;
		for (int i = 3; i * i <= num; i += 2)
			if (num % i == 0)
				return false;
		return true;
	}
}
