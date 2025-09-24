package com.moblima.dataaccess;

import java.util.List;

import com.moblima.model.Booking;
import com.moblima.model.BookingStatus;

public interface BookingDAO {
	
	public void createBooking(Booking booking);
	public void updateBooking(Booking booking);
	public void deleteBooking(Booking booking);
	public Booking getBooking(int bookingID);
	public List<Booking> getBookings();
}
