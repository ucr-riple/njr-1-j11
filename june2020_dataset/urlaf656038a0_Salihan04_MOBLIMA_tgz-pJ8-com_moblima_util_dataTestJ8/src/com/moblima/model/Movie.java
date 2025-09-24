package com.moblima.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {

	private int movieCode;
	private String title;
	private MovieRating rating;
	private MovieType type;
	private MovieStatus status;
	private String language;
	private String cast;
	private String director;
	private String opening;
	private String runtime;
	private String details;
	private List<ShowTime> showTimes;
	private Boolean blockBuster;

	public Movie(String title, MovieRating rating, MovieType type, Boolean blockBuster, MovieStatus status, String language, String cast, String director, String opening, String runtime, String details) {
		this.movieCode = -1;
		this.title = title;
		this.rating = rating;
		this.type = type;
		this.blockBuster = blockBuster;
		this.status = status;
		this.language = language;
		this.cast = cast;
		this.director = director;
		this.opening = opening;
		this.runtime = runtime;
		this.details = details;
		showTimes = new ArrayList<ShowTime>();
	}
	
	public boolean isBlockBuster(){
		return blockBuster;
	}
	
	public void setBlockBuster(Boolean blockBuster){
		this.blockBuster = blockBuster;
	}

	public void setMovieCode(int movieCode) {
		this.movieCode = movieCode;
	}
	
	public int getMovieCode() {
		return movieCode;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setRating(MovieRating rating) {
		this.rating = rating;
	}

	public MovieRating getRating() {
		return rating;
	}
	
	public void setType(MovieType type) {
		this.type = type;
	}
	
	public MovieType getType() {
		return type;
	}

	public void setStatus(MovieStatus status) {
		this.status = status;
	}

	public MovieStatus getStatus() {
		return status;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setCast(String cast) {
		this.cast = cast;
	}
	
	public String getCast() {
		return cast;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setOpening(String opening) {
		this.opening = opening;
	}
	
	public String getOpening() {
		return opening;
	}
	
	public void setRunTime(String runtime) {
		this.runtime = runtime;
	}
	
	public String getRunTime() {
		return runtime;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}
	
	public void addShowTime(ShowTime showTime) {
		showTimes.add(showTime);
	}
	
	public void removeShowTime(ShowTime showTime) {
		showTimes.remove(showTime);
	}
	
	public void setShowTimes(List<ShowTime> showTimes) {
		this.showTimes = showTimes;
	}
	
	public List<ShowTime> getShowTimes() {
		return showTimes;
	}
	
	public String toString() {
		return title;
	}
	
	public boolean equals(Object o) {
		Movie movie = (Movie)o;
		return movieCode == movie.getMovieCode();
	}
}