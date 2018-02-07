package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.model.Category;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link CategoryDao} interface.
 *
 * @author Shaheer
 */
public class JdbcCategoryDao extends GenericJDBCDao<Category, Long> implements CategoryDao {
	
	@Override
	protected String getTableName() {
		return "category";
	}

	@Override
	protected String getTableColumns() {
		return "name";
	}

	@Override
	protected Object[] getEntityParameterValues(Category entity) {
		return new Object[] { entity.getName() };
	}

	@Override
	protected Category getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new Category(resultSet.getString("name"));
	}

}
