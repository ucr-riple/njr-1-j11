package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Booking;
import com.moblima.model.MovieTicket;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;

public class MovieTicketDAOImpl implements MovieTicketDAO {
	
	private static MovieTicketDAO movieTicketDAO;
	private SerializeDB serializeDB;
	private List<MovieTicket> movieTickets;
	
	public static MovieTicketDAO getInstance() {
		if(movieTicketDAO == null) {
			movieTicketDAO = new MovieTicketDAOImpl();
		}
		return movieTicketDAO;
	}
	
	private MovieTicketDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		movieTickets = serializeDB.getMovieTickets();
	}
	
	public void createMovieTicket(MovieTicket movieTicket) {
		int movieTicketID = serializeDB.getMovieTicketID();
		movieTicket.setMovieTicketID(movieTicketID);
		serializeDB.setMovieTicketID(movieTicketID+1);
		
		movieTickets.add(movieTicket);
		
		//Update showTime
		ShowTime showTime = movieTicket.getShowTime();
		showTime.addMovieTicket(movieTicket);
		
		serializeDB.saveData();
	}

	public void updateMovieTicket(MovieTicket movieTicket) {
		serializeDB.saveData();
	}

	public void deleteMovieTicket(MovieTicket movieTicket) {
		movieTickets.remove(movieTicket);
		serializeDB.saveData();
	}

	public MovieTicket getMovieTicket(int movieTicketID) {
		for(MovieTicket movieTicket: movieTickets) {
			if(movieTicket.getMovieTicketID() == movieTicketID)
				return movieTicket;
		}
		return null;
	}
	
	public List<MovieTicket> getMovieTickets() {
		return movieTickets;
	}
	
}
