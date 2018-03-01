package com.bookstore.model;

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
	
}
