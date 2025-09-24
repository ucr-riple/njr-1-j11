package com.moblima.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements Serializable {
	
	private String cinemaCode;
	private String cinemaName; // This is for display.
	private CinemaClass cinemaClass;
	private Cineplex cineplex;
	private List<ShowTime> showTimes;
	private List<Seat> seats;
	private Integer no_of_seat_row;
	private Integer no_of_seat_column;
	
	public Cinema(String cinemaCode, String cinemaName, CinemaClass cinemaClass, Cineplex cineplex) {
		this.cinemaCode = cinemaCode;
		this.cinemaName = cinemaName;
		this.cinemaClass = cinemaClass;
		this.cineplex = cineplex;
		showTimes = new ArrayList<ShowTime>();
		seats = new ArrayList<Seat>();
		no_of_seat_row = 0;
		no_of_seat_column = 0;
	}
	
	public void setCinemaCode(String cinemaCode) {
		this.cinemaCode = cinemaCode;
	}
	
	public String getCinemaCode() {
		return cinemaCode;
	}
	
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	
	public String getCinemaName() {
		return cinemaName;
	}
	
	public void setCinemaClass(CinemaClass cinemaClass) {
		this.cinemaClass = cinemaClass;
	}
	
	public CinemaClass getCinemaClass() {
		return cinemaClass;
	}
	
	public void setCineplex(Cineplex cineplex) {
		this.cineplex = cineplex;
	}
	
	public Cineplex getCineplex() {
		return cineplex;
	}
	
	public void addShowTime(ShowTime showTime) {
		showTimes.add(showTime);
	}
	
	public void removeShowTime(ShowTime showTime) {
		showTimes.remove(showTime);
	}
	
	public void setShowTimes(List<ShowTime> showTimes) {
		this.showTimes = showTimes;
	}
	
	public List<ShowTime> getShowTimes() {
		return showTimes;
	}
	
	// Get ShowTimes By Movie
		public List<ShowTime> getShowTimes(Movie movie) {
			List<ShowTime> shows = new ArrayList<ShowTime>();
			
			for(ShowTime st: showTimes) {
				if(st.getMovie().getMovieCode() == movie.getMovieCode()) {
					shows.add(st);
				}
			}
			
			return shows;
		}
	
	public void addSeat(Seat seat) {
		seats.add(seat);
	}
	
	public void removeSeat(Seat seat) {
		seats.remove(seat);
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public Seat getSeat(String seatRow, int seatNo) {
		for(Seat seat: seats) {
			if(seat.getSeatRow().equals(seatRow) && seat.getSeatNo() == seatNo) {
				return seat;
			}
		}
		
		return null;
	}
	
	public void setNoOfSeatRow(int no_of_rows) {
		no_of_seat_row = no_of_rows;
	}
	
	public int getNoOfSeatRow() {
		return no_of_seat_row;
	}
	
	public void setNoOfSeatColumn(int no_of_columns) {
		no_of_seat_column = no_of_columns;
	}
	
	public int getNoOfSeatColumn() {
		return no_of_seat_column;
	}
}
