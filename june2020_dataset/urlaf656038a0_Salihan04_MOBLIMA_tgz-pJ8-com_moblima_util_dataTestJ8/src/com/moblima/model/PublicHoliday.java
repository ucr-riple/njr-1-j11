package com.moblima.model;

import java.io.Serializable;
import java.util.Date;

public class PublicHoliday implements Serializable {

	private int publicHolidayID;
	private Date date;
	private String name;
	
	public PublicHoliday(String name, Date date) {
		this.publicHolidayID = -1;
		this.date = date;
		this.name = name;
	}
	
	public void setPublicHolidayID(int publicHolidayID) {
		this.publicHolidayID = publicHolidayID;
	}
	
	public int getPublicHolidayID() {
		return publicHolidayID;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
