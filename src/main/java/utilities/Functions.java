
package utilities;

public class Functions {

	private Functions() {
		//
	}

	public static String replaceAll(final String string, final String pattern, final String replacement) {
		final String res;
		String realPattern = null;
		if (pattern.equals("emailAndPhone"))
			realPattern = "([a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+)|(?:\\+?\\d{2}\\s*)\\s*([0-9](\\s|-)*){9,20}";

		res = string.replaceAll(realPattern, replacement);
		return res;
	}
}
