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

/**
 * This class can be used to get an instance of the JNDI Datasource, which
 * enables retrieving data using JDBC.
 * 
 * @author Shaheer
 */
public class DBManager {

	private static final Logger logger = Logger.getLogger(DBManager.class);

	private static DataSource dataSource = null;

	static {
		if (dataSource == null) {
			lookUpDataSource();
		}
	}

	private DBManager() {
	}

	public static Connection getDBConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
			throw new DataAccessConfigurationException("Unable to connect to the database ", sqle);
		}
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

	/**
	 * Close a <code>Connection</code>, avoid closing if null.
	 *
	 * @param conn
	 *            Connection to close.
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqle) {
				logger.error("Could not close JDBC Connection", sqle);
			}
		}
	}

	/**
	 * Close a <code>Connection</code>, <code>Statement</code> and
	 * <code>ResultSet</code>. Avoid closing if null and hide any SQLExceptions that
	 * occur.
	 *
	 * @param conn
	 *            Connection to close.
	 * @param stmt
	 *            Statement to close.
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			close(rs);
		} finally {
			try {
				close(stmt);
			} finally {
				close(conn);
			}
		}

	}

	/**
	 * Close a <code>Connection</code>, <code>Statement</code> and
	 * <code>ResultSet</code>. Avoid closing if null and hide any SQLExceptions that
	 * occur.
	 *
	 * @param stmt
	 *            Statement to close.
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void close(Statement stmt, ResultSet rs) {
		try {
			close(rs);
		} finally {
			close(stmt);
		}
	}

	/**
	 * Close the given JDBC Statement and ignore any thrown exception.
	 *
	 * @param stmt
	 *            the JDBC Statement to close.
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqle) {
				logger.error("Could not close JDBC Statement", sqle);
			}
		}
	}

	/**
	 * Close the given JDBC ResultSet and ignore any thrown exception.
	 *
	 * @param rs
	 *            the JDBC ResultSet to close.
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				logger.trace("Could not close JDBC ResultSet", ex);
			}
		}
	}

	/**
	 * Commits a <code>Connection</code> then closes it, avoid closing if null and
	 * hide any SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 */
	public static void commitAndClose(Connection conn) {
		try {
			if (conn != null) {
				try {
					conn.commit();
					conn.setAutoCommit(true);
				} finally {
					conn.close();
				}
			}
		} catch (SQLException sqle) {
			logger.error("Could not commit or close JDBC connection", sqle);
		}
	}

	/**
	 * Performs a rollback on the <code>Connection</code> then closes it, avoid
	 * closing if null and hide any SQLExceptions that occur.
	 */
	public static void rollbackAndClose(Connection conn) {
		try {
			if (conn != null) {
				try {
					conn.rollback();
				} finally {
					conn.close();
				}
			}
		} catch (SQLException sqle) {
			logger.error("Could not rollback or close JDBC connection", sqle);
		}
	}

}
