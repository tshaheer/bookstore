package com.bookstore.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.ProductDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Product;
import com.bookstore.util.Constants;

public class ProductCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		final String categoryName = request.getParameter("name");
		ProductDao productDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getProductDao();
		List<Product> products;
		if(categoryName == null) {
			products = (List<Product>) productDao.findAll();
		} else {
			products = productDao.findProductsByCategoryName(categoryName);
		}
		request.setAttribute("products", products);
		return Constants.JSP_ROOT + "products";
	}
	
}