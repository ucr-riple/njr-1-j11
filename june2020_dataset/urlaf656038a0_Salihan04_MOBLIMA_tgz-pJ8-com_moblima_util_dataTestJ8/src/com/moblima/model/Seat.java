package com.moblima.model;

import java.io.Serializable;
import java.util.Hashtable;

public class Seat implements Serializable {
	
	private Integer seatID;
	private Integer seatNo;
	private String seatRow;
	private SeatType type;
	private Cinema cinema;
	private Hashtable<ShowTime, Boolean> occupied;
	
	public Seat(int seatNo, String seatRow, SeatType type, Cinema cinema) {
		this.seatID = -1;
		this.seatNo = seatNo;
		this.seatRow = seatRow;
		this.type = type;
		this.cinema = cinema;
		
		occupied = new Hashtable<ShowTime, Boolean>();
	}
	
	public int getSeatID() {
		return seatID;
	}
	
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	
	public int getSeatNo() {
		return seatNo;
	}
	
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	
	public String getSeatRow() {
		return seatRow;
	}
	
	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}
	
	public SeatType getSeatType() {
		return type;
	}
	
	public void setSeatType(SeatType type) {
		this.type = type;
	} 
	
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
	public Cinema getCinema() {
		return cinema;
	}
	
	// return boolean stating wherether the seat is occupied at particular showTime
	public boolean isOccupiedAt(ShowTime showTime) {
		return occupied.get(showTime);
	}
	
	public void setOccupiedAt(ShowTime showTime, boolean value) {
		occupied.put(showTime, value);
	}
}
