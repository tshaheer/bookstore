package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Commands are the intermediary between requests and views.
 * 
 * @author Shaheer
 */
public interface Command {
	
	String execute(HttpServletRequest request, HttpServletResponse response);

}
