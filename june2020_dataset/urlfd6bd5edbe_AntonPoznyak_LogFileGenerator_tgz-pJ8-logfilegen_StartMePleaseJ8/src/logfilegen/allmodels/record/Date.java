package logfilegen.allmodels.record;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Date {
	private GregorianCalendar date;
	private SimpleDateFormat format;

	public Date(){
		format = new SimpleDateFormat("dd.MM.YYYY hh:mm:ss z");
	}
	public Date(GregorianCalendar date){
		this.date = date;
		format = new SimpleDateFormat("dd.MM.YYYY hh:mm:ss z");
	}
	
	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		if(format != null){ this.format = format; }
	}
	
	public void setFormat(String formatPattern){
		this.format = new SimpleDateFormat(formatPattern);
	}
	
	public String toString(){
		return "[" + format.format(date.getTime()) + "]";
	}
}
