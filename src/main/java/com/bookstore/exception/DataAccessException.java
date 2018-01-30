package com.bookstore.exception;

/**
 * This class represents a generic Data Access exception. It should wrap any exception
 * of the underlying code, such as SQLExceptions.
 *
 * @author Shaheer
 */
public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a DAOException with the given detail message.
	 * 
	 * @param message
	 *            The detail message of the DAOException.
	 */
	public DataAccessException(String message) {
		super(message);
	}

	/**
	 * Constructs a DAOException with the given root cause.
	 * 
	 * @param cause
	 *            The root cause of the DAOException.
	 */
	public DataAccessException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a DAOException with the given detail message and root cause.
	 * 
	 * @param message
	 *            The detail message of the DAOException.
	 * @param cause
	 *            The root cause of the DAOException.
	 */
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
