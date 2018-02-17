package com.bookstore.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Address;
import com.bookstore.model.User;
import com.bookstore.util.Constants;

public class AddressCommand implements Command {

	private HashMap<String, String> validationErrors = new HashMap<>();

	private static final String ATTR_MODEL = "modalAddress";

	private static final String VIEW = Constants.JSP_ROOT + "address";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if ("GET".equals(request.getMethod())) {
			return handleGETRequest(request);
		} else if ("POST".equals(request.getMethod())) {
			return handlePostRequest(request);
		}
		return VIEW;
	}

	private String handleGETRequest(HttpServletRequest request) {
		String op = request.getParameter("op");
		AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
		if ("new".equals(op)) {
			request.setAttribute(ATTR_MODEL, new Address());
		}
		if ("edit".equals(op)) {
			Long id = Long.parseLong(request.getParameter("id"));
			request.setAttribute(ATTR_MODEL, addressDao.findById(id).map(a -> a).orElse(null));
		}
		if ("delete".equals(op)) {
			Long id = Long.parseLong(request.getParameter("id"));
			addressDao.deleteById(id);
			return "redirect:home.do?action=Account";
		}
		return VIEW;
	}

	private String handlePostRequest(HttpServletRequest request) {
		Address address = prepareAddressModel(request);
		User customer = (User) request.getSession().getAttribute(Constants.ATTR_USER);
		if (isValid(address)) {
			AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
			addressDao.save(address);
			if("Save".equals(request.getParameter("op"))){
				// save to address book also
				addressDao.saveAddressBook(customer.getId(), address.getId());
			}
			return "redirect:home.do?action=Account";
		}
		request.setAttribute(ATTR_MODEL, address);
		request.setAttribute("validationErrors", validationErrors);
		return VIEW;
	}

	private Address prepareAddressModel(HttpServletRequest request) {
		Address address = new Address();
		String idString = request.getParameter("hdnId");
		if (idString != null && !idString.isEmpty()) {
			address.setId(Long.parseLong(idString));
		}
		address.setFirstName(request.getParameter("fName"));
		address.setLastName(request.getParameter("lName"));
		address.setStreet1(request.getParameter("street1"));
		address.setStreet2(request.getParameter("street2"));
		address.setState(request.getParameter("state"));
		address.setCity(request.getParameter("city"));
		address.setPostalCode(request.getParameter("pCode"));
		return address;
	}

	/*
	 * Validates data entered by user into address form
	 * 
	 * @return true if input is valid, false otherwise
	 */
	public boolean isValid(Address address) {
		validationErrors.clear();
		boolean valid = true;
		if ((address.getLastName() == null) || (address.getLastName().isEmpty())) {
			addFieldError("lName", "Last Name is required.");
			valid = false;
		}
		if ((address.getFirstName() == null) || (address.getFirstName().isEmpty())) {
			addFieldError("fName", "First Name is required.");
			valid = false;
		}
		if ((address.getStreet1() == null) || (address.getStreet1().isEmpty())) {
			addFieldError("street1", "Street Address is required.");
			valid = false;
		}
		if ((address.getCity() == null) || (address.getCity().isEmpty())) {
			addFieldError("city", "City is required.");
			valid = false;
		}
		if ((address.getState() == null) || (address.getState().isEmpty())) {
			addFieldError("state", "State is required.");
			valid = false;
		}
		if ((address.getPostalCode() == null) || (address.getPostalCode().isEmpty())) {
			addFieldError("pCode", "Postal Code is required.");
			valid = false;
		}
		return valid;
	}

	private void addFieldError(String fieldname, String error) {
		validationErrors.put(fieldname, error);
	}

}