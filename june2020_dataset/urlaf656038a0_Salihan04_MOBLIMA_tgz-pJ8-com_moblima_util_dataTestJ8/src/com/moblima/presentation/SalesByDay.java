package com.moblima.presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import com.moblima.businesslogic.SalesByDayBL;
import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.ShowTime;
import com.moblima.model.Transaction;

public class SalesByDay implements SalesReport {

	private SalesByDayBL salesBL;
	private float indiTakings;
	private float cineplexTakings;
	private float aggTakings;

	public SalesByDay() {
		salesBL = new SalesByDayBL();
		indiTakings = 0;
		cineplexTakings = 0;
		aggTakings = 0;
	}

	public void show() {

		System.out.println("------------------------------------------------");
		System.out.println("| Sale Revenue Report By Day For Current Month |");
		System.out.println("------------------------------------------------");
		System.out.println();

		for(Date date : salesBL.getDaysOfCurrentMonth()) {

			System.out.println("Date");
			System.out.println("====");
			System.out.println(date);
			System.out.println();

			for(Cineplex cp : salesBL.getCineplexes(date)) {
				List<Movie> movies = salesBL.getMoviesByCineplex(cp);
				
				if(movies.size() == 0) {
					continue;
				}
				
				System.out.println("Cineplex\t\tMovie");
				System.out.println("--------\t\t-----");
				System.out.print(cp.getCineplexName());
				
				for(Movie m : movies) {
					indiTakings = salesBL.getindiTakings(date, m);
					System.out.println("\t\t\t" + m.getTitle());
					
					cineplexTakings += indiTakings;
				}
				System.out.println();
			}
			System.out.println("Individual takings: $" + Math.round(cineplexTakings*100.0)/100.0);
			aggTakings += cineplexTakings;
			cineplexTakings = 0.0f;
			indiTakings = 0.0f;
			System.out.println();		
		}
		System.out.println("________________________________________________________________");
		System.out.println("Aggregate takings: $" + Math.round(aggTakings*100.0)/100.0);
		
		aggTakings = 0.0f;
	}

	public static void main(String[] args) {
		SalesByDay view = new SalesByDay();
		view.show();
	}
}
