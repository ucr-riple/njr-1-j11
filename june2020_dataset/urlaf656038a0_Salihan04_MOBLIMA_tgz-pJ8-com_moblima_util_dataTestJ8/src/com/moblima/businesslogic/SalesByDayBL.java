package com.moblima.businesslogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.moblima.dataaccess.BookingDAO;
import com.moblima.dataaccess.BookingDAOImpl;
import com.moblima.dataaccess.CineplexDAO;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.ShowTimeDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieTicket;
import com.moblima.model.ShowTime;
import com.moblima.model.Transaction;

public class SalesByDayBL {

	private Calendar calendar;
	private CineplexDAO cineplexDAO;
	private ShowTimeDAO showTimeDAO;
	private List<Date> daysOfCurrentMonth;
	private List<Cineplex> cineplexes;
	private List<Movie> moviesByCineplex;
	private int currentMonth;
	private int showTimeMonth;
	private float indiTakings;

	public SalesByDayBL() {
		calendar = Calendar.getInstance();
		showTimeDAO = ShowTimeDAOImpl.getInstance();
		cineplexDAO = CineplexDAOImpl.getInstance();
		daysOfCurrentMonth = new ArrayList<Date>();
		cineplexes = new ArrayList<Cineplex>();
		moviesByCineplex = new ArrayList<Movie>();
		currentMonth = calendar.get(Calendar.MONTH);
		showTimeMonth = 0;
		indiTakings = 0;
	}

	public List<Date> getDaysOfCurrentMonth() {

		for(ShowTime showTime : showTimeDAO.getShowTimes()) {
			Date date = showTime.getTime();
			calendar.setTime(date);
			showTimeMonth = calendar.get(Calendar.MONTH);
			if(showTimeMonth == currentMonth && !daysOfCurrentMonth.contains(date))
				daysOfCurrentMonth.add(date);
		}

		return daysOfCurrentMonth;
	}

	public List<Cineplex> getCineplexes(Date date) {
		cineplexes = new ArrayList<Cineplex>();
		for(Cineplex cineplex : cineplexDAO.getCineplexes()) {
			for(Cinema c : cineplex.getCinemas()) {
				for(ShowTime st : c.getShowTimes()) {
					if(daysOfCurrentMonth.contains(st.getTime())) {
						cineplexes.add(cineplex);
						break;
					}
				}
			}
		}
		return cineplexes;
	}

	public List<Movie> getMoviesByCineplex(Cineplex cineplex) {
		moviesByCineplex = new ArrayList<Movie>();

		for(Cinema c : cineplex.getCinemas()) {
			for(ShowTime st : c.getShowTimes()) {
				int month;
				calendar.setTime(st.getTime());
				month = calendar.get(Calendar.MONTH);
				if(st.getMovieTickets().size() > 0 && !moviesByCineplex.contains(st.getMovie()) && currentMonth == month) {
					moviesByCineplex.add(st.getMovie());
				}
			}
		}

		return moviesByCineplex;
	}

	public float getindiTakings(Date date, Movie movie) {

		indiTakings = 0;

		for(ShowTime st : movie.getShowTimes()) {
			calendar.setTime(date);
			int cDay = calendar.get(Calendar.DATE);
			int cMonth = calendar.get(Calendar.MONTH);
			int cYear = calendar.get(Calendar.YEAR);
			
			calendar.setTime(st.getTime());
			int sDay = calendar.get(Calendar.DATE);
			int sMonth = calendar.get(Calendar.MONTH);
			int sYear = calendar.get(Calendar.YEAR);
			
			if(st.getMovie() == movie && sDay == cDay && sMonth == cMonth && sYear == cYear) { 
				for(MovieTicket ticket : st.getMovieTickets())
					indiTakings += (ticket.getPrice() + 1.5) * 1.07;
			}
		}

		return indiTakings;
	}
}
