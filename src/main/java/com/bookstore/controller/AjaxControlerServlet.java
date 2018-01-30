package com.bookstore.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bookstore.command.Command;
import com.bookstore.command.UnknownCommand;
import com.bookstore.exception.CommandCreationException;
import com.bookstore.util.Constants;

/**
 * 
 * AjaxControlerServlet is the entry point for only AJAX requests and write
 * correct AJAX response.
 *
 * @author Shaheer
 */
public class AjaxControlerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FrontControllerServlet.class);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("DispatcherServlet with name '" + getServletName() + "'" + " started processing "
				+ request.getMethod() + " request for [" + request.getRequestURI() + "]");
		response.setContentType(Constants.CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		Command command = getCommand(request);
		String strAjaxResponseText = command.execute(request, response);
		out.println(strAjaxResponseText);
		out.close();
	}

	private Command getCommand(HttpServletRequest request) {
		String action = request.getParameter(Constants.ACTION_PARAM);
		Class<?> commandClass = getCommandClass(action);
		try {
			return (Command) commandClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage());
			throw new CommandCreationException("Command class '" + commandClass + "' cannot be instantiated. Verify the request parameter", e);
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
