package com.bookstore.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.bookstore.exception.DataAccessConfigurationException;
import com.bookstore.exception.DataAccessException;

/**
 * This class can be used to get an instance of the JNDI Datasource, which
 * enables retrieving data using JDBC.
 * 
 * @author Shaheer
 */
public class DBManager {
	private static final Logger logger = Logger.getLogger(DBManager.class);

	private static DataSource dataSource = null;
	private static Connection connection = null;
	
	private DBManager() {}

	public static Connection getDBConnection() {
		if (dataSource == null) {
			lookUpDataSource();
		}
		try {
			if (connection == null || connection.isClosed()) {
				connection = dataSource.getConnection();
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw new DataAccessConfigurationException("Unable to connect to the database ", sqle);
		}
		return connection;
	}

	private static void lookUpDataSource() {
		try {
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup(Constants.BOOKSTORE_DATASOURCE);
		} catch (NamingException ne) {
			logger.error("Failed to look up data source. " + ne.getMessage());
			throw new DataAccessConfigurationException(
					"DataSource '" + Constants.BOOKSTORE_DATASOURCE + "' is missing in JNDI.", ne);
		}
	}

	public static void beginTransaction() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}
	
	public static void rollback() {
		try {
			connection.rollback();
		} catch (SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

	public static void commit() {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

	// Closes the result set and statement.
	public static void closeDBResouces(Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}
	}

}
