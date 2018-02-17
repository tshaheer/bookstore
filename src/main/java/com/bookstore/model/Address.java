package com.bookstore.model;

/**
 * @author Shaheer
 */
public class Address extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;

	private String street1;

	private String street2;

	private String city;

	private String state;

	private String postalCode;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName; 
	}

	/**
	 * The Address ID is unique for each Address. So this should compare Address by
	 * ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Address) && (this.getId() != null) ? this.getId().equals(((Address) other).getId())
				: (other == this);
	}

	/**
	 * The Address ID is unique for each Address. So Address with same ID should
	 * return same hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.getId() != null) ? (this.getClass().hashCode() + this.getId().hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Address. Not required, it just
	 * pleases reading logs.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Address[firstName=%s,lastName=%s,street1=%s,street2=%s,city=%s,state=%s,postalCode=%s]",
				firstName, lastName, street1, street2, city, state, postalCode);
	}

}
