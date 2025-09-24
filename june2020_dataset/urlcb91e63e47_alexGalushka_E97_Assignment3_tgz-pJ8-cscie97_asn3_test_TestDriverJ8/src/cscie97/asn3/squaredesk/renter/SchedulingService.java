package cscie97.asn3.squaredesk.renter;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cscie97.asn2.squaredesk.provider.OfficeSpace;

public class SchedulingService
{

	private static SchedulingService _obj;
	private Map<String, List<Booking>> bookingMap;
	private SimpleDateFormat sdf;
	private String date;
	private String[] dateArray;
	private Calendar calendar;
	private int FIX_NUM_DAYS = 95;
	
	/**
	 * unparameterized constructor: created a brand new bookingMap with FIX_NUM_DAYS keys
	 */
	private SchedulingService()
	{
		bookingMap = new HashMap<String,List<Booking>>();
		sdf = new SimpleDateFormat("yyyy M d");
		date = sdf.format(new Date());
		bookingMap.put(date, new LinkedList<Booking>());
		dateArray = date.split(" ");
		calendar = new GregorianCalendar(Integer.parseInt(dateArray[0]),
				                         Integer.parseInt(dateArray[1]) - 1, 
										 Integer.parseInt(dateArray[2]));
		
		//create a Map with booking up to 3 months, use - 95 days do be on the safe site
		String tempDateAfterRolling;
		String[] tempDateAfterRollingArr;
		String checkYear;
		for ( int increment = 0; increment < FIX_NUM_DAYS; increment++ )
		{
			calendar.roll(Calendar.DAY_OF_YEAR, true); 
			tempDateAfterRolling = sdf.format(calendar.getTime());
			tempDateAfterRollingArr = tempDateAfterRolling.split(" ");
			checkYear = tempDateAfterRollingArr[0]+" 1 1";
			if(checkYear.equals(tempDateAfterRolling))
			{
				calendar.roll(Calendar.YEAR, true);
				tempDateAfterRolling = sdf.format(calendar.getTime());
			}
			
			bookingMap.put(tempDateAfterRolling, null);
		}

	}
	
	
    /**
     * A special static method to access the single SchedulingService instance
     * @return _obj - type: SchedulingService
     */
    public static SchedulingService getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new SchedulingService();
        }
        return _obj;
    }
	
	
	/**
	 * add the new booking to the bookingMap
	 * @param Booking: booking
	 * @throws BookingException
	 */
	public boolean createBooking ( Booking booking ) throws BookingException
	{
		boolean result = true;
		
		Date startDate = booking.getStartDate();
		Date endDate = booking.getEndDate();

		if (startDate.after(endDate))
		{
			throw new BookingException ("start date has to be before end date");
		}
		
		if( !checkAvailability( booking.getOfficespace(), booking.getStartDate(), booking.getEndDate() ) )
		{
			throw new BookingException ("you can't book this office space on the chosen date");
		}
		
		String tempStartDate = sdf.format(startDate);
		String tempEndDate = sdf.format(endDate);
		
		List<String> datesRangeList = getDatesRange ( tempStartDate, tempEndDate ); 
		for ( String d:datesRangeList )
		{
			updateBookingMap ( d, booking );
		}	
		
		result = true;
		
		return result;
		
	}
	
	
	/**
	 * this method check if certain Office Space (OS) is available for rent for "booked dates"
	 * @param Booking: booking 
	 * @return true if OS is available and false if not
	 */
	public boolean checkAvailability ( OfficeSpace office, Date startDate, Date endDate )
	{
		boolean result = true;
		String tempStartDate = sdf.format( startDate );
		String tempEndDate = sdf.format( endDate );
		
		List<String> datesRangeList = getDatesRange ( tempStartDate, tempEndDate ); 
		String receivedOfficeSpaceId = office.getOfficeSpaceGuid();
		
		List<Booking> tempBookingList; 
		String bookedOfficeSpaceId;
		for (String date:datesRangeList)
		{
			tempBookingList = bookingMap.get(date);
			if ( tempBookingList != null )
			{
				for (Booking b:tempBookingList)
				{
					bookedOfficeSpaceId = b.getOfficespace().getOfficeSpaceGuid();
					if (bookedOfficeSpaceId.equals(receivedOfficeSpaceId))
					{
						result =  false;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * this method deletes specific booking from the bookingMap
	 * @param booking
	 */
	public void deleteBooking (Booking booking)
	{
		Date startDate = booking.getStartDate();
		Date endDate = booking.getEndDate();
		
		String tempStartDate = sdf.format(startDate);
		String tempEndDate = sdf.format(endDate);
		
		List<String> datesRangeList = getDatesRange ( tempStartDate, tempEndDate );
		
		List<Booking> tempBookingList;
		for (String date:datesRangeList)
		{
			tempBookingList = bookingMap.get(date);
			if (tempBookingList.contains(booking))
			{
				tempBookingList.remove(booking);
			}
			bookingMap.put(date, tempBookingList);
		}
	 }
	
	/**
	 * this method list the all unique bookings from available in the Scheduling Service
	 * @return set of Bookings
	 */
	public Set<Booking> listBookings ()
	{
		Set<Booking> bookingSet = new HashSet<Booking>();
		Iterator<Entry<String, List<Booking>>> entries = bookingMap.entrySet().iterator();
		List<Booking> tempBookingList;
		while (entries.hasNext()) 
		{
		  Entry<String, List<Booking>> thisEntry = entries.next();
		  tempBookingList = thisEntry.getValue();
		  if ( tempBookingList != null)
		  {
			  for (Booking b: tempBookingList)
			  {
				  bookingSet.add(b);
			  }
		  }
		}
		return bookingSet;
	}
	
	/**
	 * this private method updates the bookingMap with a new booking
	 * @param String: date
	 * @param Booking: booking
	 */
	private void updateBookingMap ( String date, Booking booking )
	{
		List<Booking> tempBookingList;
		if (bookingMap.containsKey(date))
		{
			tempBookingList = bookingMap.get(date);
			if ( tempBookingList == null )
			{
				tempBookingList = new LinkedList<Booking>();
				
			}
			tempBookingList.add(booking);
		    bookingMap.put(date, tempBookingList);
		}
		else
		{
			tempBookingList = new LinkedList<Booking>();
			tempBookingList.add(booking);
			bookingMap.put(date, tempBookingList);
		}
	}
	
	
	/**
	 * this private method converts the start and end dates to the rage of consecutive dates
	 * @param String: startDate
	 * @param String: endDate
	 * @return the list of consecutive dates
	 */
	private List<String> getDatesRange (String startDate, String endDate)
	{
		List<String> datesList = new LinkedList<String>();
		Calendar tempCalendar; 
		String[] tempDataArray;
		tempDataArray = startDate.split(" ");
		tempCalendar = new GregorianCalendar(Integer.parseInt(tempDataArray[0]),
				                         Integer.parseInt(tempDataArray[1]) - 1, 
										 Integer.parseInt(tempDataArray[2]));
		String tempDateAfterRolling = "";
		String[]tempDateAfterRollingArr;
		String checkYear;
		datesList.add (startDate);
		while (!endDate.equals(tempDateAfterRolling))
		{
			tempCalendar.roll(Calendar.DAY_OF_YEAR, true); 
			tempDateAfterRolling = sdf.format(tempCalendar.getTime());
			tempDateAfterRollingArr = tempDateAfterRolling.split(" ");
			checkYear = tempDateAfterRollingArr[0]+" 1 1";
			if(checkYear.equals(tempDateAfterRolling))
			{
				tempCalendar.roll(Calendar.YEAR, true);
				tempDateAfterRolling = sdf.format(tempCalendar.getTime());
			}
			
			datesList.add(tempDateAfterRolling);
		}
		
		return datesList;
	}

}
