package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.*;

public interface TicketPriceDAO {
	public void createTicketPrice(TicketPrice ticketPriceint, int i);
	public void updateTicketPrice(TicketPrice ticketPrice,int i);
	
	public TicketPrice getTicketPrice(int i);
	public List<TicketPrice> getTicketPrices();
}
