package com.bookstore.model;

/**
 * This class represents the User model. This model class can be used thorough
 * out all layers, the data layer, the controller layer and the view layer.
 * 
 * @author Shaheer
 */
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * The user EMAIL is unique for each User. So this should compare User by EMAIL
	 * only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof User) && (email != null) ? email.equals(((User) other).email) : (other == this);
	}

	/**
	 * The user EMAIL is unique for each User. So User with same EMAIL should return
	 * same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (email != null) ? (this.getClass().hashCode() + email.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this User. Not required, it just pleases
	 * reading logs.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("User[id=%d,email=%s]", getId(), email);
	}

}
