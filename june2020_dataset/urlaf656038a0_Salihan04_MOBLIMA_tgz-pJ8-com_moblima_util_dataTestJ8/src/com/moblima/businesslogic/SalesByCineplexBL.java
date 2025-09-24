package com.moblima.businesslogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.moblima.model.Booking;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieTicket;
import com.moblima.model.ShowTime;
import com.moblima.model.Transaction;

public class SalesByCineplexBL {

	private List<Movie> moviesByCineplex;
	private float indiTakings;
	int currentMonth;
	int showTimeMonth;
	Calendar calendar;

	public SalesByCineplexBL() {
		calendar = Calendar.getInstance();
		moviesByCineplex = new ArrayList<Movie>();
		indiTakings = 0;
		currentMonth = calendar.get(Calendar.MONTH);
		showTimeMonth = 0;
	}

	public List<Movie> moviesByCineplex(Cineplex cineplex) {
		moviesByCineplex = new ArrayList<Movie>();
		for(Cinema c : cineplex.getCinemas()) {
			for(ShowTime showTime : c.getShowTimes()) {
				calendar.setTime(showTime.getTime());
				showTimeMonth = calendar.get(Calendar.MONTH);
				if(showTimeMonth == currentMonth && !moviesByCineplex.contains(showTime.getMovie())) {
					moviesByCineplex.add(showTime.getMovie());						
				}
			}
		}
		return moviesByCineplex;
	}

	public float getIndiTakings(Movie movie) {
		
		indiTakings = 0;
		for(ShowTime showTime : movie.getShowTimes()) {
			if(showTime.getMovieTickets().size() > 0) {
				for(MovieTicket ticket : showTime.getMovieTickets()) {
					indiTakings += (ticket.getPrice() + 1.5) * 1.07;
				}
			}
		}

		return indiTakings;
	}
}

