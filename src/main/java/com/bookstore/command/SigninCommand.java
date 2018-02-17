package com.bookstore.command;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.User;
import com.bookstore.util.Constants;
import com.bookstore.util.CookieUtil;

public class SigninCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if ("POST".equals(request.getMethod()) && authenticate(request, response)) {
			return "redirect:home.do?action=NewArrival";
		}
		if ("logout".equals(request.getParameter("op"))) {
			request.getSession().invalidate();
		}
		return Constants.JSP_ROOT + "login";
	}

	private boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
		boolean exist = false;
		UserDao userDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getUserDao();
		User user = userDao.findByEmailAndPassword(request.getParameter("email"), request.getParameter("password"));
		if (user != null) {
			exist = true;
			request.getSession().setAttribute(Constants.ATTR_USER, user);
			if (request.getParameter("remember") != null) {
				rememberUser(user, response);
			} else {
				forgetUser(response);
			}
		}
		request.setAttribute("message", "Invalid Username or password.");
		return exist;
	}

	private void rememberUser(User user, HttpServletResponse response) {
		String encodedAuthDetails = Base64.getEncoder().encodeToString((user.getEmail() + ":" + user.getPassword()).getBytes());
		CookieUtil.addCookie(response, Constants.REMEMBER_COOKIE_NAME, encodedAuthDetails, 60 * 60 * 24 * 365);
	}

	private void forgetUser(HttpServletResponse response) {
		CookieUtil.removeCookie(response, Constants.REMEMBER_COOKIE_NAME);
	}

}