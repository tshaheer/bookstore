package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.OrderDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.Order.OrderStatus;
import com.bookstore.model.OrderItem;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the {@link OrderDao}
 * interface.
 *
 * @author Shaheer
 */
public class JdbcOrderDao extends GenericJDBCDao<Order> implements OrderDao {

	private static final Logger logger = Logger.getLogger(JdbcOrderDao.class);

	@Override
	public Order saveOrderAndOrderItems(Order order, Cart cart) {
		// transaction block start
		Connection connection = DBManager.getDBConnection();
		try {
			connection.setAutoCommit(false);
			// save order
			try (PreparedStatement statementOrder = DAOUtil.prepareStatement(connection, this.getInsertQuery(), true,
					this.getEntityParameterValues(order));) {
				int affectedRows = statementOrder.executeUpdate();
				if (affectedRows == 0) {
					throw new DataAccessException("Creating entity failed, no rows affected.");
				}
				try (ResultSet generatedKeys = statementOrder.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						order.setId(generatedKeys.getLong(1));
					} else {
						throw new DataAccessException("Creating entity failed, no generated key obtained.");
					}
				}
			}
			// save orderItems
			final String insertItemSQL = "INSERT INTO order_item (order_id, product_isbn, product_title, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?, ?)";
			Iterator<CartItem> itemIterator = cart.getItems();
			List<OrderItem> orderItems = new ArrayList<>();
			while (itemIterator.hasNext()) {
				CartItem item = itemIterator.next();
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProductIsbn(item.getProduct().getIsbn());
				orderItem.setProductTitle(item.getProduct().getTitle());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setUnitPrice(item.getProduct().getPrice());
				orderItem.setTotalPrice(item.getTotalPrice());
				orderItems.add(orderItem);
				try (PreparedStatement statement = DAOUtil.prepareStatement(connection, insertItemSQL, true,
						orderItem.getOrder().getId(), orderItem.getProductIsbn(), orderItem.getProductTitle(),
						orderItem.getQuantity(), orderItem.getUnitPrice(), orderItem.getTotalPrice())) {
					if (statement.executeUpdate() == 0) {
						throw new DataAccessException("Creating order item failed, no rows affected.");
					}
				}
			}
			order.setItems(orderItems);
			// transaction block end
			DBManager.commitAndClose(connection);
		} catch (SQLException sqle) {
			DBManager.rollbackAndClose(connection);
			throw new DataAccessException(sqle.getMessage());
		}
		return order;
	}

	@Override
	public Order findOneOrderNumber(Long orderNo) {
		Order order = this.findByConditionUnique("order_number = '" + orderNo + "'");
		if (order == null) {
			return null;
		}
		OrderItemDao orderItemDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getOrderItemDao();
		List<OrderItem> orderItems = orderItemDao.findOrderItemByOrderId(order.getId());
		order.setItems(orderItems);
		return order;
	}

	@Override
	public List<Order> findOrderByUserEmail(String email) {
		String sql = "SELECT id, " + getTableColumns() + " FROM " + getTableName() + " WHERE email_address = ?";
		logger.debug(sql);
		List<Order> orders = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, email);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				orders.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return orders;
	}

	@Override
	protected String getTableName() {
		return "orders";
	}

	@Override
	protected String getTableColumns() {
		return "email_address, address_key, card_key, order_number, order_date, order_subtotal, order_tax, order_shipping, order_total, order_status";
	}

	@Override
	protected Object[] getEntityParameterValues(Order entity) {
		return new Object[] { entity.getEmail(), entity.getOrderAddress().getId(), entity.getOrderCard().getId(),
				entity.getOrderNumber(), DAOUtil.toSqlDate(entity.getOrderDate()), entity.getOrderSubtotal(),
				entity.getOrderTax(), entity.getOrderShipping(), entity.getOrderTotal(),
				entity.getOrderStatus().name() };
	}

	@Override
	protected Order getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong("id"));
		order.setEmail(resultSet.getString("email_address"));
		order.setOrderNumber(resultSet.getLong("order_number"));
		order.setOrderDate(DAOUtil.toLocalDate(resultSet.getDate("order_date")));
		order.setOrderSubtotal(resultSet.getDouble("order_subtotal"));
		order.setOrderTax(resultSet.getDouble("order_tax"));
		order.setOrderShipping(resultSet.getDouble("order_shipping"));
		order.setOrderTotal(resultSet.getDouble("order_total"));
		order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
		return order;
	}

}
