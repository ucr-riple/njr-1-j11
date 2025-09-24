package com.moblima.businesslogic;

import java.util.List;

import com.moblima.dataaccess.CinemaDAO;
import com.moblima.dataaccess.CinemaDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.CinemaClass;
import com.moblima.model.Cineplex;
import com.moblima.model.SeatType;

public class CinemaBL {
	private CinemaDAO cinemaDAO;
	private SeatBL seatBL;

	public CinemaBL(){
		cinemaDAO = CinemaDAOImpl.getInstance();
		seatBL = new SeatBL();
	}

	public void createCinema(String cinemaCode, String cinemaName, CinemaClass cinemaClass, Cineplex cineplex){
		Cinema cinema = new Cinema(cinemaCode,cinemaName,cinemaClass,cineplex);
		cinemaDAO.createCinema(cinema);
		int seatID, seatNo;
		
		SeatType seatType = SeatType.SINGLE;
		for(int k=1;k<=40;k++){
			for(int b=1;b<=40;b++){
				seatBL.createSeat(k, Integer.toString(b),seatType,cinema);
			}
		}
	}

	public void updateCinema(String cinemaCode, String cinemaName, CinemaClass cinemaClass, Cineplex cineplex){
		Cinema cinema = cinemaDAO.getCinema(cinemaCode);
		cinema.setCinemaCode(cinemaCode);
		cinema.setCinemaName(cinemaName);
		cinema.setCinemaClass(cinemaClass);
		cinema.setCineplex(cineplex);
		cinemaDAO.updateCinema(cinema);
	}

	public void deleteCinema(String cinemaCode){
		Cinema cinema = cinemaDAO.getCinema(cinemaCode);
		cinemaDAO.deleteCinema(cinema);
	}

	public Cinema getCinema(String cinemaCode){
		return cinemaDAO.getCinema(cinemaCode);
	}

	public List<Cinema> getCinemas(){
		return cinemaDAO.getCinemas();
	}
}