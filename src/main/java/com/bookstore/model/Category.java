package com.bookstore.model;

/**
 * @author Shaheer
 */
public class Category extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	public Category(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * The Category NAME is unique for each Category. So this should compare Category by
	 * NAME only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Category) && (name != null) ? name.equals(((Category) other).name) : (other == this);
	}

	/**
	 * The Category NAME is unique for each Category. So Category with same NAME should
	 * return same hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (name != null) ? (this.getClass().hashCode() + name.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Category. Not required, it just
	 * pleases reading logs.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Category[name=%s]", name);
	}

}
