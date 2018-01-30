package com.bookstore.exception;

public class CommandCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a CommandCreationException with the given detail message.
	 * 
	 * @param message
	 *            The detail message of the CommandCreationException.
	 */
	public CommandCreationException(String message) {
		super(message);
	}

	/**
	 * Constructs a CommandCreationException with the given root cause.
	 * 
	 * @param cause
	 *            The root cause of the CommandCreationException.
	 */
	public CommandCreationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a CommandCreationException with the given detail message and root
	 * cause.
	 * 
	 * @param message
	 *            The detail message of the CommandCreationException.
	 * @param cause
	 *            The root cause of the CommandCreationException.
	 */
	public CommandCreationException(String message, Throwable cause) {
		super(message, cause);
	}

}
