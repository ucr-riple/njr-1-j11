package com.moblima.businesslogic;

import java.util.List;

import com.moblima.dataaccess.MovieTicketDAO;
import com.moblima.dataaccess.MovieTicketDAOImpl;
import com.moblima.model.Booking;
import com.moblima.model.MovieTicket;
import com.moblima.model.ShowTime;
import com.moblima.model.TicketType;

public class MovieTicketBL {
	private MovieTicketDAO movieTicketDAO;

	public MovieTicketBL(){
		movieTicketDAO = MovieTicketDAOImpl.getInstance();
	}
	public MovieTicket createMovieTicket(TicketType ticketType, ShowTime showTime, String seatNo, float price){
		MovieTicket movieTicket = new MovieTicket(ticketType, showTime, seatNo, price);
		movieTicketDAO.createMovieTicket(movieTicket);
		return movieTicket;
	}

	public void updateMovieTicket(int movieTicketID, TicketType ticketType, ShowTime showTime, String seatNo, float price,Booking booking){
		MovieTicket movieTicket = movieTicketDAO.getMovieTicket(movieTicketID);
		movieTicket.setMovieTicketID(movieTicketID);
		movieTicket.setTicketType(ticketType);
		movieTicket.setShowTime(showTime);
		movieTicket.setSeatNo(seatNo);
		movieTicket.setPrice(price);
		movieTicket.setBooking(booking);
		movieTicketDAO.updateMovieTicket(movieTicket);
	}

	public void deleteMovieTicket(int movieTicketID){
		MovieTicket movieTicket = movieTicketDAO.getMovieTicket(movieTicketID);
		movieTicketDAO.deleteMovieTicket(movieTicket);
	}

	public MovieTicket getMovieTicket(int movieTicketID){
		return movieTicketDAO.getMovieTicket(movieTicketID);
	}

	public List<MovieTicket> getMovieTickets(){
		return movieTicketDAO.getMovieTickets();
	}
}
