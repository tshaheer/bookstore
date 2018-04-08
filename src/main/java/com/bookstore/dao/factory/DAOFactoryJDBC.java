package com.bookstore.dao.factory;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.OrderDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.dao.ProductDao;
import com.bookstore.dao.UserDao;
import com.bookstore.dao.impl.JdbcAddressDao;
import com.bookstore.dao.impl.JdbcAuthorDao;
import com.bookstore.dao.impl.JdbcCategoryDao;
import com.bookstore.dao.impl.JdbcCreditCardDao;
import com.bookstore.dao.impl.JdbcOrderDao;
import com.bookstore.dao.impl.JdbcOrderItemDao;
import com.bookstore.dao.impl.JdbcProductDao;
import com.bookstore.dao.impl.JdbcUserDao;

public class DAOFactoryJDBC implements DAOFactory {
	
	@Override
	public ProductDao getProductDao() {
		return new JdbcProductDao();
	}

	@Override
	public CategoryDao getCategoryDao() {
		return new JdbcCategoryDao();
	}

	@Override
	public AuthorDao getAuthorDao() {
		return new JdbcAuthorDao();
	}
	
	@Override
	public UserDao getUserDao() {
		return new JdbcUserDao();
	}

	@Override
	public AddressDao getAddressDao() {
		return new JdbcAddressDao();
	}

	@Override
	public CreditCardDao getCreditCardDao() {
		return new JdbcCreditCardDao();
	}

	@Override
	public OrderDao getOrderDao() {
		return new JdbcOrderDao();
	}

	@Override
	public OrderItemDao getOrderItemDao() {
		return new JdbcOrderItemDao();
	}

}
