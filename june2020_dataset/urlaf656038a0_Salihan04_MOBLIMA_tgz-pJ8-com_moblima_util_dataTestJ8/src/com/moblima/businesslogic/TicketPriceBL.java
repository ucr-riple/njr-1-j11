package com.moblima.businesslogic;

import java.util.Calendar;
import java.util.Date;

import com.moblima.model.MovieGoer;
import com.moblima.model.MovieType;
import com.moblima.model.PublicHoliday;
import com.moblima.model.ShowTime;
import com.moblima.model.TicketPrice;
import com.moblima.model.TicketType;
import com.moblima.model.CinemaClass;
import com.moblima.dataaccess.*;

public class TicketPriceBL {
	
	private TicketPrice generalTicketPrice;
	private TicketPrice _3DTicketPrice;
	private TicketPriceDAO ticketPriceDAO;
	private PublicHolidayDAO publicHolidayDAO;
	
	public TicketPriceBL() {
		ticketPriceDAO = TicketPriceDAOImpl.getInstance();
		publicHolidayDAO = PublicHolidayDAOImpl.getInstance();
		generalTicketPrice = ticketPriceDAO.getTicketPrice(0);
		_3DTicketPrice = ticketPriceDAO.getTicketPrice(1);
	}
	
	public float getPrice(ShowTime showTime, MovieGoer movieGoer){
		Calendar calendar = Calendar.getInstance();
		Date phDate;
		Date showTimeDate =  showTime.getTime();
		int day = showTime.getTime().getDay();
		int time = showTime.getTime().getHours();
		int age = movieGoer.getAge();
		float amount=0;
		
		if(showTime.getCinema().getCinemaClass() == CinemaClass.PLATINUM_MOVIE_SUITES) {
			return generalTicketPrice.getPlatinumTicketPricing();
		}
		
		if(showTime.getMovie().isBlockBuster()){
			amount+=1;
		}
		for(PublicHoliday ph: publicHolidayDAO.getPublicHolidays()){
			phDate = ph.getDate();
			
			calendar.setTime(showTimeDate);
			long st = calendar.getTimeInMillis();
			
			calendar.setTime(phDate);
			long pt = calendar.getTimeInMillis();
			
			calendar.setTime(phDate);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR, 0);
			calendar.add(Calendar.HOUR, -6);
			long start = calendar.getTimeInMillis();

			calendar.setTime(phDate);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.HOUR, 23);
			long end = calendar.getTimeInMillis();
			
			if(st>start&&st<end){
				if(st>pt){
					if(showTime.getMovie().getType()==MovieType._3D){
						amount+= _3DTicketPrice.get_SAT_SUN_PH_pricing();
					}else{
						amount+= generalTicketPrice.get_SAT_SUN_PH_pricing();
					}
				}else{
					if(showTime.getMovie().getType()==MovieType._3D){
						amount+= _3DTicketPrice.get_FRI_EVE_PH_from_6_price();
					}else{
						amount+= generalTicketPrice.get_FRI_EVE_PH_from_6_price();
					}
				}
				return amount;
			}
		}
		
		
		TicketType type;
		MovieType movieType = showTime.getMovie().getType();
		CinemaClass cinemaClass = showTime.getCinema().getCinemaClass();
		if(age<18){
			type = TicketType.STUDENT;
		}else if(age<40){
			type = TicketType.NORMAL;
		}else{
			type = TicketType.SENIOR;
		}
		
		if(cinemaClass==CinemaClass.PLATINUM_MOVIE_SUITES&&movieType==MovieType.DIGITAL){
			amount+= generalTicketPrice.getPlatinumTicketPricing();
		}else if(cinemaClass==CinemaClass.PLATINUM_MOVIE_SUITES&&movieType==MovieType._3D){
			amount+= _3DTicketPrice.getPlatinumTicketPricing();
		}else if(cinemaClass==CinemaClass.NORMAL){//for normal cinemas
			if(movieType==MovieType._3D){
				if(type ==TicketType.NORMAL || type==TicketType.SENIOR){
					if(day>0&&day<4){//mon-wed
						amount+= _3DTicketPrice.get_MON_WED_pricing();
						
					}else if(day==4){//thur
						amount+= _3DTicketPrice.get_THU_pricing();

					}else if(day==5){//fri
						if(time<18){//before 6
							amount+= _3DTicketPrice.get_FRI_EVE_PH_before_6_pricing();
						}else{//after 6
							amount+= _3DTicketPrice.get_FRI_EVE_PH_from_6_price();
						}
					}else if(day==6||day==0){//sat or sun
						amount+= _3DTicketPrice.get_SAT_SUN_PH_pricing();
					}
				}else if(type==TicketType.STUDENT){
					if(day>0&&day<4){//mon-wed
						if(time<18){
							amount+= _3DTicketPrice.getStudentPricing();
						}else{
							amount+= _3DTicketPrice.get_MON_WED_pricing();
						}
					}else if(day==4){
						if(time<18){
							amount+= _3DTicketPrice.getStudentPricing();
						}else{
							amount+= _3DTicketPrice.get_THU_pricing();
						}
					}else if(day==5){//fri
						if(time<18){//before 6
							amount+= _3DTicketPrice.getStudentPricing();
						}else{//after 6
							amount+= _3DTicketPrice.get_FRI_EVE_PH_from_6_price();
						}
					}else if(day==6||day==0){
						amount+= _3DTicketPrice.get_SAT_SUN_PH_pricing();
					}
				}
		}else if(movieType==MovieType.DIGITAL){
			if(type ==TicketType.NORMAL){
				if(day>0&&day<4){//mon-wed
					amount+= generalTicketPrice.get_MON_WED_pricing();
				}else if(day==4){//thur
					amount+= generalTicketPrice.get_THU_pricing();
				}else if(day==5){//fri
					if(time<18){//before 6
						amount+= generalTicketPrice.get_FRI_EVE_PH_before_6_pricing();
					}else{//after 6
						amount+= generalTicketPrice.get_FRI_EVE_PH_from_6_price();
					}
				}else if(day==6||day==0){
					amount+= generalTicketPrice.get_SAT_SUN_PH_pricing();
				}
			}else if(type==TicketType.STUDENT){
				if(day>0&&day<4){//mon-wed
					if(time<18){
						amount+= generalTicketPrice.getStudentPricing();
					}else{
						amount+= generalTicketPrice.get_MON_WED_pricing();
					}
				}else if(day==4){
					if(time<18){
						amount+= generalTicketPrice.getStudentPricing();
					}else{
						amount+= generalTicketPrice.get_THU_pricing();
					}
				}else if(day==5){//fri
					if(time<18){//before 6
						amount+= generalTicketPrice.getStudentPricing();
					}else{//after 6
						amount+= generalTicketPrice.get_FRI_EVE_PH_from_6_price();
					}
				}else if(day==6||day==0){
					amount+= generalTicketPrice.get_SAT_SUN_PH_pricing();
				}
			}else if(type==TicketType.SENIOR){
				if(day>0&&day<4){//mon-wed
					if(time<18){
						amount+= generalTicketPrice.getSeniorCitizenPricing();
					}else{
						amount+= generalTicketPrice.get_MON_WED_pricing();
					}
				}else if(day==4){
					if(time<18){
						amount+= generalTicketPrice.getSeniorCitizenPricing();
					}else{
						amount+= generalTicketPrice.get_THU_pricing();
					}
				}else if(day==5){//fri
					if(time<18){//before 6
						amount+= generalTicketPrice.getSeniorCitizenPricing();
					}else{//after 6
						amount+= generalTicketPrice.get_FRI_EVE_PH_from_6_price();
					}
				}else if(day==6||day==0){
					amount+= generalTicketPrice.get_SAT_SUN_PH_pricing();
				}
			}
		}
			
	}
		
	return amount;
}
	
	public TicketPrice getTicketPrice(int i){
		return ticketPriceDAO.getTicketPrice(i);
	}
	
	public void setTicketPrice(TicketPrice ticketPrice, int i){
		if(i ==0 ){
			generalTicketPrice = ticketPrice;
			ticketPriceDAO.updateTicketPrice(generalTicketPrice, 0);
		}else{
			_3DTicketPrice = ticketPrice;
			ticketPriceDAO.updateTicketPrice(_3DTicketPrice, 1);
		}
	}
}