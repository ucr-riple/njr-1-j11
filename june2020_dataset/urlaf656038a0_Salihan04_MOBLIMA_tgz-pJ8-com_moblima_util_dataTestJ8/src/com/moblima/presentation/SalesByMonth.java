package com.moblima.presentation;

import java.util.Date;
import java.util.Scanner;
import java.text.DateFormatSymbols;

import com.moblima.businesslogic.SalesByMonthBL;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;

public class SalesByMonth implements SalesReport {

	private SalesByMonthBL salesBL;
	private float indiTakings;
	private float cineplexTakings;
	private float aggTakings;

	private String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month];
	}

	public SalesByMonth() {
		salesBL = new SalesByMonthBL();
		indiTakings = 0;
		cineplexTakings = 0;
		aggTakings = 0;
	}

	public void show() {

		System.out.println("------------------------------------------------");
		System.out.println("| Sale Revenue Report By Month For Current Year |");
		System.out.println("------------------------------------------------");
		System.out.println();

		for(int month : salesBL.getMonthsOfCurrentYear()) {

			System.out.println("Month");
			System.out.println("=====");
			System.out.println(getMonth(month));

			for(Cineplex cp : salesBL.getCineplexes(month)) {
				System.out.println("Cineplex\t\tMovie");
				System.out.println("--------\t\t-----");
				System.out.print(cp.getCineplexName());
				for(Movie m : salesBL.getMoviesByCineplex(month, cp)) {
					indiTakings = salesBL.getindiTakings(month, m);
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
		SalesByMonth view = new SalesByMonth();
		view.show();
	}
}
