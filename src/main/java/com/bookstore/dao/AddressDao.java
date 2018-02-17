package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Address;

public interface AddressDao extends GenericDao<Address, Long> {
	
	List<Address> findAddressByUserId(Long userId);
	
	void saveAddressBook(Long userId, Long addressId);
	
}
