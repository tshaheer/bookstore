package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Author;

public interface AuthorDao extends GenericDao<Author, Long> {
	
	List<Author> findAuthorsByProductId(Long id);
	
}
