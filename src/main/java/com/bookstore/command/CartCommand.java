package com.bookstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.dao.ProductDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.model.Product;
import com.bookstore.util.Constants;

public class CartCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if ("POST".equals(request.getMethod())) {
			String op = request.getParameter("op");
			if ("remove".equals(op)) {
				removeItem(request);
			}
			if ("add".equals(op)) {
				addItem(request);
			}
			if ("update".equals(op)) {
				updateItem(request);
			}
		} else {
			restoreCart(request);
		}
		return Constants.JSP_ROOT + "cart";
	}

	private void restoreCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.ATTR_SHOPPING_CART);
		if (cart == null || cart.getSize() <= 0) {
			String emptyMessage = "Your cart is empty";
			request.setAttribute("emptyMessage", emptyMessage);
		}

	}

	private void addItem(HttpServletRequest request) {
		// retrieve or create a cart
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.ATTR_SHOPPING_CART);
		if (cart == null) {
			cart = new Cart();
		}
		// get the product from the database, create a line item and put it into the
		// cart
		final String productCode = request.getParameter(Constants.PARAM_PRODUCT_CODE);
		int quantity;
		try {
			quantity = Integer.parseInt(request.getParameter(Constants.PARAM_QUANTITY));
			if (quantity < 0) {
				quantity = 1;
			}
		} catch (NumberFormatException e) {
			quantity = 1;
		}
		ProductDao productDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getProductDao();
		Product product = productDao.findOneByISBN(productCode);
		if (product != null) {
			CartItem lineItem = new CartItem();
			lineItem.setProduct(product);
			lineItem.setQuantity(quantity);
			cart.addItem(lineItem);
		}
		session.setAttribute(Constants.ATTR_SHOPPING_CART, cart);
	}

	private void removeItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.ATTR_SHOPPING_CART);
		final String productCode = request.getParameter(Constants.PARAM_PRODUCT_CODE);
		if (cart != null && productCode != null) {
			CartItem lineItem = cart.getItem(productCode);
			cart.removeItem(lineItem);
		}
	}

	private void updateItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute(Constants.ATTR_SHOPPING_CART);
		String productCode = request.getParameter(Constants.PARAM_PRODUCT_CODE);
		int quantity;
		try {
			quantity = Integer.parseInt(request.getParameter(Constants.PARAM_QUANTITY));
			if (quantity < 0) {
				quantity = 1;
			}
		} catch (NumberFormatException e) {
			quantity = 1;
		}
		if (cart != null && productCode != null) {
			CartItem lineItem = cart.getItem(productCode);
			lineItem.setQuantity(quantity);
		}
	}

}