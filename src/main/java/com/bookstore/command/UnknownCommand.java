package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default command in case the mapping is not successful.
 * 
 * @author Shaheer
 */
public class UnknownCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "error_404";
	}
	
}