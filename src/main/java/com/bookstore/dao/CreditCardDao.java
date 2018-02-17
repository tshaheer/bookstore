package com.bookstore.dao;

import com.bookstore.model.CreditCard;

public interface CreditCardDao extends GenericDao<CreditCard, Long> {
	
	CreditCard findOneByUserId(Long userId);
	
}
