package com.moblima.businesslogic;

import java.util.List;

import com.moblima.dataaccess.CineplexDAO;
import com.moblima.dataaccess.CineplexDAOImpl;
import com.moblima.model.Cineplex;

public class CineplexBL {
	
	private CineplexDAO cineplexDAO;
	
	public CineplexBL() {
		cineplexDAO = CineplexDAOImpl.getInstance();
	}
	
	public void createCineplex(String cineplexName, String location) {
		Cineplex cineplex = new Cineplex(cineplexName, location);
		cineplexDAO.createCineplex(cineplex);
	}
	
	public void updateCineplex(Cineplex cineplex) {
		cineplexDAO.updateCineplex(cineplex);
	}
	
	public void removeCineplex(int cineplexCode) {
		Cineplex cineplex = cineplexDAO.getCineplex(cineplexCode);
		cineplexDAO.deleteCineplex(cineplex);
	}
	
	public List<Cineplex> getCineplexes() {
		return cineplexDAO.getCineplexes();
	}
	
	public Cineplex getCineplex(int cineplexCode) {
		return cineplexDAO.getCineplex(cineplexCode);
	}
}