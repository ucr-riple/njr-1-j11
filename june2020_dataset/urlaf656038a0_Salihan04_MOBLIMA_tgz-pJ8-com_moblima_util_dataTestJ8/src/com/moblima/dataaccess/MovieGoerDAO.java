package com.moblima.dataaccess;

import com.moblima.model.MovieGoer;

import java.util.List;

public interface MovieGoerDAO {
	
	public void createMovieGoer(MovieGoer movieGoer);
	public void updateMovieGoer(MovieGoer movieGoer);
	
	public MovieGoer getMovieGoerByID(int movieGoerID);
	public MovieGoer getMovieGoerByMobileNo(String mobileNo);
	public MovieGoer getMovieGoerByEmail(String email);
	public List<MovieGoer> getMovieGoers();
	
}
