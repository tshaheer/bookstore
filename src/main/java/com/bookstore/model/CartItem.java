package com.bookstore.model;

import java.io.Serializable;
import java.text.NumberFormat;

import com.bookstore.util.Constants;

/**
 * This entity represents a collection of products with a specified quantity.
 * 
 * @author Shaheer
 */
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Product product;

	private int quantity = 1;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	public double getTotalPrice() {
		return product.getPrice() * quantity;
	}

	public String getTotalPriceCurrencyFormat() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Constants.DEFAULT_LOCALE);
		return currencyFormat.format(getTotalPrice());
	}

}
