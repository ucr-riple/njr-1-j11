package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.Cineplex;

public interface CineplexDAO {
	
	public void createCineplex(Cineplex cineplex);
	public void updateCineplex(Cineplex cineplex);
	public void deleteCineplex(Cineplex cineplex);
	
	public Cineplex getCineplex(int cineplexCode);
	public List<Cineplex> getCineplexes();
}
