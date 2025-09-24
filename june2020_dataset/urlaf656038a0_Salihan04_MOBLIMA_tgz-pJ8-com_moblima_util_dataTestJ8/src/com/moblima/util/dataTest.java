package com.moblima.util;

import java.util.List;

public class dataTest {

	public static void main(String[] args){
		
		System.out.println("Please wait.....1");
		dataGenerator dg = new dataGenerator();
		System.out.println("Please wait.....2");
		dg.createMovies();
		System.out.println("Please wait.....3");
		dg.createCineplexes();
		System.out.println("Please wait.....4");
		dg.createCinemas();
		System.out.println("Please wait.....5");
		dg.createSeats();
		System.out.println("Please wait.....6");
		dg.createShowTimes();
		System.out.println("Please wait.....7");
//		dg.createMovieGoers();
//		System.out.println("Please wait.....8");
//		dg.createBookingMovieTickets();
//		System.out.println("Please wait.....9");
//		dg.createTransactions();
		System.out.println("Done!");
		
	}
}
