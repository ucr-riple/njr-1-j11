package com.moblima.businesslogic;

import java.util.List;

import com.moblima.dataaccess.SeatDAO;
import com.moblima.dataaccess.SeatDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.Seat;
import com.moblima.model.SeatType;
import com.moblima.model.ShowTime;

public class SeatBL {
	private SeatDAO seatDAO;
	private ShowTimeBL showTimeBL;
	
	public SeatBL(){
		seatDAO = SeatDAOImpl.getInstance();
		showTimeBL = new ShowTimeBL();
	}
	
	public void createSeat(int seatNo, String seatRow, SeatType type, Cinema cinema){
		Seat seat = new Seat(seatNo,seatRow,type,cinema);
		for(ShowTime showTime:showTimeBL.getShowTimes(cinema)){
			seat.setOccupiedAt(showTime, false);
		}
		seatDAO.createSeat(seat);
	}
	
	public boolean validateSeatNumbers(ShowTime showTime, String[] seats) {
		Seat seat = null;
		boolean[][] coupleSeatTracker = new boolean[3][8];
		
		for(int i=0;i<2;i++) {
			for(int j=0;j<8;j++) {
				coupleSeatTracker[i][j] = false;
			}
		}
		
		Cinema cinema = showTime.getCinema();
		
		for(String s: seats) {
			String seatRow = s.charAt(0) + "";
			int seatNo = Integer.parseInt(s.substring(1, s.length()));
			
			for(Seat _seat: cinema.getSeats()) {
				if(_seat.getSeatNo() == seatNo && _seat.getSeatRow().equals(seatRow)) {
					seat = _seat;
					break;
				}
			}
			
			if(seat == null) {
				return false;
			}
			else if(seat.isOccupiedAt(showTime)) {
				return false;
			}
			else if(seatRow.equals("A") || seatRow.equals("B")) {// checking for couple seats
				coupleSeatTracker[seatRow.charAt(0) - 'A'][seatNo-1] = true;
			}
		}
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<8;j+=2) {
				if(coupleSeatTracker[i][j] == !coupleSeatTracker[i][j+1]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void updateSeat(int seatID, int seatNo, String seatRow, SeatType type, Cinema cinema,ShowTime showTime){
		Seat seat = seatDAO.getSeat(seatNo,seatRow);
		seat.setSeatID(seatID);
		seat.setSeatNo(seatNo);
		seat.setSeatRow(seatRow);
		seat.setSeatType(type);
		seat.setCinema(cinema);
		if(!seat.isOccupiedAt(showTime)){//if user books seat
			seat.setOccupiedAt(showTime,true);
		}
		else{
			seat.setOccupiedAt(showTime,false);
		}
	}
	
	public void deleteSeat(int seatNo,String seatRow){
		Seat seat = seatDAO.getSeat(seatNo, seatRow);
		seatDAO.deleteSeat(seat);
	}

	public Seat getSeat(int seatNo,String seatRow){
		return seatDAO.getSeat(seatNo,seatRow);
	}
	
	public List<Seat> getSeats() {
		return seatDAO.getSeats();
	}

}
