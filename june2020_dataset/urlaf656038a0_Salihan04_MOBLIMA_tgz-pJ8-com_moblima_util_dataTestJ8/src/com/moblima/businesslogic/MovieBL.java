package com.moblima.businesslogic;


import java.util.ArrayList;
import java.util.List;

import com.moblima.dataaccess.MovieDAO;
import com.moblima.dataaccess.MovieDAOImpl;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType;

public class MovieBL {
	private MovieDAO movieDAO;
	
	public MovieBL(){
		movieDAO = MovieDAOImpl.getInstance();
	}
	
	public void createMovie(String title, MovieRating rating, MovieType type, Boolean blockBuster, MovieStatus status, String language, String cast, String director, String opening, String runtime, String details){
		Movie movie = new Movie(title, rating, type, blockBuster, status, language, cast, director, opening, runtime, details);
		movieDAO.createMovie(movie);
	}
	
	public void updateMovie(int movieCode, String title, MovieRating rating, MovieType type, Boolean blockBuster, MovieStatus status, String language, String cast, String director, String opening, String runtime, String details){
		Movie movie = movieDAO.getMovie(movieCode);
		movie.setTitle(title);
		movie.setRating(rating);
		movie.setType(type);
		movie.setBlockBuster(blockBuster);
		movie.setStatus(status);
		movie.setLanguage(language);
		movie.setCast(cast);
		movie.setDirector(director);
		movie.setOpening(opening);
		movie.setRunTime(runtime);
		movie.setDetails(details);
		
		movieDAO.updateMovie(movie);
	}

	// removeMovie do not really delete movie. Just change the status to END_OF_SHOW
	public void removeMovie(int movieCode){
		Movie movie = movieDAO.getMovie(movieCode);
		movie.setStatus(MovieStatus.END_OF_SHOWING);
		movieDAO.updateMovie(movie);
	}

	public Movie getMovie(int movieCode){
		return movieDAO.getMovie(movieCode);
	}

	public List<Movie> getMovies(){
		return movieDAO.getMovies();
	}
	
	public List<Movie> searchMovies(String searchQuery) {
		return movieDAO.searchMovies(searchQuery);
	}
	
	public List<Movie> getNowShowingMovies() {
		List<Movie> movies_result = new ArrayList<Movie>();
		
		for(Movie movie: movieDAO.getMovies()) {
			if(movie.getStatus() == MovieStatus.NOW_SHOWING) {
				movies_result.add(movie);
			}
		}
		
		return movies_result;
	}
}
