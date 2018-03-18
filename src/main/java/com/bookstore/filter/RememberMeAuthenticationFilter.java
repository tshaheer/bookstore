package com.bookstore.filter;

import java.io.IOException;
import java.util.Arrays;
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

/**
 * The UserSession filter.
 * @author Shaheer
 */
public class RememberMeAuthenticationFilter extends BaseFilter {

	private UserDao userDao;

	@Override
	protected void initFilterBean() throws ServletException {
		this.userDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getUserDao();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		boolean loggedIn = tryLogin(request) || tryAutoLogin(request, response);
		if (!loggedIn && isRestrictedPath(request)) {
			response.sendRedirect("home.do?action=Signin");
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean tryLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute(Constants.ATTR_USER) != null;
	}

	private boolean tryAutoLogin(HttpServletRequest request, HttpServletResponse response) {
		String encodedAuthDetails = CookieUtil.getCookieValue(request.getCookies(), Constants.REMEMBER_COOKIE_NAME);
		boolean autoLogin = false;
		if (encodedAuthDetails != null) {
			String decodedString = new String(Base64.getDecoder().decode(encodedAuthDetails));
			String[] authDetails = decodedString.split(":");
			User user = userDao.findByEmailAndPassword(authDetails[0], authDetails[1]);
			if (user != null) {
				request.getSession().setAttribute(Constants.ATTR_USER, user);
				CookieUtil.addCookie(response, Constants.REMEMBER_COOKIE_NAME, encodedAuthDetails, 60 * 60 * 24 * 365);
				autoLogin = true;
			} else {
				CookieUtil.removeCookie(response, Constants.REMEMBER_COOKIE_NAME);
			}
		}
		return autoLogin;
	}

	private boolean isRestrictedPath(HttpServletRequest request) {
		return Arrays.asList("Account", "Address", "CreditCard", "Checkout", "OrderHistory").contains(request.getParameter(Constants.ACTION_PARAM));
	}

}
