package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.model.PublicHoliday;

public class PublicHolidayDAOImpl implements PublicHolidayDAO {
	
	private static PublicHolidayDAO publicHolidayDAO;
	
	private SerializeDB serializeDB;
	private List<PublicHoliday> publicHolidays;
	
	public static PublicHolidayDAO getInstance() {
		if(publicHolidayDAO == null) {
			publicHolidayDAO = new PublicHolidayDAOImpl();
		}
		
		return publicHolidayDAO;
	}
	
	private PublicHolidayDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		publicHolidays = serializeDB.getPublicHolidays();
	}
	
	public void createPublicHoliday(PublicHoliday publicHoliday) {
		int publicHolidayID = serializeDB.getPublicHolidayID();
		publicHoliday.setPublicHolidayID(publicHolidayID);
		serializeDB.setPublicHolidayID(publicHolidayID+1);
		
		publicHolidays.add(publicHoliday);
		
		serializeDB.saveData();
	}
	
	public List<PublicHoliday> getPublicHolidays() {
		return publicHolidays;
	}
	
	public void delete(PublicHoliday publicHoliday) {
		publicHolidays.remove(publicHoliday);
		serializeDB.saveData();
	}
	
	public void update(PublicHoliday publicHoliday) {
		for(PublicHoliday ph: publicHolidays){
			if(ph.getPublicHolidayID()==publicHoliday.getPublicHolidayID()){
				ph=publicHoliday;
			}
		}
		serializeDB.saveData();
	}
	
}