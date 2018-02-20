package com.bookstore.util;

/**
 * @author Shaheer
 */
public final class CreditCardUtil {

	private CreditCardUtil() {
		// Utility class, hide constructor.
	}
	
	public static boolean validateCreditCardNumber(String cardNumber) {
		String digitsOnly = getDigitsOnly(cardNumber);
		int sum = 0;
		int digit = 0;
		int addend = 0;
		boolean timesTwo = false;
		if (!prefixValid(digitsOnly)) {
			return false;
		}
		for (int i = digitsOnly.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
			if (timesTwo) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			timesTwo = !timesTwo;
		}
		int modulus = sum % 10;
		return modulus == 0;
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
	
	private static boolean prefixValid(String digitsOnly) {
		boolean foundcard = false;
		int digitLength = digitsOnly.length();
		// MC
		if (digitsOnly.startsWith("51") || digitsOnly.startsWith("52") || digitsOnly.startsWith("53")
				|| digitsOnly.startsWith("54")) {
			if (digitLength != 16) {
				return false;
			}
			foundcard = true;
		}
		// VISA
		if (digitsOnly.startsWith("4")) {
			if ((digitLength != 16) && (digitLength != 13))
				return false;
			foundcard = true;
		}
		// AMEX
		if (digitsOnly.startsWith("34") || digitsOnly.startsWith("37")) {
			if (digitLength != 15) {
				return false;
			}
			foundcard = true;
		}
		// DISC
		if (digitsOnly.startsWith("6011")) {
			if (digitLength != 16) {
				return false;
			}
			foundcard = true;
		}
		return foundcard;
	}

}
