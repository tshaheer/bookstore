package com.bookstore.jsp.customtags;

import java.time.ZoneId;

import javax.servlet.jsp.JspTagException;

/**
 * <p>
 * A handler for &lt;formatDate&gt; that supports rtexprvalue-based attributes.
 * </p>
 *
 * @author Shaheer
 */
public class FormatDateTag extends FormatDateSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets the value attribute.
	 *
	 * @param value
	 *            the value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Sets the style attribute.
	 *
	 * @param style
	 *            the style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Sets the pattern attribute.
	 *
	 * @param pattern
	 *            the pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Sets the zone attribute.
	 *
	 * @param dtz
	 *            the zone
	 * @throws JspTagException
	 *             incorrect zone or dtz parameter
	 */
	public void setZoneId(Object dtz) throws JspTagException {
		if (dtz == null || (dtz instanceof String && ((String) dtz).isEmpty())) {
			this.zoneId = null;
		} else if (dtz instanceof ZoneId) {
			this.zoneId = (ZoneId) dtz;
		} else if (dtz instanceof String) {
			try {
				this.zoneId = ZoneId.of((String) dtz);
			} catch (IllegalArgumentException iae) {
				throw new JspTagException("Incorrect Zone: " + dtz);
			}
		} else
			throw new JspTagException("Can only accept ZoneId or String objects.");
	}

}
