package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Product;

public interface ProductDao extends GenericDao<Product, Long> {
	
	List<Product> selectNewestProducts();
	
	List<Product> findProductsByCategoryName(String name);
	
	Product findOneByISBN(String isbn);
	
}
