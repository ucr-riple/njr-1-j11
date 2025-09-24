package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType;

public interface MovieDAO {
		
	public void createMovie(Movie movie);
	public void updateMovie(Movie movie);
	public void deleteMovie(Movie movie);
	
	public Movie getMovie(int movieCode);
	public List<Movie> getMovies();
	public List<Movie> searchMovies(String search);
	
}
