package com.moblima.businesslogic;

import java.util.Date;
import java.util.List;

import com.moblima.model.*;
import com.moblima.dataaccess.*;

public class PublicHolidayBL {
	PublicHolidayDAO publicHolidayDAO;
	
	public PublicHolidayBL(){
		publicHolidayDAO = PublicHolidayDAOImpl.getInstance();
	}
	
	public List<PublicHoliday> getPublicHolidays(){
		return publicHolidayDAO.getPublicHolidays();
	}
	
	public void setPublicHoliday(Date date, String name){
		PublicHoliday publicHoliday = new PublicHoliday(name,date);
		publicHolidayDAO.createPublicHoliday(publicHoliday);
	}
	
	public void removePublicHoliday(int id){
		List<PublicHoliday> publicHolidays = publicHolidayDAO.getPublicHolidays();
		for(int i=0; i<publicHolidays.size(); i++) {
			PublicHoliday ph = publicHolidays.get(i);
			if(ph.getPublicHolidayID()==id){
				publicHolidayDAO.delete(ph);
				break;
			}
		}
	}
	
	public PublicHoliday getPublicHoliday(int id){
		for(PublicHoliday ph: publicHolidayDAO.getPublicHolidays()){
			if(ph.getPublicHolidayID()==id){
				return ph;
			}
		}
		return null;
		
	}
}
