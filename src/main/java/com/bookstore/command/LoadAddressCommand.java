package com.bookstore.command;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Address;

public class LoadAddressCommand implements Command {

	private static final Logger logger = Logger.getLogger(LoadAddressCommand.class);

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Loading address via ajax request");
		Long id = Long.parseLong(request.getParameter("id"));
		AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
		Address address = addressDao.findById(id).map(a -> a).orElse(null);
		String strAjaxResponseText = "{}";
		if (address != null) {
			strAjaxResponseText = convertToJSONString(address);
		}
		return strAjaxResponseText;
	}

	private String convertToJSONString(Address address) {
		return Json.createObjectBuilder().add("firstName", address.getFirstName())
				.add("lastName", address.getLastName()).add("street1", address.getStreet1())
				.add("street2", address.getStreet2()).add("city", address.getCity()).add("state", address.getState())
				.add("postalCode", address.getPostalCode()).build().toString();
	}
}