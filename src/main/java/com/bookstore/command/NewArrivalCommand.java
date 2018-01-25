package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.util.Constants;

public class NewArrivalCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return Constants.JSP_ROOT + "new-arrival";
	}
	
}