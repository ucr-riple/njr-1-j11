package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.MovieGoer;


public class MovieGoerDAOImpl implements MovieGoerDAO {
	
	private static MovieGoerDAO movieGoerDAO;
	private SerializeDB serializeDB;
	private List<MovieGoer> movieGoers;
	
	public static MovieGoerDAO getInstance() {
		if(movieGoerDAO == null) {
			movieGoerDAO = new MovieGoerDAOImpl();
		}
		
		return movieGoerDAO;
	}
		
	private MovieGoerDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		movieGoers = serializeDB.getMovieGoers();
	}
	
	public void createMovieGoer(MovieGoer movieGoer) {
		int movieGoerID = serializeDB.getMovieGoerID();
		movieGoer.setMovieGoerID(movieGoerID);
		serializeDB.setMovieGoerID(movieGoerID+1);
		
		movieGoers.add(movieGoer);
		
		serializeDB.saveData();//need to save other way. this is not efficient.
	}
	
	public void updateMovieGoer(MovieGoer movieGoer) {
		serializeDB.saveData();
	}
	
	public MovieGoer getMovieGoerByID(int movieGoerID) {
		for(MovieGoer m: movieGoers) {
			if(m.getMovieGoerID()==movieGoerID) {
				return m;
			}
		}
		
		return null;
	}
	
	public MovieGoer getMovieGoerByMobileNo(String mobileNo) {
		for(MovieGoer m: movieGoers) {
			if(m.getMobileNo().equals(mobileNo)) {
				return m;
			}
		}
		
		return null;
	}
	
	public MovieGoer getMovieGoerByEmail(String email) {
		for(MovieGoer m: movieGoers) {
			if(m.getEmail().equals(email)) {
				return m;
			}
		}
		
		return null;
	}
	
	public List<MovieGoer> getMovieGoers() {
		return movieGoers;
	}

	public void deleteMovieGoer(MovieGoer movieGoer) {
		movieGoers.remove(movieGoer);
		return;
	}

}
