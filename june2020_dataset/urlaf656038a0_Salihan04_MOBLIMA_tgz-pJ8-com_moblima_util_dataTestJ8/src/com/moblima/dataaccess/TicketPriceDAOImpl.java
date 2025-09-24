package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.ShowTime;
import com.moblima.model.TicketPrice;

public class TicketPriceDAOImpl implements TicketPriceDAO {
	
	private static TicketPriceDAO ticketPriceDAO;
	private SerializeDB serializeDB;
	private List<TicketPrice> ticketPrices;
	
	public static TicketPriceDAO getInstance() {
		if(ticketPriceDAO == null) {
			ticketPriceDAO = new TicketPriceDAOImpl();
		}
		
		return ticketPriceDAO;
	}
	
	private TicketPriceDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		ticketPrices = serializeDB.getTicketPrices();
		
		if(ticketPrices.size()==0){
			TicketPrice generalTicketPrice = new TicketPrice();
			TicketPrice _3DTicketPrice = new TicketPrice();
			
			generalTicketPrice.setPlatinumTicketPricing(28);
			generalTicketPrice.setStudentPricing(7);
			generalTicketPrice.setSeniorCitizenPricing(4);
			generalTicketPrice.set_MON_WED_pricing(8.5f);
			generalTicketPrice.set_THU_pricing(8.5f);
			generalTicketPrice.set_FRI_EVE_PH_before_6_pricing(9.5f);
			generalTicketPrice.set_FRI_EVE_PH_from_6_pricing(11);
			generalTicketPrice.set_SAT_SUN_PH_pricing(11);
			
			_3DTicketPrice.setPlatinumTicketPricing(28);
			_3DTicketPrice.setStudentPricing(8);
			_3DTicketPrice.setSeniorCitizenPricing(8);
			_3DTicketPrice.set_MON_WED_pricing(10f);
			_3DTicketPrice.set_THU_pricing(10f);
			_3DTicketPrice.set_FRI_EVE_PH_before_6_pricing(13);
			_3DTicketPrice.set_FRI_EVE_PH_from_6_pricing(13);
			_3DTicketPrice.set_SAT_SUN_PH_pricing(13);
			
			ticketPrices.add(generalTicketPrice);
			ticketPrices.add(_3DTicketPrice);
			serializeDB.saveData();
		}
	}

	public void createTicketPrice(TicketPrice ticketPrice, int i) {
		this.ticketPrices.set(i, ticketPrice);
		serializeDB.saveData();
	}

	public void updateTicketPrice(TicketPrice ticketPrice, int i) {
		this.ticketPrices.set(i,ticketPrice);
		serializeDB.saveData();
	}
	
	public List<TicketPrice> getTicketPrices(){
		return ticketPrices;
	}
	
	public TicketPrice getTicketPrice(int i) {
		return this.ticketPrices.get(i);
	}
	
}
