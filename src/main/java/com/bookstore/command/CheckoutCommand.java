package com.bookstore.command;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.factory.DAOFactory;
import com.bookstore.model.Address;
import com.bookstore.model.Cart;
import com.bookstore.model.CreditCard;
import com.bookstore.model.User;
import com.bookstore.util.CreditCardUtil;
import com.bookstore.util.Constants;

public class CheckoutCommand implements Command {

	private HashMap<String, String> validationErrors = new HashMap<>();

	private static final String ATTR_CARD_MODEL = "modalCreditCard";

	private static final String VIEW = Constants.JSP_ROOT + "checkout";

	private static final String ATTR_TOGGLE = "toggleChecked";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if ("POST".equals(request.getMethod())) {
			return handlePostRequest(request);
		}
		loadFormData(request);
		request.setAttribute(ATTR_TOGGLE, false);
		return VIEW;
	}

	private String handlePostRequest(HttpServletRequest request) {
		if (isValidOrder(request)) {
			HttpSession session = request.getSession();
			AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
			Long id = Long.parseLong(request.getParameter("sbAddress"));
			session.setAttribute(Constants.ATTR_ADDRESS, addressDao.findById(id).map(a -> a).orElse(null));
			session.setAttribute(Constants.ATTR_CARD, (CreditCard) request.getAttribute(ATTR_CARD_MODEL));
			return "redirect:home.do?action=Order";
		} else {
			request.setAttribute("validationErrors", validationErrors);
			loadFormData(request);
			return VIEW;
		}
	}

	private void loadFormData(HttpServletRequest request) {
		User customer = (User) request.getSession().getAttribute(Constants.ATTR_USER);
		populateAddressDropdown(request, customer);
		populateCreditCardDetails(request, customer);
	}

	private void populateCreditCardDetails(HttpServletRequest request, User customer) {
		CreditCardDao creditCardDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCreditCardDao();
		CreditCard creditCard = creditCardDao.findOneByUserId(customer.getId());
		request.setAttribute("creditCard", creditCard);
	}

	private void populateAddressDropdown(HttpServletRequest request, User customer) {
		AddressDao addressDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getAddressDao();
		List<Address> addresses = addressDao.findAddressByUserId(customer.getId());
		request.setAttribute("addresses", addresses);
	}

	private CreditCard prepareCreditCardModel(HttpServletRequest request) {
		CreditCard creditCard = new CreditCard();
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
		User customer = (User) request.getSession().getAttribute(Constants.ATTR_USER);
		if (customer != null) {
			creditCard.setUser(customer);
		}
		return creditCard;
	}

	private boolean isValidOrder(HttpServletRequest request) {
		CreditCard creditCard = new CreditCard();
		validationErrors.clear();
		boolean valid = true;
		// validate cart
		Cart cart = (Cart) request.getSession().getAttribute(Constants.ATTR_SHOPPING_CART);
		if (cart == null || cart.getSize() <= 0) {
			addFieldError("emptyCart", "Your cart is empty.");
			valid = false;
		}
		// validate address
		if (!isValidAddress(request)) {
			valid = false;
		}
		// validate credit card
		String cardId = request.getParameter("cardId");
		if ("on".equals(request.getParameter("cbToggle"))) {
			request.setAttribute(ATTR_TOGGLE, true);
			if (cardId == null || "".equals(cardId)) {
				addFieldError("errCreditCard", "Please add or enter payment information.");
				valid = false;
			} else {
				CreditCardDao creditCardDao = DAOFactory.getDAOFactory(DAOFactory.FactoryType.JDBC).getCreditCardDao();
				creditCard = creditCardDao.findById(Long.parseLong(cardId)).map(c -> c).orElse(null);
			}
		} else {
			request.setAttribute(ATTR_TOGGLE, false);
			creditCard = prepareCreditCardModel(request);
			if (!isValidCreditCard(creditCard)) {
				valid = false;
			}
		}
		request.setAttribute(ATTR_CARD_MODEL, creditCard);
		return valid;
	}

	private boolean isValidAddress(HttpServletRequest request) {
		boolean valid = true;
		if ("-1".equals(request.getParameter("sbAddress"))) {
			addFieldError("errAddress", "Please select or add new address.");
			valid = false;
		}
		return valid;
	}

	public boolean isValidCreditCard(CreditCard creditCard) {
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
			if (!CreditCardUtil.validateCreditCardNumber(creditCard.getCardNumber())) {
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
		return cardDate.isAfter(LocalDate.now());
	}

	private void addFieldError(String fieldname, String error) {
		validationErrors.put(fieldname, error);
	}

}