package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Booking;
import com.moblima.model.BookingStatus;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;

public class BookingDAOImpl implements BookingDAO{
	
	private static BookingDAO bookingDAO;
	private SerializeDB serializeDB;
	private List<Booking> bookings;
	
	public static BookingDAO getInstance() {
		if(bookingDAO == null) {
			bookingDAO = new BookingDAOImpl();
		}
		return bookingDAO;
	}
	
	private BookingDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		bookings = serializeDB.getBookings();
	}
	
	public void createBooking(Booking booking) {
		//Set bookingID
		int bookingID = serializeDB.getBookingID();
		booking.setBookingID(bookingID);
		serializeDB.setBookingID(bookingID+1);
		
		bookings.add(booking);
		
		//Update MovieGoer
		MovieGoerDAO movieGoerDAO = MovieGoerDAOImpl.getInstance();
		MovieGoer movieGoer = booking.getMovieGoer();
		movieGoer.addBooking(booking);
		movieGoerDAO.updateMovieGoer(movieGoer);
		
		//Update Ticket
		MovieTicketDAO movieTicketDAO = MovieTicketDAOImpl.getInstance();
		for(MovieTicket movieTicket: booking.getMovieTickets()) {
			movieTicket.setBooking(booking);
			movieTicketDAO.updateMovieTicket(movieTicket);
		}
		
		serializeDB.saveData();
	}

	public void updateBooking(Booking booking) {
		serializeDB.saveData();
	}

	public void deleteBooking(Booking booking) {
		bookings.remove(booking);
		serializeDB.saveData();
	}

	public Booking getBooking(int bookingID) {
		for(Booking booking: bookings) {
			if(booking.getBookingID() == bookingID)
				return booking;
		}
		return null;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}
}
