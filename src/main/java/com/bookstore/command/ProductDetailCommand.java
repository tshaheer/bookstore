package com.bookstore.command;

import java.util.List;
import java.util.stream.Collectors;

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
		final String isbn = request.getParameter("isbn");
		ProductDao productDao = DAOFactory.getDAOFactory(DAOFactory.JDBC).getProductDao();
		Product product = productDao.findOneByISBN(isbn);
		populateAuthorsInfo(product);
		populateCategoryInfo(product);
		request.setAttribute("product", product);
		return Constants.JSP_ROOT + "product_detail";
	}

	private void populateAuthorsInfo(Product product) {
		AuthorDao authorDao = DAOFactory.getDAOFactory(DAOFactory.JDBC).getAuthorDao();
		List<Author> authors = authorDao.findAuthorsByProductId(product.getId());
		product.setAuthorsString(createAuthorsString(authors));
	}
	
	private void populateCategoryInfo(Product product) {
		CategoryDao categoryDao = DAOFactory.getDAOFactory(DAOFactory.JDBC).getCategoryDao();
		List<Category> categories = categoryDao.findCategoriesByProductId(product.getId());
		product.setCategoriesString(createCategoriesString(categories));
	}

	private String createAuthorsString(List<Author> authors) {
		return authors.stream().map(Author::getName).collect(Collectors.joining(";"));
	}
	
	private String createCategoriesString(List<Category> categories) {
		return categories.stream().map(Category::getName).collect(Collectors.joining(";"));
	}

}