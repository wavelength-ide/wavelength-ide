package edu.kit.wavelength.client.view.export;

import java.util.Arrays;
import java.util.List;

/**
 * Static class giving access to all available export formats.
 */
public final class Exports {

	/**
	 * Returns an unmodifiable list of all available export formats.
	 * 
	 * @return An unmodifiable list that contains exactly one instance of every
	 *         export format
	 */
	public static List<Export> all() {
		return Arrays.asList(new PlaintextExport(), new UnicodeExport(), new LatexExport(), new HaskellExport(),
				new LispExport());
	}
}
