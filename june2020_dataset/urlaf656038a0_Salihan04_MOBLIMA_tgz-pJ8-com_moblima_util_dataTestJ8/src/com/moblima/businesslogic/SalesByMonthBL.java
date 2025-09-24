package com.moblima.businesslogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.moblima.dataaccess.CineplexDAO;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.ShowTimeDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieTicket;
import com.moblima.model.ShowTime;

public class SalesByMonthBL {

	private Calendar calendar;
	private CineplexDAO cineplexDAO;
	private ShowTimeDAO showTimeDAO;
	private List<Integer> monthsOfCurrentYear;
	private List<Date> daysOfCurrentMonth;
	private List<Cineplex> cineplexes;
	private List<Movie> moviesByCineplex;
	private int currentYear;
	private int showTimeYear;
	private int showTimeMonth;
	private int month;
	private float indiTakings;

	public SalesByMonthBL() {
		calendar = Calendar.getInstance();
		showTimeDAO = ShowTimeDAOImpl.getInstance();
		cineplexDAO = CineplexDAOImpl.getInstance();
		monthsOfCurrentYear = new ArrayList<Integer>();
		daysOfCurrentMonth = new ArrayList<Date>();
		cineplexes = new ArrayList<Cineplex>();
		moviesByCineplex = new ArrayList<Movie>();
		currentYear = calendar.get(Calendar.YEAR);
		showTimeYear = 0;
		showTimeMonth = 0;
		month = 0;
		indiTakings = 0;
	}

	public List<Integer> getMonthsOfCurrentYear() {

		for(ShowTime showTime : showTimeDAO.getShowTimes()) {
			Date date = showTime.getTime();
			calendar.setTime(date);
			showTimeYear = calendar.get(Calendar.YEAR);
			if(showTimeYear == currentYear) {
				calendar.setTime(date);
				month = calendar.get(Calendar.MONTH);
			}
			if(!monthsOfCurrentYear.contains(month))
				monthsOfCurrentYear.add(month);
		}

		return monthsOfCurrentYear;
	}

	public List<Date> getDaysOfCurrentMonth(int month) {

		for(ShowTime showTime : showTimeDAO.getShowTimes()) {
			Date date = showTime.getTime();
			calendar.setTime(date);
			showTimeMonth = calendar.get(Calendar.MONTH);
			if(showTimeMonth == month)
				daysOfCurrentMonth.add(date);
		}

		return daysOfCurrentMonth;
	}

	public List<Cineplex> getCineplexes(int month) {
		cineplexes = new ArrayList<Cineplex>();
		for(Cineplex cineplex : cineplexDAO.getCineplexes()) {
			for(Cinema c : cineplex.getCinemas()) {
				for(ShowTime st : c.getShowTimes()) {
					calendar.setTime(st.getTime());
					int sMonth = calendar.get(Calendar.MONTH);

					if(sMonth == month && !cineplexes.contains(cineplex)) {
						cineplexes.add(cineplex);
						break;
					}
				}
			}
		}
		return cineplexes;
	}

	public List<Movie> getMoviesByCineplex(int month, Cineplex cineplex) {
		for(Cinema c : cineplex.getCinemas()) {
			for(ShowTime st : c.getShowTimes()) {
				calendar.setTime(st.getTime());
				int sMonth = calendar.get(Calendar.MONTH);
				if(st.getMovieTickets().size() > 0 && sMonth == month)
					moviesByCineplex.add(st.getMovie());
			}
		}

		return moviesByCineplex;
	}

	public float getindiTakings(int month, Movie movie) {

		indiTakings = 0;

		for(ShowTime st : movie.getShowTimes()) {

			calendar.setTime(st.getTime());
			int sMonth = calendar.get(Calendar.MONTH);

			if(st.getMovie() == movie && sMonth == month) { 
				for(MovieTicket ticket : st.getMovieTickets())
					indiTakings += (ticket.getPrice() + 1.5) * 1.07;
			}
		}

		return indiTakings;
	}
}
