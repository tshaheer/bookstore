package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.util.Constants;

public class OrderCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if(request.getSession().getAttribute(Constants.ATTR_ADDRESS) == null) {
			//redirect to cart page
			return "redirect:home.do?action=Cart";
		}
		return Constants.JSP_ROOT + "order";
	}

}
