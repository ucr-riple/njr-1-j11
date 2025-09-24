package logfilegen.allconverters.record;

import java.text.SimpleDateFormat;

import logfilegen.allmodels.record.Date;

public class DateConv {
	private Date date;
	
	public DateConv(Date date){
		this.date = date;
	}
	public DateConv(Date date, SimpleDateFormat format){
		this.date= date;
		date.setFormat(format);
	}
	
	public DateConv(Date date, String format){
		this.date=date;
		date.setFormat(format);
	}
	public String convertToString(){
		return date.getFormat().format(date.getDate());
	}
}
