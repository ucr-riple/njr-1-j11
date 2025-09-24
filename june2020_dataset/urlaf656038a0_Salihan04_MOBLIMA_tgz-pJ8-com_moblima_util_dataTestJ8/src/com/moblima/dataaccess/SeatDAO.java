package com.moblima.dataaccess;

import com.moblima.model.SeatType;
import com.moblima.model.ShowTime;

import java.util.List;

import com.moblima.model.Seat;

public interface SeatDAO {
	public void createSeat(Seat seat);
	public void updateSeat(Seat seat);
	public void deleteSeat(Seat seat);
	
	public Seat getSeat(int seatNo, String seatRow);
	public List<Seat> getSeats();
	
}
