package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.ProductDao;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.Product;
import com.bookstore.util.Constants;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link ProductDao} interface.
 *
 * @author Shaheer
 */
public class JdbcProductDao extends GenericJDBCDao<Product> implements ProductDao {

	private static final Logger logger = Logger.getLogger(JdbcProductDao.class);
	
	@Override
	public Product findOneByISBN(String isbn) {
		return this.findByConditionUnique("isbn = '"+ isbn +"'");
	}
	
	@Override
	public List<Product> findProductsByCategoryName(String name) {
		String sql = "SELECT product.id," + getTableColumns() + " from " + getTableName()
				+ " INNER JOIN category_product_xref on product.id = category_product_xref.product_id INNER JOIN category on category.id = category_product_xref.category_id AND category.name = ?";
		logger.debug(sql);
		List<Product> books = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, name);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				books.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return books;
	}

	@Override
	public List<Product> selectNewestProducts() {
		String sql = getSelectQuery() + " ORDER BY pub_date LIMIT 4";
		logger.debug(sql);
		List<Product> books = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				books.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return books;
	}

	@Override
	protected String getTableName() {
		return "product";
	}

	@Override
	protected String getTableColumns() {
		return "isbn, title, price, pub_date, description, image_url";
	}

	@Override
	protected Object[] getEntityParameterValues(Product entity) {
		Object[] values;
		if (entity.getId() == null) {
			// save
			// generate ISBN
			final String newIsbn = UUID.randomUUID().toString().replace("-", "");
			entity.setIsbn(newIsbn);
			if (entity.getImageUrl() == null) {
				entity.setImageUrl(Constants.DEFAULT_IMAGEURL);
			}
			values = new Object[] { entity.getIsbn(), entity.getTitle(), entity.getPrice(),
					DAOUtil.toSqlDate(entity.getPubDate()), entity.getDescription(), entity.getImageUrl() };
		} else {
			// update
			values = new Object[] { entity.getIsbn(), entity.getTitle(), entity.getPrice(),
					DAOUtil.toSqlDate(entity.getPubDate()), entity.getDescription(), entity.getImageUrl(),
					entity.getId() };
		}
		return values;
	}

	@Override
	protected Product getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		Product book = new Product();
		book.setId(resultSet.getLong("id"));
		book.setIsbn(resultSet.getString("isbn"));
		book.setTitle(resultSet.getString("title"));
		book.setPrice(resultSet.getFloat("price"));
		book.setPubDate(DAOUtil.toLocalDate(resultSet.getDate("pub_date")));
		book.setDescription(resultSet.getString("description"));
		book.setImageUrl(resultSet.getString("image_url"));
		return book;
	}

}
