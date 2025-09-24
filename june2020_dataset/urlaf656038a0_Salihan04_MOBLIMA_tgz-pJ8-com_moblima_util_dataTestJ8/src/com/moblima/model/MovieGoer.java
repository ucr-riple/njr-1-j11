package com.moblima.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieGoer implements Serializable {
	
	private Integer movieGoerID;
	private String name;
	private String mobileNo;
	private String email;
	private Date dateOfBirth;
	List<Booking> bookings;
	
	public MovieGoer(String name, String mobileNo, String email, Date dateOfBirth) {
		this.movieGoerID = -1;
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		bookings = new ArrayList<Booking>();
	}
	
	public void setMovieGoerID(int movieGoerID) {
		this.movieGoerID = movieGoerID;
	}
	
	public int getMovieGoerID() {
		return movieGoerID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void addBooking(Booking booking) {
		bookings.add(booking);
	}
	
	public void removeBooking(Booking booking) {
		bookings.remove(booking);
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}
	
	public int getAge() {
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dateOfBirth);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		
		return age;
	}
}
