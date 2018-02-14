package com.bookstore.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

/**
 * A handy superclass for any type of filter.
 *
 * <p>
 * This filter leaves actual filtering to subclasses, which have to implement
 * the {@link javax.servlet.Filter#doFilter} method.
 *
 * @author Shaheer
 * 
 */
public abstract class BaseFilter implements Filter {

	private static final Logger logger = Logger.getLogger(BaseFilter.class);

	private FilterConfig filterConfig;

	private ServletContext servletContext;

	/**
	 * Standard way of initializing this filter. Map config parameters onto the
	 * properties of this filter, and invoke subclass initialization.
	 * 
	 * @param filterConfig
	 *            the configuration for this filter
	 * @throws ServletException
	 *             if bean properties are invalid (or required properties are
	 *             missing), or if subclass initialization fails.
	 * @see #initFilterBean
	 */
	@Override
	public final void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Initializing filter '" + filterConfig.getFilterName() + "'");
		this.filterConfig = filterConfig;
		this.filterConfig.getServletContext();
		// Let subclasses do whatever initialization they like.
		initFilterBean();
		logger.debug("Filter '" + filterConfig.getFilterName() + "' configured successfully");
	}

	/**
	 * Make the FilterConfig of this filter available, if any. Analogous to
	 * GenericServlet's {@code getServletConfig()}.
	 * <p>
	 * 
	 * @return the FilterConfig instance, or {@code null} if none available
	 * @see javax.servlet.GenericServlet#getServletConfig()
	 */
	public final FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	/**
	 * Make the ServletContext of this filter available to subclasses. Analogous to
	 * GenericServlet's {@code getServletContext()}.
	 * <p>
	 * 
	 * @return the ServletContext instance, or {@code null} if none available
	 * @see javax.servlet.GenericServlet#getServletContext()
	 * @see javax.servlet.FilterConfig#getServletContext()
	 * @see #setServletContext
	 */
	protected final ServletContext getServletContext() {
		return this.servletContext;
	}

	/**
	 * Subclasses may override this to perform custom initialization. This default
	 * implementation is empty.
	 * 
	 * @throws ServletException
	 *             if subclass initialization fails
	 * @see #getFilterName()
	 * @see #getServletContext()
	 */
	protected void initFilterBean() throws ServletException {
	}

	/**
	 * Subclasses may override this to perform custom filter shutdown.
	 * <p>
	 * Note: This method will be called from standard filter destruction.
	 * <p>
	 * This default implementation is empty.
	 */
	@Override
	public void destroy() {
	}

}
