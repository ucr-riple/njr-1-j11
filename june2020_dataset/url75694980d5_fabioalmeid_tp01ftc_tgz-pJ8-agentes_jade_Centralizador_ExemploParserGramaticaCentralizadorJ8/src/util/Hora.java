package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Hora {
	
	public static String getPrettyHour() {
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		
		int hour24  = calendar.get(Calendar.HOUR_OF_DAY); // formato 24h
		//int hour212 = calendar.get(Calendar.HOUR);       // formato 12h
		int minute  = calendar.get(Calendar.MINUTE);
		
		String resultado = "as " +  String.valueOf(hour24) + " h: " + String.valueOf(minute) + " m";
		
		return resultado;
	}

}
