package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Cinema;
import com.moblima.model.Cineplex;
import com.moblima.model.Movie;
import com.moblima.model.MovieRating;
import com.moblima.model.MovieStatus;
import com.moblima.model.MovieType;

public class MovieDAOImpl implements MovieDAO {
	
	private static MovieDAO movieDAO;
	private SerializeDB serializeDB;
	private List<Movie> movies;
	
	public static MovieDAO getInstance() {
		if(movieDAO == null) {
			movieDAO = new MovieDAOImpl();
		}
		
		return movieDAO;
	}
	
	private MovieDAOImpl() {
		serializeDB = serializeDB.getInstance();
		movies = serializeDB.getMovies();
	}
	
	public void createMovie(Movie movie) {
		int movieID = serializeDB.getMovieID();
		movie.setMovieCode(movieID);
		serializeDB.setMovieID(movieID+1);
		
		movies.add(movie);
		
		serializeDB.saveData();
	}
	
	public void updateMovie(Movie movie) {
		serializeDB.saveData();
	}
			
	public Movie getMovie(int movieCode) {
		for(Movie m: movies) {
			if(m.getMovieCode() == movieCode) {
				return m;
			}
		}
		
		return null;
	}
	
	public void deleteMovie(Movie movie) {
		movies.remove(movie);
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public List<Movie> searchMovies(String search) {
		List<Movie> movie_search_result = new ArrayList<Movie>();
		
		for(Movie movie: movies) {
			if(movie.getTitle().contains(search)) {
				movie_search_result.add(movie);
			}
		}
		
		return movie_search_result;
	}
	
}
