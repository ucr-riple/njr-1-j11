package de.hrw.mdbt.model;

import java.util.Date;

public class Report {
	private Date date;
	private String text;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
