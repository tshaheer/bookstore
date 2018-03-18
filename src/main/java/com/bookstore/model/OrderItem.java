package com.bookstore.model;

import java.text.NumberFormat;

import com.bookstore.util.Constants;

public class OrderItem extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private Order order;
	
	private String productIsbn;
	
	private String productTitle;
	
	private int quantity;
	
	private float unitPrice;
	
	private double totalPrice;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getProductIsbn() {
		return productIsbn;
	}

	public void setProductIsbn(String productIsbn) {
		this.productIsbn = productIsbn;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getPriceCurrencyFormat() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Constants.DEFAULT_LOCALE);
		return currencyFormat.format(getUnitPrice());
	}
	
	public String getTotalPriceCurrencyFormat() {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Constants.DEFAULT_LOCALE);
		return currencyFormat.format(getTotalPrice());
	}
	
}
