package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Cinema;
import com.moblima.model.Seat;
import com.moblima.model.SeatType;

public class SeatDAOImpl implements SeatDAO {
	
	private static SeatDAO seatDAO;
	private SerializeDB serializeDB;
	private List<Seat> seats;
	
	public static SeatDAO getInstance() {
		if(seatDAO == null) {
			seatDAO = new SeatDAOImpl();
		}
		
		return seatDAO;
	}
		
	private SeatDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		seats = serializeDB.getSeats();
	}
	
	public void createSeat(Seat seat) {
		int seatID = serializeDB.getSeatID();
		seat.setSeatID(seatID);
		serializeDB.setSeatID(seatID+1);
		
		seats.add(seat);
		
		//Update cinema
		Cinema cinema = seat.getCinema();
		cinema.addSeat(seat);
		
		serializeDB.saveData();//need to save other way. this is not efficient.
	}
	
	
	public void updateSeat(Seat seat) {
		serializeDB.saveData();
	}
	
	public void deleteSeat(Seat seat) {
		seats.remove(seat);
		serializeDB.saveData();
	}
	
	public Seat getSeat(int seatNo, String row) {
		for(Seat s: seats) {
			if(s.getSeatNo() == seatNo && s.getSeatRow().equals(row)) {
				return s;
			}
		}
		
		return null;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
}
