package com.bookstore.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bookstore.command.Command;
import com.bookstore.command.UnknownCommand;
import com.bookstore.util.Constants;

/**
 * 
 * FrontController is a centralized entry point for all the client requests and
 * renders the correct response.
 * 
 * @author Shaheer
 */
public class FrontControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FrontControllerServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("DispatcherServlet with name '" + getServletName() + "'" + " started processing "
				+ request.getMethod() + " request for [" + request.getRequestURI() + "]");
		response.setContentType(Constants.CONTENT_TYPE);
		Command command = getCommand(request);
		String view = command.execute(request, response);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(Constants.JSP_ROOT + "home.jsp?paramDynInclPage=" + view);
		dispatcher.forward(request, response);
	}

	private Command getCommand(HttpServletRequest request) {
		String action = request.getParameter(Constants.ACTION_PARAM);
		Class<?> commandClass = getCommandClass(action);
		try {
			return (Command) commandClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private static Class<?> getCommandClass(String commandAction) {
		Class<?> loadedClass;
		try {
			loadedClass = Class.forName(Constants.COMMAND_BASE_PACKAGE + commandAction + Constants.COMMAND);
		} catch (ClassNotFoundException e) {
			loadedClass = UnknownCommand.class;
		}
		return loadedClass;
	}

}
