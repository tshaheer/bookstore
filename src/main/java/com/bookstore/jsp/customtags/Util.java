package com.bookstore.jsp.customtags;

import static java.time.format.FormatStyle.FULL;
import static java.time.format.FormatStyle.LONG;
import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <p>
 * Utilities in support of tag-handler classes.
 * </p>
 * 
 * @author Shaheer
 */
public final class Util {
	
	private Util() {}

	private static final String REQUEST = "request";

	private static final String SESSION = "session";

	private static final String APPLICATION = "application";

	/**
	 * Converts the given string description of a scope to the corresponding
	 * PageContext constant.
	 * 
	 * The validity of the given scope has already been checked by the appropriate
	 * TLV.
	 * 
	 * @param scope
	 *            String description of scope
	 * 
	 * @return PageContext constant corresponding to given scope description
	 */
	public static int getScope(String scope) {
		int ret = PageContext.PAGE_SCOPE; // default

		if (REQUEST.equalsIgnoreCase(scope)) {
			ret = PageContext.REQUEST_SCOPE;
		} else if (SESSION.equalsIgnoreCase(scope)) {
			ret = PageContext.SESSION_SCOPE;
		} else if (APPLICATION.equalsIgnoreCase(scope)) {
			ret = PageContext.APPLICATION_SCOPE;
		}
		return ret;
	}

	/**
	 * Creates a formatter from a two character style pattern. The first character
	 * is the date style, and the second character is the time style. Specify a
	 * character of 'S' for short style, 'M' for medium, 'L' for long, and 'F' for
	 * full. A date or time may be ommitted by specifying a style character '-'.
	 *
	 * @param style
	 *            two characters from the set {"S", "M", "L", "F", "-"}
	 * @throws JspException
	 *             if the style is invalid
	 * @return a formatter for the specified style
	 */
	public static DateTimeFormatter createFormatterForStyle(String style) throws JspException {
		if (style == null || style.length() != 2) {
			throw new JspException("Invalid style specification: " + style);
		}
		FormatStyle dateStyle = selectStyle(style.charAt(0));
		FormatStyle timeStyle = selectStyle(style.charAt(1));
		if (dateStyle == null && timeStyle == null) {
			throw new JspException("Style '--' is invalid");
		}
		return createFormatterForStyleIndex(dateStyle, timeStyle);
	}

	/**
	 * Gets the formatter for the specified style.
	 *
	 * @param dateStyle
	 *            the date style
	 * @param timeStyle
	 *            the time style
	 * @return the formatter
	 */
	private static DateTimeFormatter createFormatterForStyleIndex(FormatStyle dateStyle, FormatStyle timeStyle)
			throws JspException {
		if (dateStyle == null && timeStyle == null)
			throw new JspException("Both styles cannot be null.");
		else if (dateStyle != null && timeStyle != null)
			return DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle);
		else if (dateStyle == null)
			return DateTimeFormatter.ofLocalizedTime(timeStyle);
		else
			return DateTimeFormatter.ofLocalizedDate(dateStyle);
	}

	/**
	 * Gets the FormatStyle style code from first character.
	 *
	 * @param ch
	 *            the one character style code
	 * @return the FormatStyle
	 */
	private static FormatStyle selectStyle(char ch) throws JspException {
		switch (ch) {
		case 'S':
			return SHORT;
		case 'M':
			return MEDIUM;
		case 'L':
			return LONG;
		case 'F':
			return FULL;
		case '-':
			return null;
		default:
			throw new JspException("Invalid style character: " + ch);
		}
	}
}
