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
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;
import com.moblima.model.PublicHoliday;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;
import com.moblima.model.Staff;
import com.moblima.model.Transaction;
import com.moblima.model.TicketPrice;

public class SerializeDB {
	
	private List<Cineplex> cineplexes;
	private List<Cinema> cinemas;
	private List<Seat> seats;
	private List<Movie> movies;
	private List<ShowTime> showTimes;
	private List<MovieGoer> movieGoers;
	private List<MovieTicket> movieTickets;
	private List<Transaction> transactions;
	private List<Booking> bookings;
	private List<PublicHoliday> publicHolidays;
	private List<TicketPrice> ticketPrices;
	private List<Staff> staffs;
	
	private Integer cineplexID;
	private Integer seatID;
	private Integer movieID;
	private Integer showTimeID;
	private Integer movieGoerID;
	private Integer movieTicketID;
	private Integer bookingID;
	private Integer publicHolidayID;
	private Integer staffID;
	
	private static String fileName = "serializeDB.dat";
	private static SerializeDB instance;
	
	public static SerializeDB getInstance() {
		if(instance == null) {
			instance = new SerializeDB();
		}
		
		return instance;
	}
	
	public void saveData() {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(cineplexes);
			oos.writeObject(cinemas);
			oos.writeObject(seats);
			oos.writeObject(movies);
			oos.writeObject(showTimes);
			oos.writeObject(movieGoers);
			oos.writeObject(movieTickets);
			oos.writeObject(transactions);
			oos.writeObject(bookings);
			oos.writeObject(publicHolidays);
			oos.writeObject(ticketPrices);
			oos.writeObject(staffs);
			
			oos.writeObject(cineplexID);
			oos.writeObject(seatID);
			oos.writeObject(movieID);
			oos.writeObject(showTimeID);
			oos.writeObject(movieGoerID);
			oos.writeObject(movieTicketID);
			oos.writeObject(bookingID);
			oos.writeObject(publicHolidayID);
			oos.writeObject(staffID);
			
			oos.close();			
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadData() {
		File f = new File(fileName);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cineplexes = new ArrayList<Cineplex>();
			cinemas = new ArrayList<Cinema>();
			seats = new ArrayList<Seat>();
			movies = new ArrayList<Movie>();
			showTimes = new ArrayList<ShowTime>();
			movieGoers = new ArrayList<MovieGoer>();
			movieTickets = new ArrayList<MovieTicket>();
			transactions = new ArrayList<Transaction>();
			bookings = new ArrayList<Booking>();
			publicHolidays = new ArrayList<PublicHoliday>();
			ticketPrices = new ArrayList<TicketPrice>();
			staffs = new ArrayList<Staff>();
			
			cineplexID = 1;
			seatID = 1;
			movieID = 1;
			showTimeID = 1;
			movieGoerID = 1;
			movieTicketID = 1;
			bookingID = 1;
			publicHolidayID = 1;
			staffID = 1;
		}
		else {
			try {
				FileInputStream fin = new FileInputStream(fileName);
				if(fin.available() > 0) {
					ObjectInputStream ois = new ObjectInputStream(fin);
					cineplexes = (List<Cineplex>)ois.readObject();
					cinemas = (List<Cinema>)ois.readObject();
					seats = (List<Seat>)ois.readObject();
					movies = (List<Movie>)ois.readObject();
					showTimes = (List<ShowTime>)ois.readObject();
					movieGoers = (List<MovieGoer>)ois.readObject();
					movieTickets = (List<MovieTicket>)ois.readObject();
					transactions = (List<Transaction>)ois.readObject();
					bookings = (List<Booking>)ois.readObject();
					publicHolidays = (List<PublicHoliday>)ois.readObject();
					ticketPrices = (List<TicketPrice>)ois.readObject();
					staffs = (List<Staff>)ois.readObject();
					
					cineplexID = (Integer)ois.readObject();
					seatID = (Integer)ois.readObject();
					movieID = (Integer)ois.readObject();
					showTimeID = (Integer)ois.readObject();
					movieGoerID = (Integer)ois.readObject();
					movieTicketID = (Integer)ois.readObject();
					bookingID = (Integer)ois.readObject();
					publicHolidayID = (Integer)ois.readObject();
					staffID = (Integer)ois.readObject();
					ois.close();
				}
				else {
					cineplexes = new ArrayList<Cineplex>();
					cinemas = new ArrayList<Cinema>();
					seats = new ArrayList<Seat>();
					movies = new ArrayList<Movie>();
					showTimes = new ArrayList<ShowTime>();
					movieGoers = new ArrayList<MovieGoer>();
					movieTickets = new ArrayList<MovieTicket>();
					transactions = new ArrayList<Transaction>();
					bookings = new ArrayList<Booking>();
					publicHolidays = new ArrayList<PublicHoliday>();
					ticketPrices = new ArrayList<TicketPrice>();
					staffs = new ArrayList<Staff>();
					
					cineplexID = 1;
					seatID = 1;
					movieID = 1;
					showTimeID = 1;
					movieGoerID = 1;
					movieTicketID = 1;
					bookingID = 1;
					publicHolidayID = 1;
					staffID = 1;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private SerializeDB() {
		loadData();
	}
	
	public List<Cineplex> getCineplexes() {
		return cineplexes;
	}
	
	public List<Cinema> getCinemas() {
		return cinemas;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public List<ShowTime> getShowTimes() {
		return showTimes;
	}
	
	public List<MovieGoer> getMovieGoers() {
		return movieGoers;
	}
	
	public List<MovieTicket> getMovieTickets() {
		return movieTickets;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}
	
	public List<PublicHoliday> getPublicHolidays() {
		return publicHolidays;
	}
	
	public List<TicketPrice> getTicketPrices(){
		return ticketPrices;
	}
	
	public List<Staff> getStaffs() {
		return staffs;
	}
	
	
	public void setCineplexID(int cineplexID) {
		this.cineplexID = cineplexID;
	}
	
	public int getCineplexID() {
		return cineplexID;
	}
	
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	
	public int getSeatID() {
		return seatID;
	}
	
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	
	public int getMovieID() {
		return movieID;
	}
	
	public void setShowTimeID(int showTimeID) {
		this.showTimeID = showTimeID;
	}
	
	public int getShowTimeID() {
		return showTimeID;
	}
	
	public void setMovieGoerID(int movieGoerID) {
		this.movieGoerID = movieGoerID;
	}
	
	public int getMovieGoerID() {
		return movieGoerID;
	}
	
	public void setMovieTicketID(int movieTicketID) {
		this.movieTicketID = movieTicketID;
	}
	
	public int getMovieTicketID() {
		return movieTicketID;
	}
	
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	
	public int getBookingID() {
		return bookingID;
	}
	
	public void setPublicHolidayID(int publicHolidayID) {
		this.publicHolidayID = publicHolidayID;
	}
	
	public int getPublicHolidayID() {
		return publicHolidayID;
	}
	
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	public int getStaffID() {
		return staffID;
	}
	
}