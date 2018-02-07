package com.bookstore.model;

/**
 * @author Shaheer
 */
public class Author extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	public Author(String name) {
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
	 * The Author NAME is unique for each Author. So this should compare Author by
	 * NAME only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Author) && (name != null) ? name.equals(((Author) other).name) : (other == this);
	}

	/**
	 * The Author NAME is unique for each Author. So Author with same NAME should
	 * return same hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (name != null) ? (this.getClass().hashCode() + name.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Author. Not required, it just
	 * pleases reading logs.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Author[name=%s]", name);
	}

}
