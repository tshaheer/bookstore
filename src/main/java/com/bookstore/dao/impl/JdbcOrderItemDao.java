package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.model.OrderItem;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link OrderItemDao} interface.
 *
 * @author Shaheer
 */
public class JdbcOrderItemDao extends GenericJDBCDao<OrderItem> implements OrderItemDao {

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
