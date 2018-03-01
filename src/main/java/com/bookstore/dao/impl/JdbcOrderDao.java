package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.OrderDao;
import com.bookstore.model.Order;
import com.bookstore.util.DAOUtil;

/**
 * This class represents a concrete JDBC implementation of the {@link OrderDao}
 * interface.
 *
 * @author Shaheer
 */
public class JdbcOrderDao extends GenericJDBCDao<Order> implements OrderDao {

	@Override
	protected String getTableName() {
		return "orders";
	}

	@Override
	protected String getTableColumns() {
		return "email_address, address_key, card_key, order_number, order_date, order_subtotal, order_tax, order_shipping, order_total";
	}

	@Override
	protected Object[] getEntityParameterValues(Order entity) {
		return new Object[] { entity.getEmail(), entity.getOrderAddress().getId(), entity.getOrderCard().getId(),
				entity.getOrderNumber(), DAOUtil.toSqlDate(entity.getOrderDate()), entity.getOrderSubtotal(), entity.getOrderTax(),
				entity.getOrderShipping(), entity.getOrderTotal() };
	}

	@Override
	protected Order getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setEmail(resultSet.getString("email"));
		order.setOrderNumber(resultSet.getLong("order_number"));
		order.setOrderDate(DAOUtil.toLocalDate(resultSet.getDate("order_date")));
		order.setOrderSubtotal(resultSet.getDouble("order_subtotal"));
		order.setOrderTax(resultSet.getDouble("order_tax"));
		order.setOrderShipping(resultSet.getDouble("order_shipping"));
		order.setOrderTotal(resultSet.getDouble("order_total"));
		return order;
	}

}
