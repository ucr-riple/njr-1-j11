package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.Cinema;
import com.moblima.model.CinemaClass;
import com.moblima.model.Cineplex;

public interface CinemaDAO {
	
	public void createCinema(Cinema cinema);
	public void updateCinema(Cinema cinema);
	public void deleteCinema(Cinema cinema);
	
	public Cinema getCinema(String cinemaCode);
	public List<Cinema> getCinemas();	
}
