package com.moblima.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.moblima.businesslogic.StaffBL;
import com.moblima.dataaccess.CinemaDAO;
import com.moblima.dataaccess.CinemaDAOImpl;
import com.moblima.dataaccess.CineplexDAO;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.dataaccess.MovieDAO;
import com.moblima.dataaccess.MovieDAOImpl;
import com.moblima.dataaccess.SeatDAO;
import com.moblima.dataaccess.SeatDAOImpl;
import com.moblima.dataaccess.ShowTimeDAO;
import com.moblima.dataaccess.ShowTimeDAOImpl;
import com.moblima.dataaccess.StaffDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.CinemaClass;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType;
import com.moblima.model.Seat;
import com.moblima.model.SeatType;
import com.moblima.model.ShowTime;

public class DBInitializer {

	public static void main(String[] args) {
		StaffBL staffBL = new StaffBL();
		staffBL.createStaff("root", "toor");
		
		Cineplex cineplex = new Cineplex("Orchard", "ION Orchard Road");
		Cinema cinema = new Cinema("O01", "Hall 1", CinemaClass.NORMAL, cineplex);
		
		Movie _TheHungerGame = new Movie("The Hunger Games: Catching Fire", MovieRating.PG13, MovieType.DIGITAL, false,MovieStatus.NOW_SHOWING, "English", "Jennifer Lawrence, Josh Hutcherson, Liam Hemsworth", "Francis Lawrence", "21 Nov 2013", "TBA", "THE HUNGER GAMES: CATCHING FIRE begins as Katniss Everdeen has returned home safe after winning the 74th Annual Hunger Games along with fellow tribute Peeta Mellark. Winning means that they must turn around and leave their family and close friends, embarking on a \"Victor\'s Tour\" of the districts. Along the way Katniss senses that a rebellion is simmering, but the Capitol is still very much in control as President Snow prepares the 75th Annual Hunger Games (The Quarter Quell) - a competition that could change Panem forever. The novel on which the film is based is the second in a trilogy that has over 50 million copies in print in the U.S. alone.");
		Movie _Thor = new Movie("Marvels Thor The Dark World (3D)", MovieRating.PG13, MovieType._3D, false ,MovieStatus.NOW_SHOWING, "English with Chinese Subtitles", "Chris Hemsworth, Natalie Portman, Anthony Hopkins", "Alan Taylor", "31 Oct 2013", "112 mins", "When Jane Foster is targeted by the denizens of the dark world of Svartalfheim, Thor sets out on a quest to protect her at all costs.");
		
		MovieDAO movieDAO = MovieDAOImpl.getInstance();
		movieDAO.createMovie(_Thor);
		movieDAO.createMovie(_TheHungerGame);
		
		CineplexDAO cineplexDAO = CineplexDAOImpl.getInstance();
		cineplexDAO.createCineplex(cineplex);

		CinemaDAO cinemaDAO = CinemaDAOImpl.getInstance();
		cinemaDAO.createCinema(cinema);
		
		SeatDAO seatDAO = SeatDAOImpl.getInstance();
		int seatID = 0;
		for(int i=0;i<6;i++) {
			for(int j=0;j<8;j++) {
				SeatType seatType;
				if(i<4) {
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
		
		//creating showtimes
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = null;
		try {
			date = sdf.parse("9/11/2013 13:30");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ShowTime showTime = new ShowTime(cinema, _TheHungerGame, date);
		ShowTimeDAO showTimeDAO = ShowTimeDAOImpl.getInstance();
		showTimeDAO.createShowTime(showTime);	
		
		try {
			date = sdf.parse("9/12/2013 13:30");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		showTime = new ShowTime(cinema, _Thor, date);
		showTimeDAO.createShowTime(showTime);
	
		try {
			date = sdf.parse("12/11/2013 14:45");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showTime = new ShowTime(cinema, _Thor, date);
		showTimeDAO.createShowTime(showTime);
		
//		try {
//			date = sdf.parse("1/12/2013 1:45");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		showTime = new ShowTime(4, cinema, _TheHungerGame, date);
//		showTimeDAO.createShowTime(showTime);
	}

}
