package com.bookstore.util;

/**
 * @author Shaheer
 */
public final class BookStoreUtil {

	private BookStoreUtil() {
		// Utility class, hide constructor.
	}

	public static String getDigitsOnly(String s) {
		StringBuilder digitsOnly = new StringBuilder();
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (Character.isDigit(c)) {
				digitsOnly.append(c);
			}
		}
		return digitsOnly.toString();
	}

}
