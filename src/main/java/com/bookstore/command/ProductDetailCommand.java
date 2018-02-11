package com.bookstore.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.ProductDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Author;
import com.bookstore.model.Category;
import com.bookstore.model.Product;
import com.bookstore.util.Constants;

public class ProductDetailCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		final String productCode = request.getParameter(Constants.PARAM_PRODUCT_CODE);
		ProductDao productDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getProductDao();
		Product product = productDao.findOneByISBN(productCode);
		populateAuthorsInfo(product);
		populateCategoryInfo(product);
		request.setAttribute("product", product);
		return Constants.JSP_ROOT + "product_detail";
	}

	private void populateAuthorsInfo(Product product) {
		AuthorDao authorDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAuthorDao();
		List<Author> authors = authorDao.findAuthorsByProductId(product.getId());
		product.setAuthors(authors);
	}
	
	private void populateCategoryInfo(Product product) {
		CategoryDao categoryDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCategoryDao();
		List<Category> categories = categoryDao.findCategoriesByProductId(product.getId());
		product.setCategories(categories);
	}

}