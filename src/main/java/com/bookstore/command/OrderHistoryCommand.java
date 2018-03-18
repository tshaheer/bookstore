package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.OrderDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.User;
import com.bookstore.util.Constants;

public class OrderHistoryCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = request.getParameter("orderno");
		if (orderNo != null && !orderNo.trim().isEmpty()) {
			OrderDao orderDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getOrderDao();
			request.setAttribute("order", orderDao.findOneOrderNumber(Long.parseLong(orderNo)));
			return Constants.JSP_ROOT + "order-history-detail";
		} else {
			User customer = (User) request.getSession().getAttribute(Constants.ATTR_USER);
			OrderDao orderDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getOrderDao();
			request.setAttribute("orders", orderDao.findOrderByUserEmail(customer.getEmail()));
			return Constants.JSP_ROOT + "order-history";
		}
		
	}
}