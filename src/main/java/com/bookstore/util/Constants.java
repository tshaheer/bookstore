package com.bookstore.util;

import java.util.Locale;

/**
 * Application Constants.
 * 
 * @author Shaheer
 */
public final class Constants {
	public static final String BOOKSTORE_DATASOURCE = "jdbc/bsDataSource";
	public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    public static final String ACTION_PARAM = "action";
	public static final String JSP_ROOT = "/WEB-INF/jsp/";
	public static final String COMMAND_BASE_PACKAGE = "com.bookstore.command.";
	public static final String COMMAND = "Command";
	public static final String DATE_FORMAT_FULL = "dd-MM-yyyy HH:mm:ss";
	public static final String DEFAULT_IMAGEURL = "default_book.jpg";
	public static final Locale DEFAULT_LOCALE = Locale.US;
	public static final String PARAM_PRODUCT_CODE = "isbn";
	public static final String PARAM_QUANTITY = "qty";
	public static final String ATTR_SHOPPING_CART = "cart";
	public static final String ATTR_USER = "customer";
	public static final String REMEMBER_COOKIE_NAME = "remember-me";
	public static final String ATTR_ADDRESS = "address";
	public static final String ATTR_CARD = "card";
	
	private Constants() {
	}
	
}
