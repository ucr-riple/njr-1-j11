package com.moblima.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Booking implements Serializable{
	
	private Integer bookingID;
	private BookingStatus status;
	private Date date;
	private Transaction transaction;
	private MovieGoer movieGoer;
	private List<MovieTicket> movieTickets;
	
	public Booking(BookingStatus status, Date date, Transaction transaction, MovieGoer movieGoer, List<MovieTicket> movieTickets) {
		this.bookingID = -1;
		this.status = status;
		this.date = date;
		this.transaction = transaction;
		this.movieGoer = movieGoer;
		this.movieTickets = movieTickets;
	}
	
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	
	public int getBookingID() {
		return bookingID;
	}
	
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	
	public BookingStatus getStatus() {
		return status;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setMovieGoer(MovieGoer movieGoer) {
		this.movieGoer = movieGoer;
	}
	
	public MovieGoer getMovieGoer() {
		return movieGoer;
	}
	
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public void addMovieTicket(MovieTicket ticket) {
		movieTickets.add(ticket);
	}
	
	public void removeMovieTicket(MovieTicket ticket) {
		movieTickets.remove(ticket);
	}
	
	public List<MovieTicket> getMovieTickets() {
		return movieTickets;
	}
}

