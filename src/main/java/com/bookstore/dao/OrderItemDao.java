package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.OrderItem;

public interface OrderItemDao extends GenericDao<OrderItem, Long> {
	
	List<OrderItem> findOrderItemByOrderId(Long orderId);
	
}
