package com.bookstore.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents a cart which can be filled with cart items by a
 * customer.
 *
 * @author Shaheer
 */
public class Cart implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Map<String, CartItem> items;

	public Cart() {
		items = new HashMap<>();
	}

	public Iterator<CartItem> getItems() {
		return items.values().iterator();
	}

	public CartItem getItem(String isbn) {
		return items.get(isbn);
	}

	/**
	 * Adds a line item into the cart if it's not already there. Otherwise, the
	 * quantity will be increased.
	 *
	 * @param lineItem
	 *            The line item to be added to the cart
	 */
	public void addItem(CartItem lineItem) {
		String isbn = lineItem.getProduct().getIsbn();
		if (items.get(isbn) != null) {
			items.get(isbn).addQuantity(lineItem.getQuantity());
		} else {
			items.put(isbn, lineItem);
		}
	}

	/**
	 * Removes the item if it exists in the cart, does nothing otherwise
	 *
	 * @param lineItem
	 *            The line item to be removed
	 */
	public void removeItem(CartItem lineItem) {
		items.remove(lineItem.getProduct().getIsbn());
	}

	/**
	 * @return The amount of LineItem objects in the cart
	 */
	public int getSize() {
		return items.size();
	}

	/**
	 * @return The total price for the items in the cart
	 */
	public double getTotalPrice() {
		double total = 0.0;
		Iterator<CartItem> it = items.values().iterator();
		while (it.hasNext()) {
			total += it.next().getTotalPrice();
		}
		return total;
	}

	/**
	 * @return Formatted String which represents the total price for a cart
	 */
	public String getTotalPriceCurrencyFormat() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
		double totalPrice = getTotalPrice();
		return currencyFormat.format(totalPrice);
	}

}
