package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.MovieTicket;
import com.moblima.model.Seat;
import com.moblima.model.ShowTime;

public interface MovieTicketDAO {

	public void createMovieTicket(MovieTicket movieTicket);
	public void updateMovieTicket(MovieTicket movieTicket);
	public void deleteMovieTicket(MovieTicket movieTicket);
	public MovieTicket getMovieTicket(int movieTicketID);
	
	public List<MovieTicket> getMovieTickets();
	
}
