package com.moblima.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import com.moblima.businesslogic.BookingBL;
import com.moblima.businesslogic.CineplexBL;
import com.moblima.businesslogic.MovieBL;
import com.moblima.businesslogic.MovieGoerBL;
import com.moblima.businesslogic.SeatBL;
import com.moblima.businesslogic.ShowTimeBL;
import com.moblima.businesslogic.TicketPriceBL;
import com.moblima.dataaccess.BookingDAO;
import com.moblima.dataaccess.BookingDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieGoer;
import com.moblima.model.MovieTicket;
import com.moblima.model.Seat;
import com.moblima.model.SeatType;
import com.moblima.model.ShowTime;
import com.moblima.model.Transaction;
import com.moblima.model.TransactionMethod;
import com.moblima.util.ConsoleReader;

public class SearchListMovieAndCreateBooking_CLI {

	private BookingBL bookingBL;
	private MovieBL movieBL;
	private CineplexBL cineplexBL;
	private MovieGoerBL movieGoerBL;
	private TicketPriceBL ticketPriceBL;
	private ShowTimeBL showTimeBL;
	private SeatBL seatBL;
	
	private List<ShowTime> _showTimes;
	
	public SearchListMovieAndCreateBooking_CLI() {
		bookingBL = new BookingBL();
		movieBL = new MovieBL();
		cineplexBL = new CineplexBL();
		movieGoerBL = new MovieGoerBL();
		ticketPriceBL = new TicketPriceBL();
		showTimeBL = new ShowTimeBL();
		seatBL = new SeatBL();
	}
	
	public void listMovies(List<Movie> movies) {
		System.out.println();
		System.out.println("MOVIE CODE\tMOVIE TITLE");
		System.out.println("=======================================================");
		
		for(Movie movie: movies) {
			System.out.println(movie.getMovieCode() + "\t\t" + movie.getTitle());
		}
		
		booking();
	}
	
	public void searchMovie() {
		boolean tryAgain;
		String searchQuery;
		
		do {
			tryAgain = false;
			
			System.out.println();
			System.out.print("Enter Your Search Query (-1 to go back) : ");
			searchQuery = ConsoleReader.readString();
			
			if(searchQuery.equals("-1")) {
				return;
			}
			
			List<Movie> movies = movieBL.searchMovies(searchQuery);
			
			if(movies.size() == 0) {
				System.out.println("No search results.");
				tryAgain = true;
			}
			else {
				listMovies(movies);
			}			
		} while(tryAgain);
	}
	
	public void show() {
		int choice;
		boolean _continue;
		
		do {
			_continue = true;
			
			System.out.println();
			System.out.println("=================");
			System.out.println("Search/List Movie");
			System.out.println("=================");
			System.out.println("1) Search Movie");
			System.out.println("2) List Movie");
			System.out.println("3) Back");
			System.out.print("Please enter your choice : ");
			choice = ConsoleReader.readIntInput();
	
			switch(choice) {
				case 1:
					searchMovie();
					break;
				case 2:
					listMovies(movieBL.getNowShowingMovies());
					break;
				case 3:
					_continue = false;
					return;
			}
		}while(_continue);
	}
	
	public void booking() {
		int movieCode;
		int showTime_index;
		String seats;
		Movie movie;
		ShowTime showTime = null;
		MovieGoer movieGoer;
		
		boolean tryAgain;
		do {
			tryAgain = false;
			System.out.println();
			System.out.print("Please enter movie code (-1 to go back) : ");
			movieCode = ConsoleReader.readIntInput();
			
			if(movieCode == -1) {
				return;
			}
			
			movie = movieBL.getMovie(movieCode);
			if(movie == null) {
				tryAgain = true;
				System.out.println("Invalid Movie Code. Try again.");
				System.out.println();
			}		
		} while(tryAgain);
		
		printMovieInfo(movie); // Print Movie Details and all the ShowTimes
		
		do {
			tryAgain = false;
			System.out.print("Please select showtime (the number between \'[ ]\') (-1 to go back) : ");
			showTime_index = ConsoleReader.readIntInput();
			
			if(showTime_index == -1) {
				return;
			}
			
			if(showTime_index < 1 || showTime_index > _showTimes.size()) {
				tryAgain = true;
				System.out.println("Invalid number. Try again.");
			}
			else {			
				showTime = _showTimes.get(showTime_index-1); //This showTimes is construted when we list the showTimes. Not from the BL.
				
				if(showTimeBL.isFullyBooked(showTime)) {
					System.out.println();
					System.out.println("===========================================================================");
					System.out.println("| We\'re sorry, the selected session is sold out. Please pick another one! |");
					System.out.println("===========================================================================");
					System.out.println();
					tryAgain = true;
					continue;
				}
				
				printSeats(showTime);
			}
		} while(tryAgain);
		
		boolean isValidSeatNos;
		do {
			System.out.print("Please enter seat no. (e.g. G1 G2 ...) : ");
			seats = ConsoleReader.readSeatNumbers();
			String[] _seats = seats.split(" ");
			isValidSeatNos = seatBL.validateSeatNumbers(showTime, _seats);
			
			if(!isValidSeatNos) {
				System.out.println("Invalid seat numbers. Please choose again.");
			}
			else if(_seats.length > 10) {
				System.out.println("You cannot book more than 10 tickets per transaction. Please select seats again.");
				isValidSeatNos = false;
			}
			System.out.println();
		} while(!isValidSeatNos);
		
		//Ask for movieGoer info
		String movieGoerName;
		String movieGoerMobileNo;
		String movieGoerEmail;
		Date dateOfBirth = null;
		
		System.out.println();
		System.out.println("Please Enter Your Details");
		System.out.println("=========================");
		
		System.out.print("Name : ");
		movieGoerName = ConsoleReader.readString();
		
		System.out.print("Date of Birth (DD/MM/YYYY) : ");
		dateOfBirth = ConsoleReader.readDateInput();
		
		System.out.print("Email : ");
		movieGoerEmail = ConsoleReader.readString();
		
		System.out.print("Mobile No : ");
		movieGoerMobileNo = ConsoleReader.readString();
		
		movieGoer = movieGoerBL.createMovieGoer(movieGoerName, movieGoerEmail, movieGoerMobileNo, dateOfBirth);
		
		printBookingSummary(showTime, movieGoer, seats);
	}
	
	public void printBookingSummary(ShowTime showTime, MovieGoer movieGoer, String seats) {
		Booking booking;
		Cinema cinema = showTime.getCinema();
		Cineplex cineplex = cinema.getCineplex();
		Movie movie = showTime.getMovie();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		int no_of_tickets = seats.split(" ").length;
		float grand_total = (float)(no_of_tickets * ticketPriceBL.getPrice(showTime, movieGoer) + 1.5);
		int choice;
		
		grand_total = (float)(grand_total * 1.07);
		
		System.out.println();
		System.out.println("YOUR BOOKING SUMMARY");
		System.out.println("------------------------------------");
		System.out.println("Cineplex\t: " + cineplex.getCineplexName());
		System.out.println("Title\t\t: " + movie.getTitle());
		System.out.println("Date\t\t: " +  dateFormat.format(showTime.getTime()));
		System.out.println("Time\t\t: " + timeFormat.format(showTime.getTime()));
		System.out.println("Rating\t\t: " + movie.getRating());
		System.out.println("Duration\t: " + movie.getRunTime());
		System.out.println("Seats\t\t: " + seats);
		System.out.println("No. of tickets\t: " + no_of_tickets);
		System.out.println("Price\t\t: $" + no_of_tickets * ticketPriceBL.getPrice(showTime, movieGoer));
		System.out.println("Booking Fee\t: $1.50");
		System.out.println("Grand Total (GST 7% Inclusive) : $" + grand_total);
		
		System.out.println();
		System.out.println("1) CONFIRM BOOKING");
		System.out.println("2) CANCEL");
		System.out.print("Please enter your choice : ");
		choice = ConsoleReader.readIntInput();
		
		switch(choice) {
			case 1:
				Payment_CLI payment_cli = new Payment_CLI();
				
				if(payment_cli.show(grand_total)) { // Payment is successful
					booking = bookingBL.createBooking(movieGoer, showTime, TransactionMethod.CUSTOM, seats.split(" "));
				
					printBookingPurchaseInvoice(booking);
				}
				break;
		}
	}
	
	public void printBookingPurchaseInvoice(Booking booking) {
		int no_of_tickets = booking.getMovieTickets().size();
		MovieTicket movieTicket = booking.getMovieTickets().get(0);
		ShowTime showTime = movieTicket.getShowTime();
		MovieGoer movieGoer = booking.getMovieGoer();
		Transaction transaction = booking.getTransaction();
		float ticket_price = ticketPriceBL.getPrice(showTime, movieGoer);
		float total = no_of_tickets * ticket_price;
		float gst = (float)((total + 1.5) * 0.07);
		float grand_total = (float)((total + 1.5) * 1.07);
		
		System.out.println();
		System.out.println("\tBOOKING PURCHASE INVOICE");
		System.out.println("\t========================");
		System.out.println();
		System.out.println("Booking Ref No : " + booking.getBookingID());
		System.out.println("TID            : " + transaction.getTID());
		System.out.println();
		System.out.println("Description\t\tQty\tAmount");
		System.out.println("--------------------------------------");
		System.out.println(movieTicket.getTicketType() + " Ticket ($" + ticket_price + ")\t" + no_of_tickets + "\t$" + total);
		System.out.println("Online Booking Fee\t\t$" + 1.50);
		System.out.println("\t\t\t\t------");
		System.out.println("\t\t\tGST 7%:\t$" + gst);
		System.out.println("\t\t\tTotal :\t$" + grand_total);	
	}
	
	public void printMovieInfo(Movie movie) {
		int index = 0;
		_showTimes = new ArrayList<ShowTime>();
		
		System.out.println();
		System.out.println(movie.getTitle());
		System.out.println("=================================");
		System.out.println("AVAILABLE IN\t: " + movie.getType());
		System.out.println("CAST\t\t: " + movie.getCast());
		System.out.println("DIRECTOR\t: " + movie.getDirector());
		System.out.println("LANGUAGE\t: " + movie.getLanguage());
		System.out.println("OPENING\t\t: " + movie.getOpening());
		System.out.println("RUNTIME\t\t: " + movie.getRunTime());
		System.out.println("RATING\t\t: " + movie.getRating());
		System.out.println("Description\t: " + movie.getDetails());	
		
		// Printing movie showtimes
		System.out.println();
		System.out.println("SHOWTIMES");
		System.out.println("=========");
		
		for(Cineplex cineplex: cineplexBL.getCineplexes()) {
			List<Cinema> cinemas = cineplex.getCinemas(movie);
			
			if(cinemas == null) {
				continue;
			}
			
			System.out.println(cineplex.getCineplexName());
			System.out.println("..................................");
			
			// Go through every cinemas
			for(Cinema cinema: cinemas) {
				List<ShowTime> showTimes = cinema.getShowTimes(movie);
				
				if(showTimes == null) {
					continue;
				}
					
				Hashtable<String, List<ShowTime>> showTimeHashTable = new Hashtable<String, List<ShowTime>>();
				//Preprocessing for showTime to follow the cathay format
				SimpleDateFormat sdf = new SimpleDateFormat("d MMM, E");
				for(ShowTime st: showTimes) {
					String d = sdf.format(st.getTime());
												
					if(showTimeHashTable.get(d) == null) {
						showTimeHashTable.put(d, new ArrayList<ShowTime>());
					}
					
					showTimeHashTable.get(d).add(st);
				}
									
				SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
				
				for(Enumeration<String> e = showTimeHashTable.keys();e.hasMoreElements();) {
					String dateString = e.nextElement();
				
					System.out.print(dateString);
					
					for(ShowTime showTime: showTimeHashTable.get(dateString)) {
						// showTime can be accessed using the index. It is in the sequence it is printed.
						// index starts at zero even though the index printed out start at 1. -1 before accessing.
						_showTimes.add(showTime); 
												
						String time = new SimpleDateFormat("HH:mm").format(showTime.getTime());
						System.out.print("\t[" + (index+1) + "] " + time);
						
						index++;
					}
					
					System.out.println();
				}
				System.out.println();
			}
		}
	}
		
	public void printSeats(ShowTime showTime) {
		Cinema cinema = showTime.getCinema();
		
		System.out.println();
		System.out.println("            SCREEN                  ");
		System.out.println(" -------------------------------  ");
		System.out.print(" ");
		for(int i=1;i<=cinema.getNoOfSeatColumn();i++) {
			System.out.print("  " + i);
			
			if(i == cinema.getNoOfSeatColumn()/2) {
				System.out.print("     ");
			}
		}
		System.out.println();
		System.out.println(" -------------------------------  ");
		String prev = "";
		int seat_column_count = 0;
		for(Seat seat: cinema.getSeats()) { 
			if(!prev.equals(seat.getSeatRow())) {
				seat_column_count = 0;
				
				if(!prev.equals("")){
					System.out.println(" " + prev);
					System.out.println(" -------------------------------  ");
				}
				System.out.print(seat.getSeatRow() + " ");
			}
			
			if(seat.isOccupiedAt(showTime)) {
				if(seat.getSeatType() == SeatType.COUPLE && seat.getSeatNo()%2 == 1) {
					System.out.print("[X ");
				}
				else if(seat.getSeatType() == SeatType.COUPLE && seat.getSeatNo()%2 == 0) {
					System.out.print(" X]");
				}
				else {
					System.out.print("[X]");
				}
				
			}
			else {
				if(seat.getSeatType() == SeatType.COUPLE && seat.getSeatNo()%2 == 1) {
					System.out.print("[  ");
				}
				else if(seat.getSeatType() == SeatType.COUPLE && seat.getSeatNo()%2 == 0) {
					System.out.print("  ]");
				}
				else {
					System.out.print("[ ]");
				}
			}
			
			seat_column_count++;
			
			if(seat_column_count == cinema.getNoOfSeatColumn()/2) {
				System.out.print("     ");
			}

			prev = seat.getSeatRow();
		}
		
		System.out.println(" " + prev);
		System.out.println(" -------------------------------  ");
		System.out.print(" ");
		for(int i=1;i<=cinema.getNoOfSeatColumn();i++) {
			System.out.print("  " + i);
			
			if(i == cinema.getNoOfSeatColumn()/2) {
				System.out.print("     ");
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("====================================");
		System.out.println("|              LEGEND              |");
		System.out.println("====================================");
		System.out.println("|  [ ] - Seat Available            |");
		System.out.println("|  [X] - Seat Taken                |");
		System.out.println("|  [     ] - Couple Seat Available |");
		System.out.println("|  [X   X] - Couple Seat Taken     |");
		System.out.println("====================================");
	}
		
	public static void main(String[] args) {
		SearchListMovieAndCreateBooking_CLI cli = new SearchListMovieAndCreateBooking_CLI();
		cli.show();
//		BookingDAO bookingDAO = BookingDAOImpl.getInstance();
//		
//		cli.printBookingPurchaseInvoice(bookingDAO.getBooking(0));
	}

}
