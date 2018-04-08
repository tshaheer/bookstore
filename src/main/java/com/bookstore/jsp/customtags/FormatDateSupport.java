package com.bookstore.jsp.customtags;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Support for tag handlers for &lt;formatDate&gt;, the date and time formatting
 * tag in JSTL 1.0.
 *
 * @author Shaheer
 */
public abstract class FormatDateSupport extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected Object value;

	protected String style;

	protected String pattern;

	protected ZoneId zoneId;

	private String var;

	private int scope;

	// Constructor and initialization
	public FormatDateSupport() {
		super();
		init();
	}

	private void init() {
		value = null;
		style = pattern = var = null;
		zoneId = null;
		scope = PageContext.PAGE_SCOPE;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setScope(String scope) {
		this.scope = Util.getScope(scope);
	}

	// *********************************************************************
	// Tag logic

	/*
	 * Formats the given instant or partial.
	 */

	@Override
	public int doEndTag() throws JspException {
		if (value == null) {
			if (var != null) {
				pageContext.removeAttribute(var, scope);
			}
			return EVAL_PAGE;
		}

		// Create formatter
		DateTimeFormatter formatter = createFormatter();

		// format value
		String formatted;
		if (value instanceof TemporalAccessor) {
			formatted = formatter.format((TemporalAccessor) value);
		} else {
			throw new JspException("value attribute of format tag must be a TemporalAccessor," + " was: "
					+ value.getClass().getName());
		}

		if (var != null) {
			pageContext.setAttribute(var, formatted, scope);
		} else {
			try {
				pageContext.getOut().print(formatted);
			} catch (IOException ioe) {
				throw new JspTagException(ioe.toString(), ioe);
			}
		}

		return EVAL_PAGE;
	}

	// Releases any resources we may have (or inherit)
	@Override
	public void release() {
		init();
	}

	private DateTimeFormatter createFormatter() throws JspException {
		DateTimeFormatter formatter;
		if (pattern != null) {
			formatter = DateTimeFormatter.ofPattern(pattern);
		} else if (style != null) {
			formatter = Util.createFormatterForStyle(style);
		} else {
			// use a medium date (no time) style by default; same as jstl
			formatter = Util.createFormatterForStyle("M-");
		}

		// set formatter timezone
		ZoneId zone = this.zoneId;
		if (zone != null) {
			formatter = formatter.withZone(zone);
		} else {
			if (value instanceof Instant || value instanceof LocalDateTime || value instanceof OffsetDateTime
					|| value instanceof OffsetTime || value instanceof LocalTime)
				// these time objects may need a zone to resolve some patterns
				// and/or styles, and as there is no zone we revert to the
				// system default zone
				formatter = formatter.withZone(ZoneId.systemDefault());
		}
		return formatter;
	}
}
