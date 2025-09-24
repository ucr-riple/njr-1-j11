package com.moblima.businesslogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.moblima.dataaccess.MovieDAO;
import com.moblima.dataaccess.MovieDAOImpl;
import com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.ShowTimeDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;

public class ShowTimeBL {
	
	private Calendar calendar;
	private ShowTimeDAO showTimeDAO;
	
	public ShowTimeBL(){
		showTimeDAO = ShowTimeDAOImpl.getInstance();
		calendar = Calendar.getInstance();
	}
	
	public void createShowTime(Cinema cinema, Movie movie, Date time){
		ShowTime showTime = new ShowTime(cinema, movie,time);
		showTimeDAO.createShowTime(showTime);
	}
	
	public void updateShowTime(ShowTime showTime, String time){
		// update later
		
		calendar.setTime(showTime.getTime());
		
		String[] time_hh_mm = time.split(":");
		int hour = Integer.parseInt(time_hh_mm[0]);
		int minute = Integer.parseInt(time_hh_mm[1]);
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		showTime.setTime(calendar.getTime());
		showTimeDAO.updateShowTime(showTime);
	}
	
	public void deleteShowTime(Date time,Movie movie){
		for(ShowTime showTime :showTimeDAO.getShowTimes(movie)){
			if(time == showTime.getTime()){
				showTimeDAO.deleteShowTime(showTime);
			}
		}
	}
	
	public void deleteShowTime(Date time,Cinema cinema){
		for(ShowTime showTime :showTimeDAO.getShowTimes(cinema)){
			if(time == showTime.getTime()){
				showTimeDAO.deleteShowTime(showTime);
			}
		}
	}

	public ShowTime getShowTimebyMovie(Date time,Movie movie) {
		for(ShowTime showTime :showTimeDAO.getShowTimes(movie)){
			if(time == showTime.getTime()){
				return showTime;
			}
		}
		return null;
	}
	
	public ShowTime getShowTimebyID(int showTimeID) {
		return showTimeDAO.getShowTime(showTimeID);
	}
	
	public ShowTime getShowTimebyCinema(Date time,Cinema cinema) {
		for(ShowTime showTime :showTimeDAO.getShowTimes(cinema)){
			if(time == showTime.getTime()){
				return showTime;
			}
		}
		return null;
	}
	
	public List<ShowTime> getShowTimes(Movie movie){
		return showTimeDAO.getShowTimes(movie);
	}
	
	public List<ShowTime> getShowTimes(Cinema cinema){
		return showTimeDAO.getShowTimes(cinema);
	}
	
	public List<ShowTime> getShowTimes(Movie movie, Cineplex cineplex) {
		List<ShowTime> result = new ArrayList<ShowTime>();
		
		for(ShowTime showTime: movie.getShowTimes()) {
			Cinema cinema = showTime.getCinema();
			if(cinema.getCineplex().getCineplexCode() == cineplex.getCineplexCode()) {
				result.add(showTime);
			}
		}
		
		return result;
	}
	
	public void deleteShowTime(ShowTime showTime) {
		showTimeDAO.deleteShowTime(showTime);
	}
	
	public boolean isFullyBooked(ShowTime showTime) {
		Cinema cinema = showTime.getCinema();
		for(Seat seat: cinema.getSeats()) {
			if(!seat.isOccupiedAt(showTime)) {
				return false;
			}
		}
		
		return true;
	}

}
