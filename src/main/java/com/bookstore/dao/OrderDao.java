package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Cart;
import com.bookstore.model.Order;

public interface OrderDao extends GenericDao<Order, Long> {
	
	List<Order> findOrderByUserEmail(String email);
	
	Order findOneOrderNumber(Long orderNo);
	
	Order saveOrderAndOrderItems(Order order, Cart cart);
	
}
