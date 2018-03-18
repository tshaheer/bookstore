package com.bookstore.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Shaheer
 */
public class Order extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String email;

	private Long orderNumber;

	private LocalDate orderDate;

	private double orderSubtotal;

	private double orderTax;

	private double orderShipping;

	private double orderTotal;

	private Address orderAddress;

	private CreditCard orderCard;
	
	private List<OrderItem> items; 
	
	private OrderStatus orderStatus;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public double getOrderSubtotal() {
		return orderSubtotal;
	}

	public void setOrderSubtotal(double orderSubtotal) {
		this.orderSubtotal = orderSubtotal;
	}

	public double getOrderTax() {
		return orderTax;
	}

	public void setOrderTax(double orderTax) {
		this.orderTax = orderTax;
	}

	public double getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(double orderShipping) {
		this.orderShipping = orderShipping;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Address getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(Address orderAddress) {
		this.orderAddress = orderAddress;
	}

	public CreditCard getOrderCard() {
		return orderCard;
	}

	public void setOrderCard(CreditCard orderCard) {
		this.orderCard = orderCard;
	}
	
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderSubtotalString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(getOrderSubtotal());
	}

	public String getOrderTaxString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(getOrderTax());
	}

	public String getOrderShippingString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(getOrderShipping());
	}

	public String getOrderTotalString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(getOrderTotal());
	}

	/**
	 * The Order ORDERNUMBER is unique for each Order. So this should compare Order
	 * by ORDERNUMBER only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Order) && (orderNumber != null) ? orderNumber.equals(((Order) other).orderNumber)
				: (other == this);
	}

	/**
	 * The Order ORDERNUMBER is unique for each Order. So Order with same
	 * ORDERNUMBER should return same hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (orderNumber != null) ? (this.getClass().hashCode() + orderNumber.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		return String.format(
				"Order [email=%s, orderNumber=%d, orderDate=%s, orderSubtotal=%s, orderTax=%s, orderShipping=%s, orderTotal=%s]",
				email, orderNumber, orderDate, orderSubtotal, orderTax, orderShipping, orderTotal);
	}
	
	public enum OrderStatus {
		PROCESSING, COMPLETED, CANCELLED, ONHOLD, SHIPPED, DELIVERED
	}

}
