package logfilegen.allparsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import logfilegen.allmodels.record.Date;

public class ParserDate {
	public Date parser(String dateStr) throws ParseException{
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY hh:mm:ss z");
        
        java.util.Date newDate = dateFormat.parse(dateStr);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(newDate);
        date.setDate(gregorianCalendar);
        
        
        return date;
}
}
