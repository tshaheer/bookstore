package com.bookstore.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provide useful methods for working with cookies.
 * 
 * @author Shaheer
 */
public final class CookieUtil {

	private CookieUtil() {
	}

	/**
	 * Retrieves cookie value based on its name from the provided list
	 *
	 * @param cookies
	 *            The list of cookies
	 * @param name
	 *            Name of the cookie to be found
	 * @return Either cookie value, or null if it does not exist or cookies are null
	 */
	public static String getCookieValue(Cookie[] cookies, String name) {
		String result = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					result = c.getValue();
					break;
				}
			}
		}
		return result;
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}

}
