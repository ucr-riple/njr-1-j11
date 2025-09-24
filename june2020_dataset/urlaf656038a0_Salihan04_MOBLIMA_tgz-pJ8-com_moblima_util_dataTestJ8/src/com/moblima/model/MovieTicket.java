package com.moblima.model;

import java.io.Serializable;

public class MovieTicket implements Serializable {
	
	private Integer movieTicketID;
	private TicketType ticketType;
	private ShowTime showTime;
	private Booking booking;
	private String seatNo;
	private Float price;
	
	public MovieTicket(TicketType ticketType, ShowTime showTime, String seatNo, float price) {
		this.movieTicketID = -1;
		this.ticketType = ticketType;
		this.showTime = showTime;
		this.booking = null;
		this.seatNo = seatNo;
		this.price = price;
	}
	
	public void setMovieTicketID(int movieTicketID) {
		this.movieTicketID = movieTicketID;
	}
	
	public int getMovieTicketID() {
		return movieTicketID;
	}
	
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	public TicketType getTicketType() {
		return ticketType;
	}
	
	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}
	
	public ShowTime getShowTime() {
		return showTime;
	}
	
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public Booking getBooking() {
		return booking;
	}
	
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	public String getSeatNo() {
		return seatNo;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getPrice() {
		return price;
	}
}
