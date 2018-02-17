package com.bookstore.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Address;
import com.bookstore.model.CreditCard;
import com.bookstore.model.User;
import com.bookstore.util.Constants;

public class AccountCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User customer = (User) request.getSession().getAttribute(Constants.ATTR_USER);
		AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
		List<Address> addresses = addressDao.findAddressByUserId(customer.getId());
		request.setAttribute("addresses", addresses);
		
		CreditCardDao creditCardDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCreditCardDao();
		CreditCard creditCard =  creditCardDao.findOneByUserId(customer.getId());
		request.setAttribute("creditCard", creditCard);
		return Constants.JSP_ROOT + "account";
	}
}