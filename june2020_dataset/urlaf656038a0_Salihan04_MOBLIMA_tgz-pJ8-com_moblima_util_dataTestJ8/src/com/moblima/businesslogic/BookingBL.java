package com.moblima.businesslogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.moblima.dataaccess.BookingDAO;
import com.moblima.dataaccess.BookingDAOImpl;
import com.moblima.dataaccess.SeatDAO;
import com.moblima.dataaccess.SeatDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.BookingStatus;
import com.moblima.model.Cinema;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;
import com.moblima.model.TicketType;
import com.moblima.model.Transaction;
import com.moblima.model.TransactionMethod;


public class BookingBL{
	private static float bookingPrice = 1.5f;
	
	private BookingDAO bookingDAO;
	private SeatDAO seatDAO;
	private TransactionBL transactionBL;
	private MovieGoerBL movieGoerBL;
	private MovieTicketBL movieTicketBL;
	private TicketPriceBL ticketPriceBL;

	public BookingBL(){
		bookingDAO = BookingDAOImpl.getInstance();
		seatDAO = SeatDAOImpl.getInstance();
		transactionBL = new TransactionBL();
		movieGoerBL = new MovieGoerBL();
		movieTicketBL = new MovieTicketBL();
		ticketPriceBL = new TicketPriceBL();
	}

	public Booking createBooking(MovieGoer movieGoer, ShowTime showTime, TransactionMethod transactionMethod, String[] seats){
		Transaction transaction;
		List<MovieTicket> movieTickets;
		MovieTicket movieTicket;
		
		Cinema cinema = showTime.getCinema();
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String transactionID = cinema.getCinemaCode() + dateFormat.format(now.getTime());
		
		int no_of_tickets = seats.length;
		float ticket_price = ticketPriceBL.getPrice(showTime, movieGoer);
		float grand_total = (float)((no_of_tickets * ticket_price + bookingPrice) * 1.07);
		
		transaction = transactionBL.createTransaction(transactionID, grand_total, transactionMethod);
		
		int age = movieGoer.getAge();
		TicketType ticketType = movieGoerBL.getTicketType(movieGoer);
		
		movieTickets = new ArrayList<MovieTicket>();
		//create movie ticket
		for(String seat_number: seats) {
			movieTicket = movieTicketBL.createMovieTicket(ticketType, showTime, seat_number, ticket_price);
			movieTickets.add(movieTicket);
			
			//Update the seat status
			String seatRow = seat_number.charAt(0) + "";
			int seatNo = Integer.parseInt(seat_number.substring(1, seat_number.length()));
			Seat seat = cinema.getSeat(seatRow, seatNo);
			seat.setOccupiedAt(showTime, true);
			seatDAO.updateSeat(seat);
		}
		
		//create booking
		Booking booking = new Booking(BookingStatus.ACCEPTED, now, transaction, movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		
		return booking;
	}

	public void updateBooking(int bookingID, BookingStatus status, TransactionMethod transactionMethod,int movieGoerID,ShowTime showTime){
		Date date = showTime.getTime();
		MovieGoer movieGoer = movieGoerBL.getMovieGoerByID(movieGoerID);
		Booking booking = bookingDAO.getBooking(bookingID);
		booking.setBookingID(bookingID);
		booking.setStatus(status);
		booking.setDate(date);
		booking.setTransaction(null);
		booking.setMovieGoer(movieGoer);
		bookingDAO.updateBooking(booking);
	}

	public void deleteBooking(int bookingID){
		Booking booking = bookingDAO.getBooking(bookingID);
		bookingDAO.deleteBooking(booking);
	}

	public Booking getBooking(int bookingID){
		return bookingDAO.getBooking(bookingID);
	}

	public List<Booking> getBookings(){
		return bookingDAO.getBookings();
	}
}
