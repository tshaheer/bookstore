package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.Author;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the {@link AuthorDao}
 * interface.
 *
 * @author Shaheer
 */
public class JdbcAuthorDao extends GenericJDBCDao<Author> implements AuthorDao {

	private static final Logger logger = Logger.getLogger(JdbcAuthorDao.class);
	
	@Override
	public List<Author> findAuthorsByProductId(Long id) {
		String sql = "SELECT " + getTableColumns() + " FROM " + getTableName()
				+ " INNER JOIN product_author_xref on author.id = product_author_xref.author_id AND product_author_xref.product_id = ?";
		logger.debug(sql);
		List<Author> authors = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, id);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				authors.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return authors;
	}

	@Override
	protected String getTableName() {
		return "author";
	}

	@Override
	protected String getTableColumns() {
		return "name";
	}

	@Override
	protected Object[] getEntityParameterValues(Author entity) {
		return new Object[] { entity.getName() };
	}

	@Override
	protected Author getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new Author(resultSet.getString("name"));
	}

}
