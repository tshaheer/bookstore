package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Category;

public interface CategoryDao extends GenericDao<Category, Long> {
	
	List<Category> findCategoriesByProductId(Long id);
	
}
