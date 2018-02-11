package com.bookstore.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Category;

/**
 * This class will execute code within the contextInitialized method whenever
 * the web application is launched.
 * 
 * @author Shaheer
 */
public class BookStoreContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(BookStoreContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Initializing application context");
		ServletContext sContext = event.getServletContext();
		// find all categories and set it as a context attribute
		CategoryDao categoryDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCategoryDao();
		List<Category> categories = (List<Category>) categoryDao.findAll();
		sContext.setAttribute("categories", categories);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// not necessary
	}

}
