package com.moblima.presentation;

import java.util.ArrayList;
import java.util.List;

import com.moblima.businesslogic.CineplexBL;
import com.moblima.businesslogic.SalesByCineplexBL;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;

public class SalesByCineplex implements SalesReport {
	
	private CineplexBL cineplexBL;
	private SalesByCineplexBL salesBL;
	private List<Movie> moviesByCineplex;
	private float indiTakings;
	private float aggTakings;
	
	public SalesByCineplex() {
		cineplexBL = new CineplexBL();
		salesBL = new SalesByCineplexBL();
		moviesByCineplex = new ArrayList<Movie>();
		indiTakings = 0;
		aggTakings = 0;
	}

	public void show() {
		
		System.out.println("-----------------------------------------------------");
		System.out.println("| Sale Revenue Report By Cineplex For Current Month |");
		System.out.println("-----------------------------------------------------");
		System.out.println();

		for(Cineplex cineplex : cineplexBL.getCineplexes()) {
			
			System.out.println("Cineplex: " + cineplex.getCineplexCode() + ", " + cineplex.getCineplexName() + ", " + cineplex.getLocation());
			System.out.println("===================================================================================================");
			System.out.println();
			
			System.out.println("Movie Code\t\tMovie Title\t\t\tIndividual Takings($)");
			System.out.println("----------\t\t-----------\t\t\t---------------------");
			
			moviesByCineplex = salesBL.moviesByCineplex(cineplex);
			
			for(Movie movie : moviesByCineplex) {
				indiTakings = salesBL.getIndiTakings(movie);
				System.out.println(movie.getMovieCode() + "\t\t\t" + movie.getTitle() + "\t\t" + Math.round(indiTakings*100.0)/100.0);
				aggTakings += indiTakings;
				indiTakings = 0;
			}
			System.out.println();

			System.out.print("Aggregated Takings: $" + Math.round(aggTakings * 100.0)/100.0);
			aggTakings = 0;
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		SalesByCineplex view = new SalesByCineplex();
		view.show();
	}
}
