package com.bookstore.model;

import com.bookstore.util.CreditCardUtil;

/**
 * @author Shaheer
 */
public class CreditCard extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String cardOwner;

	private String cardType;

	private String cardNumber;

	private int expMonth;

	private int expYear;
	
	private User user;

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}

	public int getExpYear() {
		return expYear;
	}

	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getObscuredNumber() {
		if ((cardNumber != null) && (cardNumber.length() > 6)) {
			String digitsOnly = CreditCardUtil.getDigitsOnly(cardNumber);
			String allStars = "****************************************";
			return digitsOnly.substring(0, 2) + allStars.substring(0, digitsOnly.length() - 6)
					+ digitsOnly.substring(digitsOnly.length() - 4, digitsOnly.length());
		} else {
			return "";
		}
	}

	/**
	 * The CreditCard ID is unique for each CreditCard. So this should compare
	 * CreditCard by ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof CreditCard) && (this.getId() != null)
				? this.getId().equals(((CreditCard) other).getId())
				: (other == this);
	}

	/**
	 * The CreditCard ID is unique for each CreditCard. So CreditCard with same ID
	 * should return same hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.getId() != null) ? (this.getClass().hashCode() + this.getId().hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this CreditCard. Not required, it just
	 * pleases reading logs.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("CreditCard[cardOwner=%s,cardType=%s,cardNumber=%s,expMonth=%d,expYear=%d]", cardOwner,
				cardType, cardNumber, expMonth, expYear);
	}

}
