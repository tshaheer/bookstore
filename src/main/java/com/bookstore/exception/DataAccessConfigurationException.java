package com.bookstore.exception;

/**
 * This class represents an exception in the DAO configuration which cannot be
 * resolved at runtime, such as a missing resource in the classpath, a missing
 * property in the xml file, etcetera.
 *
 * @author Shaheer
 */
public class DataAccessConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a DataAccessConfigurationException with the given detail message.
	 * 
	 * @param message
	 *            The detail message of the DataAccessConfigurationException.
	 */
	public DataAccessConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructs a DataAccessConfigurationException with the given root cause.
	 * 
	 * @param cause
	 *            The root cause of the DataAccessConfigurationException.
	 */
	public DataAccessConfigurationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a DataAccessConfigurationException with the given detail message and root
	 * cause.
	 * 
	 * @param message
	 *            The detail message of the DataAccessConfigurationException.
	 * @param cause
	 *            The root cause of the DataAccessConfigurationException.
	 */
	public DataAccessConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
