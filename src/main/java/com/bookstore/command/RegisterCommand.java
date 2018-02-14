package com.bookstore.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.UserDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.dao.impl.JdbcUserDao;
import com.bookstore.model.User;
import com.bookstore.util.Constants;

public class RegisterCommand implements Command {

	private HashMap<String, String> validationErrors = new HashMap<>();

	private static final String EMAIL_PARAM = "email";

	private static final String PASS_PARAM = "password";

	private static final String CONFIRM_PASS_PARAM = "confirmPassword";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if ("POST".equals(request.getMethod())) {
			User customer = prepareUserModel(request);
			// Validate input
			if (isValid(customer.getEmail(), customer.getPassword(), request.getParameter(CONFIRM_PASS_PARAM))) {
				// save
				UserDao userDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getUserDao();
				userDao.save(customer);
				// success, redirect to login page
				return "redirect:home.do?action=Signin";
			}
			request.setAttribute("validationErrors", validationErrors);
		}
		return Constants.JSP_ROOT + "register";
	}
	
	private User prepareUserModel(HttpServletRequest request) {
		User customer = new User();
		customer.setEmail(request.getParameter(EMAIL_PARAM));
		customer.setPassword(request.getParameter(PASS_PARAM));
		return customer;
	}

	/*
	 * Validates data entered by user into registration form
	 * 
	 * @return true if input is valid, false otherwise
	 */
	public boolean isValid(String email, String password, String confirmPass) {
		validationErrors.clear();
		boolean valid = true;
		if ((email == null) || (email.length() == 0)) {
			addFieldError(EMAIL_PARAM, "E-Mail Address is required.");
			valid = false;
		} else if (email.indexOf('@') == -1) {
			addFieldError(EMAIL_PARAM, "Please supply a valid address.");
			valid = false;
		} else {
			// Check if email exists.
			UserDao userDao = new JdbcUserDao();
			if (userDao.existEmail(email)) {
				addFieldError(EMAIL_PARAM, "This email address already exist.");
				valid = false;
			}
		}
		if ((password == null) || (password.length() == 0)) {
			addFieldError(PASS_PARAM, "Password is required.");
			valid = false;
		}
		if ((confirmPass == null) || (confirmPass.length() == 0)) {
			addFieldError(CONFIRM_PASS_PARAM, "Confirm Password is required.");
			valid = false;
		} else {
			if (!confirmPass.equals(password)) {
				addFieldError(CONFIRM_PASS_PARAM, "Confirm Password should match with the Password.");
				valid = false;
			}
		}
		return valid;
	}

	private void addFieldError(String fieldname, String error) {
		validationErrors.put(fieldname, error);
	}

}