package com.moblima.dataaccess;

import com.moblima.model.PublicHoliday;

import java.util.Date;
import java.util.List;

public interface PublicHolidayDAO {
	
	public void createPublicHoliday(PublicHoliday publicHoliday);
	public List<PublicHoliday> getPublicHolidays();
	
	public void delete(PublicHoliday publicHoliday);
	public void update(PublicHoliday publicHoliday);

}