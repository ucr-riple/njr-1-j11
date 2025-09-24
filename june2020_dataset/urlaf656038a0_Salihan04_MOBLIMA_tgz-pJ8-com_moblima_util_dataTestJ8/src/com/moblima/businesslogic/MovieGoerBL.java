package com.moblima.businesslogic;

import java.util.Date;

import com.moblima.dataaccess.MovieGoerDAO;
import com.moblima.dataaccess.MovieGoerDAOImpl;
import com.moblima.model.MovieGoer;
import com.moblima.model.TicketType;

public class MovieGoerBL {
	
	private MovieGoerDAO movieGoerDAO;
	
	public MovieGoerBL(){
		movieGoerDAO = MovieGoerDAOImpl.getInstance();
	}

	public MovieGoer createMovieGoer(String name, String email, String mobileNo, Date dateOfBirth) {
		// this is for duplicates record. If the movieGoer already has an account, then do not create a new one
		MovieGoer movieGoer = movieGoerDAO.getMovieGoerByMobileNo(mobileNo);
		
		if(movieGoer == null) {
			movieGoer = new MovieGoer(name, mobileNo, email, dateOfBirth);
			movieGoerDAO.createMovieGoer(movieGoer);
		}			
		
		return movieGoer;
	}
	
	public void updateMovieGoer(String name,String email,String mobileNo,int movieGoerID){
		MovieGoer movieGoer = movieGoerDAO.getMovieGoerByID(movieGoerID);
		movieGoer.setName(name);
		movieGoer.setEmail(email);
		movieGoer.setMobileNo(mobileNo);
		movieGoerDAO.updateMovieGoer(movieGoer);
	}
	
	public TicketType getTicketType(MovieGoer movieGoer){
		if(movieGoer.getAge()<18){
			return TicketType.STUDENT;
		}else if(movieGoer.getAge()<40){
			return TicketType.NORMAL;
		}else{
			return TicketType.SENIOR;
		}
	}
	
	public MovieGoer getMovieGoerByID(int movieGoerID) {
		return movieGoerDAO.getMovieGoerByID(movieGoerID);
	}
	
	public MovieGoer getMovieGoerByMobile(String mobileNo) {
		return movieGoerDAO.getMovieGoerByMobileNo(mobileNo);
	}
	
	public MovieGoer getMovieGoerByEmail(String email) {
		return null;
	}
	
}
