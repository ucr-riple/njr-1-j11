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

public class SalesByMovieBL {

	private MovieBL movieBL;
	private List<Movie> moviesInCurrentMonth;
	private List<Cineplex> cineplexesByMovie;
	private float indiTakings;
	Calendar calendar;
	int currentMonth;
	int showTimeMonth;

	public SalesByMovieBL() {
		calendar = Calendar.getInstance();
		movieBL = new MovieBL();
		moviesInCurrentMonth = new ArrayList<Movie>();
		cineplexesByMovie = new ArrayList<Cineplex>();
		indiTakings = 0;
		currentMonth = calendar.get(Calendar.MONTH);
		showTimeMonth = 0;
		calendar = Calendar.getInstance();
	}

	public List<Movie> moviesInCurrentMonth() {

		for(Movie movie : movieBL.getMovies()) {
			for(ShowTime showTime : movie.getShowTimes()) {
				calendar.setTime(showTime.getTime());
				showTimeMonth = calendar.get(Calendar.MONTH);
				if(showTimeMonth == currentMonth && !moviesInCurrentMonth.contains(movie)) {
					moviesInCurrentMonth.add(movie);
				}
			}
		}

		return moviesInCurrentMonth;
	}

	public List<Cineplex> cineplexesByMovie(Movie movie) {
		cineplexesByMovie = new ArrayList<Cineplex>();
		for(ShowTime showTime : movie.getShowTimes()) {
			Cinema cinema = showTime.getCinema();
			if(showTime.getMovieTickets().size() > 0) {
				Cineplex cineplex = cinema.getCineplex();
				cineplexesByMovie.add(cineplex);
			}

		}

		return cineplexesByMovie;
	}

	public float getIndiTakings(Cineplex cineplex, Movie movie) {

		indiTakings = 0;
		
		for(Cinema cinema : cineplex.getCinemas()) {
			for(ShowTime showTime : cinema.getShowTimes()) {
				if(showTime.getMovie() == movie) {
					for(MovieTicket ticket : showTime.getMovieTickets()) {
						indiTakings += ((ticket.getPrice() + 1.5) * 1.07);  
					}
				}
			}
		}

		return indiTakings;
	}
}
