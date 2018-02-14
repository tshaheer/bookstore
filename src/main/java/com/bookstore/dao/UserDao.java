package com.bookstore.dao;

import com.bookstore.model.User;

public interface UserDao extends GenericDao<User, Long> {
	
	 public boolean existEmail(String email);
	 
	 public User findByEmailAndPassword(String email,  String password);

}
