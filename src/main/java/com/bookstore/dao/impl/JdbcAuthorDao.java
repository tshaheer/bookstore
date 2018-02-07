package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.model.Author;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link AuthorDao} interface.
 *
 * @author Shaheer
 */
public class JdbcAuthorDao extends GenericJDBCDao<Author, Long> implements AuthorDao {
	
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
