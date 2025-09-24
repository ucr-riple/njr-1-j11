package com.moblima.presentation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.moblima.businesslogic.BookingBL;
import com.moblima.dataaccess.MovieGoerDAO;
import com.moblima.dataaccess.MovieGoerDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;
import com.moblima.model.ShowTime;
import com.moblima.model.Transaction;
import com.moblima.util.ConsoleReader;

public class CheckBookingAndHistory_CLI {

	private MovieGoerDAO movieGoerDAO;
	private BookingBL bookingBL;
	
	public CheckBookingAndHistory_CLI() {
		bookingBL = new BookingBL();
		movieGoerDAO = MovieGoerDAOImpl.getInstance();
	}
	
	public void printBookingDetail(Booking booking) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		List<MovieTicket> movieTickets = booking.getMovieTickets();
		ShowTime showTime = movieTickets.get(0).getShowTime();
		Movie movie = showTime.getMovie();
		Transaction transaction = booking.getTransaction();
		String seats = "";
		
		for(MovieTicket movieTicket: movieTickets) {
			seats += movieTicket.getSeatNo() + ". ";
		}
		
		System.out.println();
		System.out.println(dateFormat.format(booking.getDate()));
		System.out.println("========================================");
		System.out.println("Booking Ref No.\t: " + booking.getBookingID());
		System.out.println("TID\t\t: " + transaction.getTID());
		System.out.println("Booking Status\t: [" + booking.getStatus() + "]");
		System.out.println("Movie Title\t: " + movie.getTitle());
		System.out.println("Seats\t\t: " + seats);
		System.out.println("Amount\t\t: $" + transaction.getAmount());
	}
	
	public void printMovieGoerBookings(MovieGoer movieGoer) {
		if(movieGoer == null) {
			System.out.println("No record found. :(");
			return;
		}
		
		for(Booking booking: movieGoer.getBookings()) {
			printBookingDetail(booking);
		}
	}
	
	public void showBookingsByEmail() {
		String email;
		MovieGoer movieGoer;
		
		System.out.println();
		System.out.print("Enter email: ");
		email = ConsoleReader.readString();
		
		movieGoer = movieGoerDAO.getMovieGoerByEmail(email);
		
		printMovieGoerBookings(movieGoer);		
	}
	
	public void showBookingsByMobileNo() {
		String mobileNo;
		MovieGoer movieGoer;
		
		System.out.println();
		System.out.print("Enter mobile no : ");
		mobileNo = ConsoleReader.readString();
		
		movieGoer = movieGoerDAO.getMovieGoerByMobileNo(mobileNo);
		
		printMovieGoerBookings(movieGoer);	
	}
	
	public void showBookingByRefNo() {
		int bookingRefNo;
		Booking booking;
		
		System.out.println();
		System.out.print("Enter booking ref no : ");
		bookingRefNo = ConsoleReader.readIntInput();
		
		booking = bookingBL.getBooking(bookingRefNo);
		
		if(booking == null) {
			System.out.println("No record found. :(");
			return;
		}
		
		printBookingDetail(booking);
	}
	
	public void show() {
		int choice;
		
		do {
			System.out.println();
			System.out.println("=============================");
			System.out.println("RETRIEVE YOUR BOOKING DETAILS");
			System.out.println("=============================");
			System.out.println("Please enter one field to retrieve your booking details");
			System.out.println("1) Enter Email address");
			System.out.println("2) Enter Mobile No.");
			System.out.println("3) Enter Booking Ref. Number");
			System.out.println("4) Back");
			System.out.print("Please enter your choice : ");
			choice = ConsoleReader.readIntInput();
			
			switch(choice) {
				case 1:
					showBookingsByEmail();
					break;
				case 2:
					showBookingsByMobileNo();
					break;
				case 3:
					showBookingByRefNo();
					break;
			}
		} while(choice < 4);
	}
}
