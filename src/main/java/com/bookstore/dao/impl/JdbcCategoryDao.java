package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.Category;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link CategoryDao} interface.
 *
 * @author Shaheer
 */
public class JdbcCategoryDao extends GenericJDBCDao<Category> implements CategoryDao {

	private static final Logger logger = Logger.getLogger(JdbcCategoryDao.class);

	@Override
	public List<Category> findCategoriesByProductId(Long id) {
		String sql = "SELECT " + getTableColumns() + " FROM " + getTableName()
				+ " INNER JOIN category_product_xref on category.id = category_product_xref.category_id AND category_product_xref.product_id = ?";
		logger.debug(sql);
		List<Category> categories = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, id);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				categories.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return categories;
	}

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
