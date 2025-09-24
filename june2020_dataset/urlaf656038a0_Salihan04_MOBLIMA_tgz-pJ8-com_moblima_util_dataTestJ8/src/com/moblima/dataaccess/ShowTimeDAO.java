package com.moblima.dataaccess;

import java.util.Date;
import java.util.List;

import com.moblima.model.Cinema;
import com.moblima.model.Movie;
import com.moblima.model.ShowTime;

public interface ShowTimeDAO {
	
	public void createShowTime(ShowTime showTime);
	public void updateShowTime(ShowTime showTime);
	public void deleteShowTime(ShowTime showTime);
	
	public List<ShowTime> getShowTimes();
	public List<ShowTime> getShowTimes(Movie movie);
	public List<ShowTime> getShowTimes(Cinema cinema);
	public ShowTime getShowTime(int showTimeID);
}
