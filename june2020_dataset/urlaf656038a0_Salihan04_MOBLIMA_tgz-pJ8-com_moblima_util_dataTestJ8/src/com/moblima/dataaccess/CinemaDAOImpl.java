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
import com.moblima.model.CinemaClass;
import com.moblima.model.Cineplex;


public class CinemaDAOImpl implements CinemaDAO {
	
	private static CinemaDAO cinemaDAO;
	private SerializeDB serializeDB;
	private List<Cinema> cinemas;
	
	public static CinemaDAO getInstance() {
		if(cinemaDAO == null){
			cinemaDAO = new CinemaDAOImpl();
		}
		
		return cinemaDAO;
	}
	
	private CinemaDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		cinemas = serializeDB.getCinemas();
	}
	
	public void createCinema(Cinema cinema) {
		cinemas.add(cinema);
		
		//Update cineplex
		Cineplex cineplex = cinema.getCineplex();
		cineplex.addCinema(cinema);
		
		serializeDB.saveData();//need to save other way. this is not efficient.
	}
	
	public void updateCinema(Cinema cinema) {
		serializeDB.saveData();
	}
	
	public void deleteCinema(Cinema cinema) {
		cinemas.remove(cinema);
		
		Cineplex cineplex = cinema.getCineplex();
		cineplex.removeCinema(cinema);
		
		serializeDB.saveData();
	}
	
	public Cinema getCinema(String cinemaCode) {
		for(Cinema c: cinemas) {
			if(c.getCinemaCode().equals(cinemaCode)) {
				return c;
			}
		}
		
		return null;
	}
	
	public List<Cinema> getCinemas() {
		return cinemas;
	}
	
}
