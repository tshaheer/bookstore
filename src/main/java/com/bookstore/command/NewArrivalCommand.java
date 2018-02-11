package com.bookstore.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.ProductDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Product;
import com.bookstore.util.Constants;

public class NewArrivalCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		ProductDao productDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getProductDao();
		List<Product> newProducts = productDao.selectNewestProducts();
		request.setAttribute("products", newProducts);
		return Constants.JSP_ROOT + "new-arrival";
	}
	
}