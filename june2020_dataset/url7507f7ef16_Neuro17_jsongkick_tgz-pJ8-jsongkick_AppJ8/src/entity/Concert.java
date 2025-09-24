package entity;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Concert {
	private DateTime dateTime; 
	private LocalDate date;
	private ArrayList<Artist> performance;
	private Venue venue;
	private String id;
	private double popularity;
	private SimpleLocation location;
		
	public Concert(DateTime dateTime, LocalDate date, ArrayList<Artist> performance, Venue venue, String id, double popularity, SimpleLocation location) {
		this.dateTime = dateTime;
		this.date = date;
		this.performance = performance;
		this.venue = venue;
		this.id = id;
		this.popularity = popularity;
		this.location = location;
	}

	public Concert() {

	}

	public DateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ArrayList<Artist> getPerformance() {
		return performance;
	}
	
	public void setPerformance(ArrayList<Artist> performance) {
		this.performance = performance;
	}
	
	public Venue getVenue() {
		return venue;
	}
	
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public double getPopularity() {
		return popularity;
	}
	
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	
	public SimpleLocation getLocation() {
		return location;
	}
	
	public void setLocation(SimpleLocation location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Concert [dateTime=" + dateTime + ", date=" + date
				+ ", performance=" + performance + ", venue=" + venue + ", id="
				+ id + ", popularity=" + popularity + ", location=" + location
				+ "]";
	}

}
		