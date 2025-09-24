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

import com.moblima.businesslogic.CinemaBL;
import com.moblima.businesslogic.CineplexBL;
import com.moblima.businesslogic.MovieBL;
import com.moblima.businesslogic.ShowTimeBL;
import com.moblima.dataaccess.CinemaDAOImpl;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.dataaccess.MovieDAO;
import com.moblima.dataaccess.MovieDAOImpl;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.ShowTime;
import com.moblima.util.ConsoleReader;

public class ManageShowTimes_CLI {
	private CineplexBL cineplexBL;
	private CinemaBL cinemaBL;
	private MovieBL movieBL;
	private ShowTimeBL showTimeBL;

	private List<ShowTime> _showTimes;

	public ManageShowTimes_CLI() {
		cineplexBL = new CineplexBL();
		movieBL = new MovieBL();
		cinemaBL = new CinemaBL();
		showTimeBL = new ShowTimeBL();
	}

	public void listShowTimes() {
		int index = 0;
		_showTimes = new ArrayList<ShowTime>(); // To be used for update and remove showTime

		System.out.println("ShowTimes");
		System.out.println("=========");

		for(Cineplex cineplex: cineplexBL.getCineplexes()) {
			List<Movie> movies = cineplex.getMovies();

			if(movies.size() == 0) {
				continue;
			}

			System.out.println(cineplex.getCineplexName());
			System.out.println("-----------------------------------");

			for(Movie movie: movies) {				
				List<ShowTime> showTimes = showTimeBL.getShowTimes(movie, cineplex);

				if(showTimes.size() == 0) {
					continue;
				}

				System.out.println(movie.getTitle());
				System.out.println("********************************");

				Hashtable<String, List<ShowTime>> showTimeHashTable = new Hashtable<String, List<ShowTime>>();
				//Preprocessing for showTime to follow the cathay format
				for(ShowTime st: showTimes) {
					String d = new SimpleDateFormat("d MMM, E").format(st.getTime());

					if(showTimeHashTable.get(d) == null) {
						showTimeHashTable.put(d, new ArrayList<ShowTime>());
					}

					showTimeHashTable.get(d).add(st);
				}

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

	public void createShowTimes() {
		int cineplexCode;
		String cinemaCode;
		int movieCode;
		String showTimeDate;
		String showTimesString;

		System.out.println("Create ShowTimes");
		System.out.println("================");
		System.out.println("CINEPLEX CODE\tCINEPLEX NAME");
		System.out.println("..................................");
		for(Cineplex cineplex: cineplexBL.getCineplexes()) {
			System.out.println(cineplex.getCineplexCode() + "\t\t" + cineplex.getCineplexName());
		}

		System.out.println();
		System.out.print("Please enter CINEPLEX CODE: ");
		cineplexCode = ConsoleReader.readIntInput();

		Cineplex cineplex = cineplexBL.getCineplex(cineplexCode);
		if(cineplex == null){
			System.out.println("Cineplex not found!");
			return;
		}

		System.out.println();
		System.out.println(cineplex.getCineplexName());
		System.out.println(".................");
		System.out.println("CINEMA CODE\tCINEMA NAME");
		for(Cinema cinema: cineplex.getCinemas()) {
			System.out.println(cinema.getCinemaCode()+"\t\t"+cinema.getCinemaName());
		}

		System.out.println();
		System.out.print("Please enter CINEMA CODE: ");
		cinemaCode = ConsoleReader.readString();

		Cinema cinema = cinemaBL.getCinema(cinemaCode);
		if(cinema == null){
			System.out.println("Cinema not found!");
			return;
		}

		System.out.println();
		System.out.println("Movies");
		System.out.println("..................");
		System.out.println("MOVIE CODE\tMOVIE TITLE");
		for(Movie movie: movieBL.getNowShowingMovies()) {
			System.out.println(movie.getMovieCode() + "\t\t" + movie.getTitle());
		}

		System.out.println();
		System.out.print("Please enter MOVIE CODE: ");
		movieCode = ConsoleReader.readIntInput();

		Movie movie = movieBL.getMovie(movieCode);
		if(movie == null){
			System.out.println("Error movie does not exist!");
			return;
		}

		System.out.println();
		System.out.print("Please enter date of the ShowTime (DD/MM/YYYY): ");
		showTimeDate = ConsoleReader.readDateString(); //FIX ME PLEASE!!!

		System.out.println();
		System.out.print("Please enter time (HH:mm HH:mm ... ): ");
		showTimesString = ConsoleReader.readTimeInput();

		ShowTime showTime;
		for(String time: showTimesString.split(" ")) {
			SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy HH:mm");
			try {
				Date date = sdf.parse(showTimeDate + " " + time);

				showTimeBL.createShowTime(cinema, movie, date);				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void manageShowTimes() {
		int index;
		int choice;
		int hour, minute;
		String time;
		ShowTime showTime;

		listShowTimes();

		System.out.print("Please select showtime (the number between \'[ ]\') : ");
		index = ConsoleReader.readIntInput();
		if(index<=0){
			System.out.println("Show Time is invalid!");
			return;
		}

		showTime = _showTimes.get(index-1); //This showTimes is construted when we list the showTimes. Not from the BL.

		//update and delete
		System.out.println("1) Update ShowTime");
		System.out.println("2) Delete ShowTime");
		System.out.println("3) Back");

		System.out.print("Please enter your choice: ");
		choice = ConsoleReader.readIntInput();
		
		switch(choice) {
		case 1:
			System.out.print("Please enter time (HH:mm): ");
			time = ConsoleReader.readTimeInput();

			showTimeBL.updateShowTime(showTime, time);
			break;

		case 2:
			showTimeBL.deleteShowTime(showTime);
			break;

		default:
			break;
		}
	}

	public void showMenu() {
		int choice;

		do{
			System.out.println();
			System.out.println("ShowTimes Management");
			System.out.println("====================");
			System.out.println("1) List ShowTimes");
			System.out.println("2) Create ShowTimes");
			System.out.println("3) Manage ShowTimes");
			System.out.println("4) Back");

			System.out.print("Please enter your choice: ");
			choice = ConsoleReader.readIntInput();
			System.out.println();

			switch(choice) {
			case 1:
				listShowTimes();
				System.out.println();
				break;

			case 2:
				createShowTimes();
				System.out.println();
				break;

			case 3:
				manageShowTimes();
				System.out.println();
				break;

			default:
				break;
			}
		}while(choice != 4);
	}
	
	public static void main(String[] args) {
		ManageShowTimes_CLI cli = new ManageShowTimes_CLI();
		cli.showMenu();
	}

}
