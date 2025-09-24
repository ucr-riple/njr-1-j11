package com.moblima.presentation;

import java.util.ArrayList;
import java.util.List;

import com.moblima.businesslogic.MovieBL;
import com.moblima.businesslogic.SalesByMovieBL;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;

public class SalesByMovie implements SalesReport{

	private SalesByMovieBL salesBL;
	private List<Movie> moviesInCurrentMonth;
	private List<Cineplex> cineplexesByMovie;
	private float indiTakings;
	private float aggTakings;

	public SalesByMovie() {
		salesBL = new SalesByMovieBL();
		moviesInCurrentMonth = new ArrayList<Movie>();
		cineplexesByMovie = new ArrayList<Cineplex>();
		indiTakings = 0;
		aggTakings = 0;
	}

	public void show() {

		System.out.println("--------------------------------------------------");
		System.out.println("| Sale Revenue Report By Movie For Current Month |");
		System.out.println("--------------------------------------------------");
		System.out.println();

		for(Movie movie : salesBL.moviesInCurrentMonth()) {

			System.out.println("Movie Code, Movie Title: " + movie.getMovieCode() + ", " + movie.getTitle());
			System.out.println("===========================================================================");
			System.out.println();

			System.out.println("Cineplex Code\t\tCinema Name\t\tCineplex Location\t\tIndividual Takings($)");
			System.out.println("-------------\t\t-----------\t\t-----------------\t\t---------------------");

			cineplexesByMovie = salesBL.cineplexesByMovie(movie);
			
			for(Cineplex cineplex : cineplexesByMovie) {
				indiTakings = salesBL.getIndiTakings(cineplex, movie);
				System.out.println(cineplex.getCineplexCode() + "\t\t\t" + cineplex.getCineplexName() + "\t\t\t" + cineplex.getLocation() + "\t\t" + Math.round(indiTakings*100.0)/100.0);
				aggTakings += indiTakings;
				indiTakings = 0;
			}
			System.out.println();
			
			System.out.println("Aggregated Takings: $" + Math.round(aggTakings * 100.0)/100.0);
			System.out.println();
			aggTakings = 0;
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		SalesByMovie view = new SalesByMovie();
		view.show();
	}
}
