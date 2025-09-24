package com.moblima.util;


import java.util.List;
import java.io.*;


import com.moblima.dataaccess.BookingDAO;
import com.moblima.dataaccess.BookingDAOImpl;
import com.moblima.dataaccess.CinemaDAO;
import com.moblima.dataaccess.CinemaDAOImpl;
import com.moblima.dataaccess.CineplexDAO;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.dataaccess.MovieDAOImpl;
import com.moblima.dataaccess.MovieGoerDAO;
import com.moblima.dataaccess.MovieGoerDAOImpl;
import com.moblima.dataaccess.MovieTicketDAO;
import com.moblima.dataaccess.MovieTicketDAOImpl;
import com.moblima.dataaccess.PublicHolidayDAO;
import com.moblima.dataaccess.SeatDAO;
import com.moblima.dataaccess.SeatDAOImpl;
import com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.MovieDAO;
import com.moblima.dataaccess.ShowTimeDAOImpl;
import com.moblima.dataaccess.TransactionDAO;
import com.moblima.dataaccess.TransactionDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;
import com.moblima.model.PublicHoliday;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;
import com.moblima.model.TicketPrice;
import com.moblima.model.Transaction;

public class showTimeReadtest {
	static CinemaDAO cinemaDAO;
	static CineplexDAO cineplexDAO;
	static MovieDAO movieDAO;
	static ShowTimeDAO showTimeDAO;
	static SeatDAO seatDAO;
	static BookingDAO bookingDAO;
	static MovieTicketDAO movieTicketDAO;
	static MovieGoerDAO movieGoerDAO;
	static TransactionDAO transactionDAO;
	static String toWrite = "";
	
	public static void main(String [] args){
		cinemaDAO = CinemaDAOImpl.getInstance();
		cineplexDAO = CineplexDAOImpl.getInstance();
		movieDAO = MovieDAOImpl.getInstance();
		showTimeDAO = ShowTimeDAOImpl.getInstance();
		seatDAO = SeatDAOImpl.getInstance();
		bookingDAO = BookingDAOImpl.getInstance();
		movieTicketDAO = MovieTicketDAOImpl.getInstance();
		movieGoerDAO = MovieGoerDAOImpl.getInstance();
		transactionDAO = TransactionDAOImpl.getInstance();
		
		
//		addTo("Showing generated information: ");
//		for(Cineplex cineplex: cineplexes){
//			addTo(cineplex.getCineplexName() + "\t" + cineplex.getLocation());
//		
//		for(Cinema cinema: cinemas){
//			addTo(cinema.getCinemaName() + "\t" + cinema.getCinemaClass() + "\t" + cinema.getCinemaCode() + cinema.getCineplex().getCineplexName());
//		}
		
		
		addTo("cineplexDAO");
		for(Cineplex cineplex: cineplexDAO.getCineplexes()){
			addTo(cineplex.getCineplexName() + "\t" + cineplex.getLocation());
			for(Cinema cinema: cineplex.getCinemas()){
				addTo(cinema.getCinemaName() + "\t" + cinema.getCinemaClass() + "\t" + cinema.getCinemaCode() + cinema.getCineplex().getCineplexName());
			}
			addTo("");
		}
		
		addTo("");
		addTo("cinemaDAO");
		int s=0;
		for(Cinema cinema: cinemaDAO.getCinemas()){
			addTo(cinema.getCinemaName() + "\t" + cinema.getCinemaClass() + "\t" + cinema.getCinemaCode() + cinema.getCineplex().getCineplexName());
			for(Seat seat: cinema.getSeats()){
				s++;
				addTo(seat.getSeatID() + "\t" + seat.getSeatRow() + seat.getSeatNo());
			}
		}
		addTo("no. of seats: " + s);
		
		addTo("");
		int i=0;
		addTo("moviesDAO");
		for(Movie movie: movieDAO.getMovies()){
			i++;
			addTo(movie.getTitle() + i);
			addTo("");
			addTo(movie.getShowTimes().size());
			for(ShowTime showTime: movie.getShowTimes()){
				addTo(showTime.getShowTimeID() + "\t"+ showTime.getTime()+ "\t" + showTime.getCinema().getCinemaName() + "\t" + showTime.getCinema().getCinemaCode());
			}
		}
		addTo("");
		addTo("showtimeDAO");
		for(ShowTime showTime: showTimeDAO.getShowTimes()){
			addTo("");
			addTo(showTime.getTime() +"\t"+ showTime.getMovie() + "\t" + showTime.getCinema().getCinemaName() + "\t" + showTime.getCinema().getCinemaCode());
			addTo("");
		}
		
		addTo("");
		addTo("SeatDAO: ");
		addTo("");
		addTo(seatDAO.getSeats().size());
		for(Seat seat: seatDAO.getSeats()){
			addTo(seat.getCinema().getCinemaName() + "\t" + seat.getSeatID());
		}
		
		addTo("");
		addTo("MovieGoers");
		for(MovieGoer movieGoer: movieGoerDAO.getMovieGoers()){
			addTo(movieGoer.getName() + "\t" +movieGoer.getAge());
		}
		
		addTo("");
		addTo("BookingDAO");
		addTo("");
		for(Booking booking: bookingDAO.getBookings()){
			addTo(booking.getBookingID() + "\t"+booking.getMovieGoer().getName() + "\t" + booking.getStatus());
			addTo("Movie Tikcet id:" );
			for(MovieTicket movieTicket: booking.getMovieTickets()){
				addTo(movieTicket.getMovieTicketID() + "\t" + movieTicket.getPrice());
			}
			addTo("");
		}
		
		addTo("");
		addTo("MovieTicketDAO");
		addTo("");
		for(MovieTicket movieTicket: movieTicketDAO.getMovieTickets()){
			addTo(movieTicket.getMovieTicketID() + "\t" + movieTicket.getPrice());
		}
		
		
		addTo("");
		addTo("TransactionDAO:");
		for(Transaction transaction: transactionDAO.getTransactions()){
			addTo(transaction.getTID() + "\t" + transaction.getAmount() +"\t"+ transaction.getTransactionMethod() + "\t" + transaction.getDate());
			
		}
		write(toWrite);
		System.out.println("done!!");
		System.out.println("Please check data.txt with wordpad or microsoft word");
		
		
	}
	static void write(int output){
		File file = new File("data.txt");
		
		try {
			FileOutputStream fop = new FileOutputStream(file);
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
 
			fop.write(output);
			fop.flush();
			fop.close();
 
//			addTo("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	static void write(String output){
		File file = new File("data.txt");
		
		try {
			FileOutputStream fop = new FileOutputStream(file);
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = output.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
//			addTo("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	static void addTo(String write){
		toWrite += write + "\n";
		
	}
	static void addTo(int write){
		toWrite += write + "\n";
		
	}
}
