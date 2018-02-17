package com.bookstore.command;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.CreditCard;
import com.bookstore.model.User;
import com.bookstore.util.BookStoreUtil;
import com.bookstore.util.Constants;

public class CreditCardCommand implements Command {

	private HashMap<String, String> validationErrors = new HashMap<>();

	private static final String ATTR_MODEL = "modalCreditCard";

	private static final String VIEW = Constants.JSP_ROOT + "credit-card";

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
		CreditCardDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCreditCardDao();
		String op = request.getParameter("op");
		if ("new".equals(op)) {
			request.setAttribute(ATTR_MODEL, new CreditCard());
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
		CreditCard creditCard = prepareCreditCardModel(request);
		if (isValid(creditCard)) {
			this.save(creditCard);
			return "redirect:home.do?action=Account";
		}
		request.setAttribute(ATTR_MODEL, creditCard);
		request.setAttribute("validationErrors", validationErrors);
		return VIEW;
	}

	private void save(CreditCard creditCard) {
		CreditCardDao creditCardDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCreditCardDao();
		creditCardDao.save(creditCard);
	}

	private CreditCard prepareCreditCardModel(HttpServletRequest request) {
		CreditCard creditCard = new CreditCard();
		String idString = request.getParameter("hdnId");
		if (idString != null && !idString.isEmpty()) {
			creditCard.setId(Long.parseLong(idString));
		}
		creditCard.setCardOwner(request.getParameter("card-owner"));
		String cardType = request.getParameter("card-type");
		if (!"-1".equals(cardType)) {
			creditCard.setCardType(cardType);
		}
		creditCard.setCardNumber(request.getParameter("card-number"));
		String expMonth = request.getParameter("expiry-month");
		if (!"-1".equals(expMonth)) {
			creditCard.setExpMonth(Integer.parseInt(expMonth));
		}
		creditCard.setExpYear(Integer.parseInt(request.getParameter("expiry-year")));
		User customer = (User)request.getSession().getAttribute(Constants.ATTR_USER);
		if( customer != null) {
			creditCard.setUser(customer);
		}
		return creditCard;
	}

	/*
	 * Validates data entered by user into creditCard form
	 * 
	 * @return true if input is valid, false otherwise
	 */
	public boolean isValid(CreditCard creditCard) {
		validationErrors.clear();
		boolean valid = true;
		if ((creditCard.getCardType() == null) || (creditCard.getCardType().isEmpty())) {
			addFieldError("cType", "Card Type is required.");
			valid = false;
		}
		if ((creditCard.getCardOwner() == null) || (creditCard.getCardOwner().isEmpty())) {
			addFieldError("cOwner", "Cardholder Name is required.");
			valid = false;
		}
		if ((creditCard.getCardNumber() == null) || (creditCard.getCardNumber().isEmpty())) {
			addFieldError("cNumber", "Card Number is required.");
			valid = false;
		} else {
			if (!validateCreditCardNumber(creditCard.getCardNumber())) {
				addFieldError("cNumber", "Invalid Card Number");
				valid = false;
			}
		}
		if (creditCard.getExpMonth() == 0) {
			addFieldError("expMonth", "Expiration Month is required.");
			valid = false;
		}
		if (creditCard.getExpYear() == 0) {
			addFieldError("expYear", "Expiration Year is required.");
			valid = false;
		}
		if (creditCard.getExpMonth() > 0 && creditCard.getExpYear() > 0
				&& !validateCCDate(creditCard.getExpMonth(), creditCard.getExpYear())) {
			addFieldError("expYear", "Expired Card Date");
			valid = false;
		}
		return valid;
	}

	private boolean validateCCDate(int expMonth, int expYear) {
		YearMonth cardYearMonth = YearMonth.of(expYear, expMonth);
		LocalDate cardDate = cardYearMonth.atDay(1);
		System.out.println("Cedit card date  ===== " + cardDate);
		System.out.println("Current date  ===== " + LocalDate.now());
		System.err.println("Vlidation is >>>" + cardDate.isAfter(LocalDate.now()));
		return cardDate.isAfter(LocalDate.now());
	}

	private boolean validateCreditCardNumber(String cardNumber) {
		String digitsOnly = BookStoreUtil.getDigitsOnly(cardNumber);
		int sum = 0;
		int digit = 0;
		int addend = 0;
		boolean timesTwo = false;
		if (!prefixValid(digitsOnly)) {
			return false;
		}
		for (int i = digitsOnly.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
			if (timesTwo) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			timesTwo = !timesTwo;
		}
		int modulus = sum % 10;
		return modulus == 0;
	}

	private boolean prefixValid(String digitsOnly) {
		boolean foundcard = false;
		int digitLength = digitsOnly.length();
		// MC
		if (digitsOnly.startsWith("51") || digitsOnly.startsWith("52") || digitsOnly.startsWith("53")
				|| digitsOnly.startsWith("54")) {
			if (digitLength != 16) {
				return false;
			}
			foundcard = true;
		}
		// VISA
		if (digitsOnly.startsWith("4")) {
			if ((digitLength != 16) && (digitLength != 13))
				return false;
			foundcard = true;
		}
		// AMEX
		if (digitsOnly.startsWith("34") || digitsOnly.startsWith("37")) {
			if (digitLength != 15) {
				return false;
			}
			foundcard = true;
		}
		// DISC
		if (digitsOnly.startsWith("6011")) {
			if (digitLength != 16) {
				return false;
			}
			foundcard = true;
		}
		return foundcard;
	}

	private void addFieldError(String fieldname, String error) {
		validationErrors.put(fieldname, error);
	}

}