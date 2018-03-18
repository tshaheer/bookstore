package com.bookstore.command;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.dao.OrderDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Address;
import com.bookstore.model.Cart;
import com.bookstore.model.CreditCard;
import com.bookstore.model.Order;
import com.bookstore.model.Order.OrderStatus;
import com.bookstore.model.User;
import com.bookstore.util.Constants;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.MailUtil;

public class OrderCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute(Constants.ATTR_SHOPPING_CART) == null
				|| request.getSession().getAttribute(Constants.ATTR_ADDRESS) == null
				|| request.getSession().getAttribute(Constants.ATTR_CARD) == null) {
			// redirect to cart page
			return "redirect:home.do?action=Cart";
		}
		if ("POST".equals(request.getMethod())) {
			completeOrder(request);
			return Constants.JSP_ROOT + "receipt";
		}
		return Constants.JSP_ROOT + "order";
	}

	private void completeOrder(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User customer = (User) session.getAttribute(Constants.ATTR_USER);
		Address address = (Address) session.getAttribute(Constants.ATTR_ADDRESS);
		CreditCard creditCard = (CreditCard) session.getAttribute(Constants.ATTR_CARD);
		Cart cart = (Cart) session.getAttribute(Constants.ATTR_SHOPPING_CART);
		// insert to the database
		Order savedOrder = recordOrder(customer, address, creditCard, cart);
		request.setAttribute("amount", savedOrder.getOrderTotalString());
		request.setAttribute("orderNo", savedOrder.getOrderNumber());
		// remove all session data
		removeSession(session);
		// send confirmation email
		MailUtil.sendOrderConfirmation(request.getServletContext(), customer.getEmail(), address.getFirstName());
	}
	
	private void removeSession(HttpSession session) {
		session.setAttribute(Constants.ATTR_SHOPPING_CART, null);
		session.setAttribute(Constants.ATTR_ADDRESS, null);
		session.setAttribute(Constants.ATTR_CARD, null);
	}

	private Order recordOrder(User customer, Address address, CreditCard creditCard, Cart cart) {
		Order order = new Order();
		order.setEmail(customer.getEmail());
		order.setOrderAddress(address);
		order.setOrderCard(creditCard);
		order.setOrderNumber(DAOUtil.generateUUID());
		order.setOrderDate(LocalDate.now());
		order.setOrderSubtotal(cart.getTotalPrice());
		order.setOrderShipping(0);
		order.setOrderTax(0);
		order.setOrderTotal(cart.getTotalPrice());
		order.setOrderStatus(OrderStatus.PROCESSING);
		
		OrderDao orderDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getOrderDao();
		return orderDao.saveOrderAndOrderItems(order, cart);
	}

}
