package com.moblima.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Cineplex implements Serializable {

	private Integer cineplexCode;
	private String cineplexName;
	private String location;
	private List<Cinema> cinemas;
 
	public Cineplex(String cineplexName, String location) {
		this.cineplexCode = -1;
		this.cineplexName = cineplexName;
		this.location = location;
		cinemas = new ArrayList<Cinema>();
	}
	
	public void setCineplexCode(int cineplexCode) {
		this.cineplexCode = cineplexCode;
	}
	
	public int getCineplexCode() {
		return cineplexCode;
	}
	
	public void setCineplexName(String cineplexName) {
		this.cineplexName = cineplexName;
	}
	
	public String getCineplexName() {
		return cineplexName;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void addCinema(Cinema cinema) {
		cinemas.add(cinema);
	}
	
	public void removeCinema(Cinema cinema) {
		cinemas.remove(cinema);
	}
	
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	
	public List<Cinema> getCinemas() {
		return cinemas;
	}
	
	public List<Cinema> getCinemas(Movie movie) {
		List<Cinema> cinema_result = new ArrayList<Cinema>();
		
		for(Cinema c: cinemas) {
			if(c.getShowTimes(movie)!=null) {
				cinema_result.add(c);
			}
		}
		
		return cinema_result;
	}
	
	public List<Movie> getMovies() {
		List<Movie> movie_result = new ArrayList<Movie>();
		
		for(Cinema cinema: cinemas) {
			for(ShowTime st: cinema.getShowTimes()) {
				if(!movie_result.contains(st.getMovie())) {
					movie_result.add(st.getMovie());
				}
			}
		}
		
		return movie_result;
	}
	
}