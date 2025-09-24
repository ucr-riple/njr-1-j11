package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.moblima.model.Cinema;
import com.moblima.model.Movie;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;

public class ShowTimeDAOImpl implements ShowTimeDAO {
	
	private static ShowTimeDAO showTimeDAO;
	private SerializeDB serializeDB;
	private List<ShowTime> showTimes;
	
	public static ShowTimeDAO getInstance() {
		if(showTimeDAO == null) {
			showTimeDAO = new ShowTimeDAOImpl();
		}
		
		return showTimeDAO;
	}
		
	private ShowTimeDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		showTimes = serializeDB.getShowTimes();
	}
	
	public void createShowTime(ShowTime showTime) {
		int showTimeID = serializeDB.getShowTimeID();
		showTime.setShowTimeID(showTimeID);
		serializeDB.setShowTimeID(showTimeID+1);
		
		showTimes.add(showTime);
		
		//Update movie
		Movie movie = showTime.getMovie();
		movie.addShowTime(showTime);
		
		//Update cinema
		Cinema cinema = showTime.getCinema();
		cinema.addShowTime(showTime);
		
		//Add entry to seat for the occupied status
		for(Seat seat: cinema.getSeats()) {
			seat.setOccupiedAt(showTime, false);
		}
		
		serializeDB.saveData();
	}
	
	public void updateShowTime(ShowTime showTime) {
		serializeDB.saveData();
	}
	
	public void deleteShowTime(ShowTime showTime) {
		Cinema cinema = showTime.getCinema();
		Movie movie = showTime.getMovie();
		
		cinema.removeShowTime(showTime);
		movie.removeShowTime(showTime);
		
		showTimes.remove(showTime);
		serializeDB.saveData();
	}
	
	public List<ShowTime> getShowTimes() {
		return showTimes;
	}
	
	public List<ShowTime> getShowTimes(Movie movie) {
		List<ShowTime> st = new ArrayList<ShowTime>();
		
		for(ShowTime show: showTimes) {
			if(show.getMovie() == movie) {
				st.add(show);
			}
		}
		
		return st;
	}
	
	public List<ShowTime> getShowTimes(Cinema cinema) {
		List<ShowTime> st = new ArrayList<ShowTime>();
		
		for(ShowTime show: showTimes) {
			if(show.getCinema() == cinema) {
				st.add(show);
			}
		}
		
		return st;
	}

	public ShowTime getShowTime(int showTimeID) {
		
		for(ShowTime showTime: showTimes) {
			if(showTime.getShowTimeID() == showTimeID) {
				return showTime;
			}
		}
		
		return null;
	}
	
}
