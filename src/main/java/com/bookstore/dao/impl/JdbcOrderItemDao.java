package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.OrderItem;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link OrderItemDao} interface.
 *
 * @author Shaheer
 */
public class JdbcOrderItemDao extends GenericJDBCDao<OrderItem> implements OrderItemDao {
	
	private static final Logger logger = Logger.getLogger(JdbcOrderItemDao.class);
	
	@Override
	public List<OrderItem> findOrderItemByOrderId(Long orderId) {
		String sql = "SELECT id, " + getTableColumns() + " FROM " + getTableName() + " WHERE order_id = ?";
		logger.debug(sql);
		List<OrderItem> orderItems = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, orderId);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				orderItems.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return orderItems;
	}

	@Override
	protected String getTableName() {
		return "order_item";
	}

	@Override
	protected String getTableColumns() {
		return "order_id, product_isbn, product_title, quantity, unit_price, total_price";
	}

	@Override
	protected Object[] getEntityParameterValues(OrderItem entity) {
		return new Object[] { entity.getOrder().getId(), entity.getProductIsbn(), entity.getProductTitle(),
				entity.getQuantity(), entity.getUnitPrice(), entity.getTotalPrice() };
	}

	@Override
	protected OrderItem getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		OrderItem orderItem = new OrderItem();
		orderItem.setProductIsbn(resultSet.getString("product_isbn"));
		orderItem.setProductTitle(resultSet.getString("product_title"));
		orderItem.setQuantity(resultSet.getInt("quantity"));
		orderItem.setUnitPrice(resultSet.getFloat("unit_price"));
		orderItem.setTotalPrice(resultSet.getDouble("total_price"));
		return orderItem;
	}

}
