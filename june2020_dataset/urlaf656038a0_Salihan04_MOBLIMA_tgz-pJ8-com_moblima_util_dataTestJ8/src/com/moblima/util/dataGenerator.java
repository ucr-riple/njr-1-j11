package com.moblima.util;

import com.moblima.dataaccess.*;
import com.moblima.model.*;
import com.moblima.businesslogic.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class dataGenerator {
	private CinemaDAO cinemaDAO;
	private MovieDAO movieDAO;
	private SeatDAO seatDAO;
	private CineplexDAO cineplexDAO;
	private MovieGoerDAO movieGoerDAO;
	private PublicHolidayDAO publicHolidayDAO;
	private ShowTimeDAO showTimeDAO;
	private MovieTicketDAO movieTicketDAO;
	private TicketPriceBL ticketPriceBL;
	private MovieGoerBL movieGoerBL;
	private BookingDAO bookingDAO;
	private StaffBL staffBL;
	private TransactionDAO transactionDAO;
	
	List<Cineplex> cineplexes;
	List<Cinema> cinemas;
	List<Seat> seats;
	List<Movie> movies;
	List<MovieGoer> movieGoers;
	List<ShowTime> showTimes;
	List<PublicHoliday> publicHolidays;
	List<MovieTicket> movieTickets; 
	
	public dataGenerator(){	
		cinemaDAO = CinemaDAOImpl.getInstance();
		movieDAO = MovieDAOImpl.getInstance();
		seatDAO = SeatDAOImpl.getInstance();
		cineplexDAO = CineplexDAOImpl.getInstance();
		movieGoerDAO = MovieGoerDAOImpl.getInstance();
		publicHolidayDAO = PublicHolidayDAOImpl.getInstance();
		showTimeDAO = ShowTimeDAOImpl.getInstance();
		movieTicketDAO = MovieTicketDAOImpl.getInstance();
		ticketPriceBL = new TicketPriceBL();
		bookingDAO = BookingDAOImpl.getInstance();
		movieGoerBL = new MovieGoerBL();
		staffBL = new StaffBL();
		transactionDAO = TransactionDAOImpl.getInstance();
		
		cineplexes = new ArrayList<Cineplex>();
		cinemas = new ArrayList<Cinema>();
		seats = new ArrayList<Seat>();
		movies = new ArrayList<Movie>();
		movieGoers = new ArrayList<MovieGoer>();
		showTimes = new ArrayList<ShowTime>();
		publicHolidays = new ArrayList<PublicHoliday>();
		movieTickets = new ArrayList<MovieTicket>();
	}
	
	public void createCineplexes(){
		staffBL.createStaff("root", "toor");
		Cineplex cineplex1 = new Cineplex("Cathay Cineplex West Mall", "Bukit Batok");
		Cineplex cineplex2 = new Cineplex("Cathay Cineplex JEM","Jurong Gateway Road");
		Cineplex cineplex3 = new Cineplex("Cathay Cineplex AMK Hub","Ang Mo Kio Ave 3");
		Cineplex cineplex4 = new Cineplex("Cathay Cineplex AMK Hub - Platinum Suite","Ang Mo Kio Ave 3");
		cineplexes.add(cineplex1);
		cineplexes.add(cineplex2);
		cineplexes.add(cineplex3);
		cineplexes.add(cineplex4);
		for(Cineplex cineplex: cineplexes){
			cineplexDAO.createCineplex(cineplex);
		}
	}
	
	public void createCinemas(){
		if(cineplexes==null){
			System.out.println("please create cineplexes first.");
			return;
		}
		
		for(int j =0;j<cineplexes.size();j++){
			Cineplex cineplex = cineplexes.get(j);
			
			if(j == cineplexes.size()-1) {
				Cinema cinema = new Cinema("PS" + 1, "Platinum Suite", CinemaClass.PLATINUM_MOVIE_SUITES, cineplex);
				cinemaDAO.createCinema(cinema);
				break;
			}
			
			for(int i=0;i<2;i++){
				Cinema cinema = new Cinema( cineplex.getLocation().substring(0, 2) + (i+1), "Hall " + (i+1) , CinemaClass.NORMAL, cineplex);
				cinemaDAO.createCinema(cinema);
			}
		}
	}
	
	public void createSeats(){
		if(cinemas==null){
			System.out.println("Please create Cinemas first.");
			return;
		}
		for(Cinema cinema: cinemaDAO.getCinemas()){
			SeatType seatType;
			for(int i=0;i<6;i++) {
				for(int j=0;j<8;j++) {
					if(i < 4) {
						seatType = SeatType.SINGLE;
					}
					else {
						seatType = SeatType.COUPLE;
					}
					
					Seat seat = new Seat(j+1, (char)((int)'F'-i)+"", seatType, cinema);
					seatDAO.createSeat(seat);
				}
			}
			
			cinema.setNoOfSeatRow(6);
			cinema.setNoOfSeatColumn(8);
			cinemaDAO.updateCinema(cinema);
		}
	}
	
	public void createMovies(){
		Movie movie1 = new Movie("The Hunger Games: Catching Fire", MovieRating.TBA, MovieType.DIGITAL,true, MovieStatus.COMING_SOON, "English","Jennifer Lawrence, Josh Hutcherson, Liam Hemsworth" , "Francis Lawrence", "21 Nov 2013", "TBA", "THE HUNGER GAMES: CATCHING FIRE begins as Katniss Everdeen has returned home safe after winning the 74th Annual Hunger Games along with fellow tribute Peeta Mellark. Winning means that they must turn around and leave their family and close friends, embarking on a 'Victor's Tour' of the districts. Along the way Katniss senses that a rebellion is simmering, but the Capitol is still very much in control as President Snow prepares the 75th Annual Hunger Games (The Quarter Quell) - a competition that could change Panem forever. The novel on which the film is based is the second in a trilogy that has over 50 million copies in print in the U.S. alone");
		movieDAO.createMovie(movie1);
		Movie movie2 = new Movie("Homefront", MovieRating.TBA, MovieType.DIGITAL,false, MovieStatus.COMING_SOON, "English with Chinese Subtitles", "Jason Statham, James Franco, Winona Ryder, Kate Bosworth", "Gary Fleder", "5 Dec 2013", "TBA", "HOMEFRONT stars Jason Statham as former drug enforcement agent, Phil Broker, a family man who moves off the grid with his daughter, to a seemingly quiet bayou backwater to escape his troubled past. However, BrokerÕs world soon becomes anything but quiet once he discovers that an underbelly of drugs and violence riddles the small town. Soon, a sociopathic methamphetamine kingpin, Gator Bodine (James Franco) puts Broker and his daughter in harmÕs way forcing Broker back into action in order to save his family and the town.");
		movieDAO.createMovie(movie2);
		Movie movie3 = new Movie("Ender's Game", MovieRating.PG, MovieType.DIGITAL,true, MovieStatus.NOW_SHOWING,"English with Subtitles", "Asa Butterfield, Harrison Ford, Ben Kingsley, Hailee Steinfeld, Viola Davis, Abigail Breslin", "Gavin Hood", "07 Nov 2013", "114 mins", "In the near future, a hostile alien race called the Formics have attacked Earth. If not for the legendary heroics of International Fleet Commander Mazer Rackham, all would have been lost. In preparation for the next attack, the highly esteemed Colonel Hyrum Graff and the International Military are training only the best young minds to find the future Mazer. Ender Wiggin, a shy but strategically brilliant boy, is recruited to join the elite. Arriving at Battle School, Ender quickly and easily masters increasingly difficult challenges and simulations, distinguishing himself and winning respect amongst his peers. Ender is soon ordained by Graff as the military's next great hope, resulting in his promotion to Command School. Once there, he's trained by Mazer Rackham himself to lead his fellow soldiers into an epic battle that will determine the future of Earth and save the human race.");
		movieDAO.createMovie(movie3);
		Movie movie4 = new Movie("Baby Blues", MovieRating.PG13, MovieType.DIGITAL,false, MovieStatus.NOW_SHOWING, "Mandarin with Chinese and English subtitles", "Mandarin with Chinese and English subtitles", "Leong Po-Chih", "07 Nov 2013", "92 mins", "When a young couple, blogger Tian Qing and song-writer Hao moves into a home, they find a mysterious doll that changes their lives forever. Not long after, Tian Qing becomes pregnant with twins but as Hao is always busy with his work, he comes home late every night. When Tian Qing suffers a fall and ends up giving birth to only one child, she begins to show signs of depression and weird behaviour..");
		movieDAO.createMovie(movie4);
		Movie movie5 = new Movie("Marvels Thor The Dark World(Digital) ", MovieRating.PG13, MovieType.DIGITAL,true, MovieStatus.NOW_SHOWING, "English with Chinese Subtitles", "Chris Hemsworth, Natalie Portman, Anthony Hopkins", "Alan Taylor", "31 Oct 2013", "112 mins", "When Jane Foster is targeted by the denizens of the dark world of Svartalfheim, Thor sets out on a quest to protect her at all costs.");
		movieDAO.createMovie(movie5);
		Movie movie6 = new Movie("Marvels Thor The Dark World(3D) ", MovieRating.PG13, MovieType._3D,false, MovieStatus.NOW_SHOWING, "English with Chinese Subtitles", "Chris Hemsworth, Natalie Portman, Anthony Hopkins", "Alan Taylor", "31 Oct 2013", "112 mins", "When Jane Foster is targeted by the denizens of the dark world of Svartalfheim, Thor sets out on a quest to protect her at all costs.");
		movieDAO.createMovie(movie6);
		
		movies.add(movie1);
		movies.add(movie2);
		movies.add(movie3);
		movies.add(movie4);
		movies.add(movie5);
		movies.add(movie6);
	}
	
	public void createMovieGoers(){
		int yr,mth,day;
		Calendar calendar = Calendar.getInstance();
		
		day = 1;
		mth = 2;
		yr = 1988;
		calendar.set(yr,mth,day,00,00,00);
		Date date = calendar.getTime();
		MovieGoer movieGoer1 = new MovieGoer("John", "98712354", "johnny_boy@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer1);
		
		day = 2;
		mth = 2;
		yr = 1990;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieGoer movieGoer2 = new MovieGoer("Jane", "99711234", "jane_girl@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer2);
		
		day = 3;
		mth = 3;
		yr = 1991;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieGoer movieGoer3 = new MovieGoer("Max", "96547776", "max_hot@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer3);
		
		day = 4;
		mth = 5;
		yr = 1996;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieGoer movieGoer4 = new MovieGoer("lynners", "98234554", "Awhat_miracle@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer4);
		
		day = 20;
		mth = 5;
		yr = 1992;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieGoer movieGoer5 = new MovieGoer("linus", "98234554", "whatsap-_miracle@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer5);
		
		day = 12;
		mth = 12;
		yr = 1953;
		calendar.set(yr,mth,day,00,00,00);
		date = calendar.getTime();
		MovieGoer movieGoer6 = new MovieGoer("syndicate", "98234554", "boners_miracle@hotmail.com",date);
		movieGoerDAO.createMovieGoer(movieGoer6);
		
	}
	
	public void createShowTimes(){
		int mth,day,hr;
		/////////////////////////////////////1st day////////////////////////////////////
		mth = 11;
		day= 24;
		hr = 8;
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		
		
		Cineplex cineplex = cineplexDAO.getCineplexes().get(0);//1st cineplex
//		mth = 11;
//		day=13;
//		hr = 8;
		
		Cinema cinema = cineplex.getCinemas().get(0);//for 1st cinema
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new ShowTime(cinema, movies.get(0), date);
			showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new ShowTime(cinema, movies.get(1), date);
			showTimeDAO.createShowTime(showTime);
		}
		
		//////////////////////////////////////2nd cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(1);//2nd cineplex
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new ShowTime(cinema, movies.get(2), date);
			showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
			calendar.set(2013,mth,day,hr,00,00);
			date = calendar.getTime();
			ShowTime showTime = new ShowTime(cinema, movies.get(3), date);
			showTimeDAO.createShowTime(showTime);
		}
		////////////////////////////////////
		//3rd cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(2);//2nd cineplex
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(4), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(5), date);
		showTimeDAO.createShowTime(showTime);
		}
		//////////////////////////////////////4th cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(3);
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(0), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		/////////////////////////////////////2nd day////////////////////////////////////
		mth = 11;
		day=25;
		hr = 8;
		
		
		cineplex = cineplexDAO.getCineplexes().get(0);//1st cineplex
		
		cinema = cineplex.getCinemas().get(0);//for 1st cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(0), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(1), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		//////////////////////////////////////2nd cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(1);//2nd cineplex
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(2), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(3), date);
		showTimeDAO.createShowTime(showTime);
		}
		////////////////////////////////////
		//3rd cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(2);//2nd cineplex
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(4), date);
		showTimeDAO.createShowTime(showTime);
		}
		
		cinema = cineplex.getCinemas().get(1);//for 2nd cinema
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(5), date);
		showTimeDAO.createShowTime(showTime);
		}
		//////////////////////////////////////4th cineplex////////////////////////////
		cineplex = cineplexDAO.getCineplexes().get(3);
		
		cinema = cineplex.getCinemas().get(0);
		for(hr=8;hr<22;hr+=3){
		calendar.set(2013,mth,day,hr,00,00);
		date = calendar.getTime();
		ShowTime showTime = new ShowTime(cinema, movies.get(4), date);
		showTimeDAO.createShowTime(showTime);
		}
	}
	
	
	public void createBookingMovieTickets(){
		int ticketID =1;
		int bookingID=1;
		int mth = 1, days=23, yr=2013, hr=12;
		List<MovieTicket> movieTickets = new ArrayList<MovieTicket>();
		
		//1st booking
		MovieGoer movieGoer = movieGoerDAO.getMovieGoerByID(1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013,mth,days,hr,00,00);
		Date date = calendar.getTime();
		TicketType ticketType = movieGoerBL.getTicketType(movieGoer);
		ShowTime showTime = showTimeDAO.getShowTime(1);
		String seatNo = "A01";
		TicketPrice ticketPrice = new TicketPrice();
		float price = ticketPriceBL.getPrice(showTime, movieGoer);
		MovieTicket movieTicket = new MovieTicket(ticketType, showTime, seatNo,price);
		movieTickets.add(movieTicket);
		movieTicketDAO.createMovieTicket(movieTicket);
		ticketID++;
		//make booking
		Booking booking = new Booking(BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		
		//second booking
		bookingID++;
		mth =1;
		days = 12;
		yr = 2013;
		hr = 17;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(2);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(1);
		seatNo = "A03";
		price =ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his grandpa ticket)
		ticketType = TicketType.SENIOR;
		seatNo = "A04";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		//third booking
		bookingID++;
		mth =2;
		days = 1;
		yr = 2013;
		hr = 8;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(3);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(2);
		seatNo = "B03";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his friend ticket)
		ticketType = TicketType.STUDENT;
		seatNo = "C04";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		
		//4th booking
		bookingID++;
		mth =12;
		days = 26;
		yr = 2012;
		hr = 8;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(4);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(12);
		seatNo = "B03";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his grandpa ticket)
		ticketType = TicketType.SENIOR;
		seatNo = "B04";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his grandpa ticket)
		ticketType = TicketType.SENIOR;
		seatNo = "B05";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking( BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		//5th booking
		bookingID++;
		mth =2;
		days = 1;
		yr = 2013;
		hr = 8;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(5);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(12);
		seatNo = "C06";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
		//6th booking
		bookingID++;
		mth =1;
		days = 12;
		yr = 2013;
		hr = 17;
		calendar.set(2013,mth,days,hr,00,00);
		date = calendar.getTime();
		//1st movieGoer (his own ticket)
		movieGoer = movieGoerDAO.getMovieGoerByID(6);
		ticketType = movieGoerBL.getTicketType(movieGoer);
		showTime = showTimeDAO.getShowTime(1);
		seatNo = "C03";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//(his grandpa ticket)
		ticketType = TicketType.NORMAL;
		seatNo = "C04";
		price = ticketPriceBL.getPrice(showTime, movieGoer);
		movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		movieTickets.add(movieTicket);
		ticketID++;
		//make booking
		booking = new Booking(BookingStatus.PENDING, date, null,movieGoer, movieTickets);
		bookingDAO.createBooking(booking);
		movieTickets = new ArrayList<MovieTicket>();
		
	}
	
	public void createTransactions(){
		int TID=0;
		float total=0;
		int mth=10,days=13,hr=8;
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013,mth,days,hr,00,00);
		Date date = calendar.getTime();
//		List<TransactionMethod> method = new ArrayList<TransactionMethod>(); 
//		method.add(TransactionMethod.CUSTOM);
//		method.add(TransactionMethod.MASTER);
//		method.add(TransactionMethod.PAYPAL);
//		method.add(TransactionMethod.VISA);
		
		for(Booking booking: bookingDAO.getBookings()){
			calendar.set(2013,mth,days,hr,00,00);
			date = calendar.getTime();
			for(MovieTicket movieTicket: booking.getMovieTickets()){
				total+=movieTicket.getPrice();
			}
			Transaction transaction = new Transaction(TID + "",total ,date, TransactionMethod.CUSTOM);
			transactionDAO.createTransaction(transaction);
			booking.setTransaction(transaction);
			booking.setStatus(BookingStatus.ACCEPTED);
			bookingDAO.updateBooking(booking);
			TID++;
			total=0;
			hr+=2;
		}
		
	}
	
	
//	public void createPublicHolidays(){
//		SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy H:mm");
//		Date date = null;
//		try {
//			date = sdf.parse(3 + "/" + 1+  "/" + 2013 + " " + "00:00");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		PublicHoliday publicHoliday1 = new PublicHoliday("Happy kiddy day", date);
//		publicHolidays.add(publicHoliday1);
//		publicHolidayDAO.createPublicHoliday(publicHoliday1);
//	}
	
//	public List<Cineplex> getCineplexes(){
//		return cineplexes;
//		
//	}
//	
//	public List<Cinema> getCinemas(){
//		return cinemas;
//	}
	
	public List<Movie> getMovies(){
		return movies;
	}
	
	
	
	
	
	
}
