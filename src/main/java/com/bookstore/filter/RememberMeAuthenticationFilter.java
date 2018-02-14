package com.bookstore.filter;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.User;
import com.bookstore.util.Constants;
import com.bookstore.util.CookieUtil;

public class RememberMeAuthenticationFilter extends BaseFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		boolean loggedIn = session != null && session.getAttribute(Constants.ATTR_USER) != null;
		if (!loggedIn) {
			tryAutoLogin(request, response);
		}
		chain.doFilter(request, response);
	}

	private void tryAutoLogin(HttpServletRequest request, HttpServletResponse response) {
		String encodedAuthDetails = CookieUtil.getCookieValue(request.getCookies(), Constants.REMEMBER_COOKIE_NAME);
		if (encodedAuthDetails != null) {
			String decodedString = new String(Base64.getDecoder().decode(encodedAuthDetails));
			String[] authDetails = decodedString.split(":");
			UserDao userDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getUserDao();
			User user = userDao.findByEmailAndPassword(authDetails[0], authDetails[1]);
			if (user != null) {
				request.getSession().setAttribute(Constants.ATTR_USER, user);
				CookieUtil.addCookie(response, Constants.REMEMBER_COOKIE_NAME, encodedAuthDetails, 3600 * 24 * 365);
			} else {
				CookieUtil.removeCookie(response, Constants.REMEMBER_COOKIE_NAME);
			}
		}
	}

}
