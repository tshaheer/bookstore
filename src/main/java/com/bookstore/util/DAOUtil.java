package com.bookstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is
 * been re-factored in single static methods. As far it contains a
 * PreparedStatement values setter and a <code>java.time.LocalDate</code> to
 * <code>java.sql.Date</code> converter.
 *
 * @author Shaheer
 */
public final class DAOUtil {

	private DAOUtil() {
		// Utility class, hide constructor.
	}

	/**
	 * Returns a PreparedStatement of the given connection, set with the given SQL
	 * query and the given parameter values.
	 * 
	 * @param connection
	 *            The Connection to create the PreparedStatement from.
	 * @param sql
	 *            The SQL query to construct the PreparedStatement with.
	 * @param returnGeneratedKeys
	 *            Set whether to return generated keys or not.
	 * @param values
	 *            The parameter values to be set in the created PreparedStatement.
	 * @throws SQLException
	 *             If something fails during creating the PreparedStatement.
	 */
	public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys,
			Object... values) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		setValues(statement, values);
		return statement;
	}

	/**
	 * Set the given parameter values in the given PreparedStatement.
	 * 
	 * @param connection
	 *            The PreparedStatement to set the given parameter values in.
	 * @param values
	 *            The parameter values to be set in the created PreparedStatement.
	 * @throws SQLException
	 *             If something fails during setting the PreparedStatement values.
	 */
	public static void setValues(PreparedStatement statement, Object... values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			statement.setObject(i + 1, values[i]);
		}
	}

	/**
	 * Converts the given java.time.LocalDate to java.sql.Date.
	 * 
	 * @param localDate
	 *            The java.time.LocalDate to be converted to java.sql.Date.
	 * @return The converted java.sql.Date.
	 */
	public static java.sql.Date toSqlDate(java.time.LocalDate localDate) {
		return (localDate != null) ? java.sql.Date.valueOf(localDate) : null;
	}

	/**
	 * Converts the given java.sql.Date to java.time.LocalDate.
	 * 
	 * @param date
	 *            The java.sql.Date to be converted to java.time.LocalDate.
	 * @return The converted java.time.LocalDate.
	 */
	public static java.time.LocalDate toLocalDate(java.sql.Date date) {
		return (date != null) ? date.toLocalDate() : null;
	}

	public static long generateUUID() {
		UUID uuidOne = UUID.randomUUID();
		long keyValues = uuidOne.getMostSignificantBits();
		return (keyValues < 0 ? -keyValues : keyValues); // remove negative if any
	}

}
